package pl.btcgrouppl.btc.backend.commons.test;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 12.06.15.
 * <p>
 *     Test constants
 * </p>
 */
public interface TestConstants {

    interface INTEGRATION {
        public static final String TEST_CHANNEL_DIRECT = "testChannelDirect";
        public static final String TEST_CHANNEL_PUBSUB = "testChannelPubSub";
        public static final String TEST_DESTINATION = "testDestination";

        public static final String TEST_FLOW_IN = "testFlowIn";
        public static final String TEST_FLOW_OUT = "testFlowOut";
    }

    interface DDD {
        public static final String SPEL_CONDITIONAL_EXPRESSION = "x>20";
        public static final String SPEL_OBJECT_CONDITIONAL_EXPRESSION = "event.x>20";
    }

    interface OTHERS {
        public static final String TEST_PROP_SOURCE_JMS = "classpath:/test-application-jms.properties";
    }
}
