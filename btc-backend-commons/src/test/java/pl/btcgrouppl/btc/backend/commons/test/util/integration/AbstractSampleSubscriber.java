package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import rx.Observable;
import rx.subjects.ReplaySubject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Abstract sample subscriber. Operations connected with testing, like: counting messages etc.
 * </p>
 */
public class AbstractSampleSubscriber implements IntegrationSubscriberHelper, AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(AbstractSampleSubscriber.class);

    protected Map<String, ReplaySubject<Integer>> channelNameToCountMap = new HashMap<>();  //Channel name to replay subject instances

    public AbstractSampleSubscriber() {
        init();
    }

    @Override
    public Observable<Integer> countForChannel(String channelName) {
        if(!channelNameToCountMap.containsKey(channelName))
            return Observable.empty();
        return channelNameToCountMap.get(channelName).asObservable();   //Setting timeout, need to get last message
    }

    @Override
    public void refresh() throws Exception {
        LOG.debug("Refreshing all counter data!");
        close();
        init();
    }

    @Override
    public void increase(String channelName) {
        channelNameToCountMap.get(channelName).onNext(1);   //TODO fix
    }

    @Override
    public void close() throws Exception {
        Collection<ReplaySubject<Integer>> values = channelNameToCountMap.values();
        for(ReplaySubject<Integer> item: values) {
            item.onCompleted();
        }
        channelNameToCountMap.clear();
    }

    /**
     * Scan for IntegrationSubscriberAnnotation and add all channels
     */
    private void init() {
        IntegrationSubscriberAnnotation annotation = this.getClass().getAnnotation(IntegrationSubscriberAnnotation.class);
        if(annotation==null) {
            throw new IllegalStateException("Class extending AbstractSampleSubscriber has to be annotated with IntegrationSubscriberAnnotation");
        }
        String[] channels = annotation.channels();
        for(String channel: channels) {
            if(!channelNameToCountMap.containsKey(channel)) {
                LOG.debug("Adding ReplaySubject for channel: " + channel);
                channelNameToCountMap.put(channel, ReplaySubject.<Integer>create());
            }
        }
    }
}
