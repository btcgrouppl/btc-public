package pl.btcgrouppl.btc.backend.commons.integration.subscribers.impl;

import lombok.EqualsAndHashCode;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.Constants;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;

import java.util.*;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 08.06.15.
 * <p>
 *     Integration subscriber registry. Look for classes annotated with {@link pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation}
 *     and with implemented interface IntegrationSubscriber
 * </p>
 */
@Component
public class IntegrationSubscriberRegistry implements AutoCloseable, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrationSubscriberRegistry.class);

    public static final String SUBSCRIBER_DEFAULT_CHANNEL = Constants.INTEGRATION.GENERAL_CHANNEL;

    /**
     * Map of String (bean name) --> subscriber's channels
     */
    private Map<String, SubscriberMessageHandler> subscribersToHandlers = Collections.synchronizedMap(new HashMap<String, SubscriberMessageHandler>());

    /**
     * Map channel name --> bean for channel name
     */
    private Map<String, String> channelsToBeanNames = Collections.synchronizedMap(new HashMap<String, String>());

    private final ConfigurableListableBeanFactory beanFactory;


    @Autowired
    public IntegrationSubscriberRegistry(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        subscribersToHandlers.clear();
        scanForChannels();
        scanForSubscribers();
    }

    @Override
    public void close() throws Exception {
        for(Map.Entry<String, SubscriberMessageHandler> subscriber: subscribersToHandlers.entrySet()) {
            subscribeUnsubscribeHandler(subscriber.getValue(), false);
        }
        subscribersToHandlers.clear();
    }

    /**
     * Scanning for all available subscribers
     */
    protected void scanForSubscribers() {
        String[] beanNamesForType = beanFactory.getBeanNamesForType(IntegrationSubscriber.class);
        String[] channels;
        for(String beanName: beanNamesForType) {
            IntegrationSubscriber integrationSubscriber = (IntegrationSubscriber)beanFactory.getBean(beanName);
            Class<?> beanClass = integrationSubscriber.getClass();
            if(!beanClass.isAnnotationPresent(IntegrationSubscriberAnnotation.class)) {
                LOG.debug("No annotation IntegrationSubscriberAnnotation present for bean: " + beanName + ". Setting default message channel.");
                channels = new String[] {SUBSCRIBER_DEFAULT_CHANNEL};   //If no annotation, setting default channel
            }
            else {
                IntegrationSubscriberAnnotation annotation = beanClass.getAnnotation(IntegrationSubscriberAnnotation.class);
                channels = annotation.channels();
            }
            SubscriberMessageHandler subChannelsHandler = new SubscriberMessageHandler(channels, integrationSubscriber);
            subscribeUnsubscribeHandler(subChannelsHandler, true);
            subscribersToHandlers.put(beanName, subChannelsHandler);
        }
    }

    /**
     * Subscribing to requested channels (PublishSubscribeChannel instances)
     * @param subMessageHandler {@link SubscriberMessageHandler} instance
     * @param subscribe if false, unsubscribing
     */
    protected void subscribeUnsubscribeHandler(SubscriberMessageHandler subMessageHandler, boolean subscribe) {
        List<String> channels = subMessageHandler.channels;
        for(String channelName: channels) {
            if(!channelsToBeanNames.containsKey(channelName)) {
                LOG.debug("Trying to add subscribtion for non-existing channel name: " + channelName);
                continue;
            }
            PublishSubscribeChannel channelBean = beanFactory.getBean(channelsToBeanNames.get(channelName), PublishSubscribeChannel.class);
            boolean toSubscribe = subscribe ? channelBean.subscribe(subMessageHandler) : channelBean.unsubscribe(subMessageHandler);
            LOG.debug("Subscribe (true)/usubscribe (false): " + subscribe + " to channel: " + channelName + ". Subscriber handler ID: " + subMessageHandler.uuid);
        }
    }

    /**
     * Scanning for channels
     */
    protected void scanForChannels() {
        String[] beanNamesForType = beanFactory.getBeanNamesForType(PublishSubscribeChannel.class);
        for (String beanName : beanNamesForType) {
            PublishSubscribeChannel pubSubChannel = beanFactory.getBean(beanName, PublishSubscribeChannel.class);
            LOG.debug("Registering pub-sub channel in registry. Channel name: " + pubSubChannel.getComponentName() + ", bean name: " + beanName);
            channelsToBeanNames.put(pubSubChannel.getComponentName(), beanName);
        }
    }

    public Map<String, SubscriberMessageHandler> getSubscribersToHandlers() {
        return Collections.unmodifiableMap(subscribersToHandlers);
    }

    public Map<String, String> getChannelsToBeanNames() {
        return Collections.unmodifiableMap(channelsToBeanNames);
    }



    /**
     * Subscriber channels adapter class. Adapting IntegrationSubscriber to MessageHandler
     *
     */
    @EqualsAndHashCode
    public class SubscriberMessageHandler implements MessageHandler {

        public final UUID uuid;   //Subscriber unique identifier
        public final List<String> channels;
        public final IntegrationSubscriber integrationSubscriber;


        public SubscriberMessageHandler(String[] channelsAsString, IntegrationSubscriber integrationSubscriber) {
            this(Arrays.asList(channelsAsString), integrationSubscriber);
        }

        public SubscriberMessageHandler(List<String> channelsAsString, IntegrationSubscriber integrationSubscriber) {
            channels = Collections.unmodifiableList(channelsAsString);
            this.integrationSubscriber = integrationSubscriber;
            uuid = UUID.randomUUID();
        }

        @Override
        public void handleMessage(Message<?> message) throws MessagingException {
            LOG.debug("Subscriber handler with UUID: " + uuid + ". Trying to handle message: " + message);
            IntegrationMessage payload = (IntegrationMessage)message.getPayload();
            try {
                IntegrationMessageEvent integrationMessageEvent = IntegrationMessageEvent.fromMessage(message);
                integrationSubscriber.onIntegrationMessage(integrationMessageEvent);
            }
            catch(NullPointerException e) { //In case of errors on IntegrationMessageEvent creation
                LOG.error("Unable to handle integration message." + payload.getUuid() + " to subscriber handler ID: " + uuid + ".", e);
            }
        }
    }

}