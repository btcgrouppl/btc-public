package pl.btcgrouppl.btc.backend.commons.test.util.integration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Abstract sample subscriber. Operations connected with testing, like: counting messages etc.
 * </p>
 */
public abstract class AbstractSampleSubscriber implements IntegrationSubscriber, IntegrationSubscriberHelper, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSampleSubscriber.class);

    protected ReplaySubject<IntegrationMessage> replaySubject;
    protected CompositeSubscription compositeSubscription;

    public AbstractSampleSubscriber() {
        init();
    }

    /**
     * Getting subscriber's ID
     * @return String
     */
    public abstract String getSubscriberId();

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
        LOG.debug("Sample subscriber " + getSubscriberId() + ": " + integrationMessageEvent);
        LOG.debug("Sample subscriber " + getSubscriberId() + ":  headers: " + integrationMessageEvent.getHeaders());
        LOG.debug("Sample subscriber " + getSubscriberId() + ":  message body: " + integrationMessageEvent.getIntegrationMessageBody());
        IntegrationMessage integrationMessage = integrationMessageEvent.getIntegrationMessage();
        notify(integrationMessage);     //Notify RX subject
    }

    @Override
    public void close() throws Exception {
        replaySubject.onCompleted();
        compositeSubscription.clear();
    }

    private void init() {
        replaySubject = ReplaySubject.create();
        compositeSubscription = new CompositeSubscription();
        IntegrationSubscriberAnnotation annotation = this.getClass().getAnnotation(IntegrationSubscriberAnnotation.class);
        if(annotation==null) {
            throw new IllegalStateException("Class extending AbstractSampleSubscriber has to be annotated with IntegrationSubscriberAnnotation");
        }
        String[] channels = annotation.channels();
        LOG.debug("Listening for messages from channels: " + channels);
    }
}
