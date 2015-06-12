package pl.btcgrouppl.btc.backend.commons.test.util.integration;

import com.google.common.base.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.btcgrouppl.btc.backend.commons.integration.models.annotations.IntegrationSubscriberAnnotation;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessage;
import pl.btcgrouppl.btc.backend.commons.integration.models.pojos.IntegrationMessageEvent;
import pl.btcgrouppl.btc.backend.commons.integration.subscribers.IntegrationSubscriber;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.06.15.
 * <p>
 *     Sample subscriber 1
 * </p>
 */
@IntegrationSubscriberAnnotation
public class SampleSubscriber1 extends AbstractSampleSubscriber {
}