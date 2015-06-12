package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;
import rx.subscriptions.CompositeSubscription;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Abstract sample subscriber. Operations connected with testing, like: counting messages etc.
 * </p>
 */
public class AbstractSampleSubscriber implements IntegrationSubscriber, IntegrationSubscriberHelper, AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(AbstractSampleSubscriber.class);

    protected ReplaySubject<IntegrationMessage> replaySubject;
    protected CompositeSubscription compositeSubscription;

    public AbstractSampleSubscriber() {
        init();
    }

    @Override
    public Observable<IntegrationMessage> asObservable() {
        return replaySubject.asObservable();
    }

    @Override
    public void notify(IntegrationMessage integrationMessage) {
        LOG.debug("Incomming integration message: " + integrationMessage + ". Passing it to rx Subject");
        replaySubject.onNext(integrationMessage);
    }

    @Override
    public Subscription subscribe(Action1<IntegrationMessage> subscriber) {
        final Subscription subscription = replaySubject.subscribe(subscriber);
        compositeSubscription.add(subscription);
        return subscription;
    }

    @Override
    public void onIntegrationMessage(IntegrationMessageEvent integrationMessageEvent) {
        LOG.debug("Sample subscriber1: " + integrationMessageEvent);
        LOG.debug("Sample subscriber1 headers: " + integrationMessageEvent.getHeaders());
        LOG.debug("Sample subscriber1 message body: " + integrationMessageEvent.getIntegrationMessageBody());
        IntegrationMessage integrationMessage = integrationMessageEvent.getIntegrationMessage();
        notify(integrationMessage);     //Notify RX subject
    }

    @Override
    public void close() throws Exception {
        replaySubject.onCompleted();
        compositeSubscription.clear();
    }

    private void init() {
        IntegrationSubscriberAnnotation annotation = this.getClass().getAnnotation(IntegrationSubscriberAnnotation.class);
        if(annotation==null) {
            throw new IllegalStateException("Class extending AbstractSampleSubscriber has to be annotated with IntegrationSubscriberAnnotation");
        }
        String[] channels = annotation.channels();
        LOG.debug("Listening for messages from channels: " + channels);
    }
}
