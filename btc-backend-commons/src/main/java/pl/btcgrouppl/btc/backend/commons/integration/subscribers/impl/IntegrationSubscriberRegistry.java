package pl.btcgrouppl.btc.backend.commons.integration.subscribers.impl;

import lombok.EqualsAndHashCode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;

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

    private static final Logger LOG = LogManager.getLogger(IntegrationSubscriberRegistry.class);

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
        scanForSubscribers();
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
            checkChannelsBeforeSubscribe(subChannelsHandler);  //Check, if channels exist before subscribe
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
            PublishSubscribeChannel channelBean = beanFactory.getBean(channelsToBeanNames.get(channelName), PublishSubscribeChannel.class);
            boolean toSubscribe = subscribe ? channelBean.subscribe(subMessageHandler) : channelBean.unsubscribe(subMessageHandler);
        }
    }

    /**
     * Scanning for channels
     */
    protected void scanForChannels() {
        String[] beanNamesForType = beanFactory.getBeanNamesForType(PublishSubscribeChannel.class);
        for(String beanName: beanNamesForType) {
            PublishSubscribeChannel pubSubChannel = beanFactory.getBean(beanName, PublishSubscribeChannel.class);
            LOG.debug("Registering pub-sub channel in registry. Channel name: " + pubSubChannel.getComponentName() + ", bean name: " + beanName);
            channelsToBeanNames.put(pubSubChannel.getComponentName(), beanName);
        }
    }

    /**
     * Checking, if subscriber try to subscribe correct channels
     * @param subscriberChannels
     */
    protected void checkChannelsBeforeSubscribe(SubscriberMessageHandler subscriberChannels) {

    }

    @Override
    public void close() throws Exception {
        for(Map.Entry<String, SubscriberMessageHandler> subscriber: subscribersToHandlers.entrySet()) {
            subscribeUnsubscribeHandler(subscriber.getValue(), false);
        }
        subscribersToHandlers.clear();
    }



    /**
     * Subscriber channels wrapper class
     *
     */
    @EqualsAndHashCode
    private class SubscriberMessageHandler implements MessageHandler {

        public final List<String> channels;
        public final IntegrationSubscriber integrationSubscriber;

        public SubscriberMessageHandler(List<String> channelsAsString, IntegrationSubscriber integrationSubscriber) {
            channels = Collections.unmodifiableList(channelsAsString);
            this.integrationSubscriber = integrationSubscriber;
        }

        public SubscriberMessageHandler(String[] channelsAsString, IntegrationSubscriber integrationSubscriber) {
            channels = Collections.unmodifiableList(Arrays.asList(channelsAsString));
            this.integrationSubscriber = integrationSubscriber;
        }

        @Override
        public void handleMessage(Message<?> message) throws MessagingException {
            IntegrationMessage payload = (IntegrationMessage)message.getPayload();

            //TODO finish
        }
    }

}