package pl.btcgrouppl.btc.backend.commons;

import org.springframework.beans.factory.annotation.Qualifier;

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
    public interface INTEGRATION {
        public static final String GENERAL_CHANNEL = "generalChannel";
        public static final String GENERAL_DESTINATION = "general_mailbox";
    }

    /**
     * Active profiles
     */
    public interface PROFILES {
        public static final String PROFILE_INTEGRATION_JMS = "integration-jms";
        public static final String PROFILE_INTEGRATION_MONGO = "integration-mongo";

        public static final String PROFILE_DB_MONGO = "db-mongo";
    }

    public interface JSON {
        public static final String SERIALIZER_CLEAR = "serializerClear";
        public static final String SERIALIZERS_SET = "serializersSet";
        public static final String INTEGRATION_SERIALIZER = "integrationSerializer";
        public static final String INTEGRATION_DESERIALIZER = "integrationDeserializer";
    }
}
