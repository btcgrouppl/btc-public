package pl.btcgrouppl.btc.backend.commons;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 05.06.15.
 * <p>
 *     General constants interface, used by another classes
 * </p>
 */
public interface Constants {


    /**
     * Spring integration constants
     */
    interface INTEGRATION {
        public static final String GENERAL_CHANNEL = "generalChannel";
        public static final String GENERAL_DESTINATION = "general_mailbox";
    }

    /**
     * Active profiles
     */
    interface PROFILES {
        public static final String PROFILE_INTEGRATION_JMS = "integration-jms";
        public static final String PROFILE_INTEGRATION_MONGO = "integration-mongo";

        public static final String PROFILE_DB_MONGO = "db-mongo";
    }

    interface JSON {
        public static final String SERIALIZER_CLEAR = "serializerClear";
        public static final String SERIALIZERS_SET = "serializersSet";
        public static final String INTEGRATION_SERIALIZER = "integrationSerializer";
        public static final String INTEGRATION_DESERIALIZER = "integrationDeserializer";
    }

    interface QUALIFIERS {
        public static final String DEFAULT_PUBLISHER = "defaultEventPublisher";
        public static final String INTEGRATION_PUBLISHER = "integrationEventPublisher";
        public static final String DELEGATING_PUBLISHER = "delegatingEventPublisher";

        public static final String SPEL_PARSER = "spElParserUtil";
    }
}
