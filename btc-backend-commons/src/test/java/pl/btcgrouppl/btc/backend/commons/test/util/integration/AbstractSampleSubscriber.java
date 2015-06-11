package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
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

    protected ReplaySubject<IntegrationMessage> replaySubject;

    public AbstractSampleSubscriber() {
        init();
    }

    @Override
    public void close() throws Exception {
        replaySubject.onCompleted();
    }

    @Override
    public Observable<IntegrationMessage> countMessages() {
        return replaySubject.asObservable();
    }

    @Override
    public void notify(IntegrationMessage integrationMessage) {
        LOG.debug("Incomming integration message: " + integrationMessage + ". Passing it to rx Subject");

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
