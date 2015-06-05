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
}
