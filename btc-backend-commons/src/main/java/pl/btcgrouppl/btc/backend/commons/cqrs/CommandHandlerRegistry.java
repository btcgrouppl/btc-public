package pl.btcgrouppl.btc.backend.commons.cqrs;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 19.05.15.<br /><br />
 * <p>
 *     Command handler registry interface. All stored objects annotated with CommandHandler annotation will be available<br />
 * via this class.
 * </p>
 */
public interface CommandHandlerRegistry {

    /**
     *
     * @param commandClass
     * @return CommandHandler
     */
    CommandHandler getCommandHandler(Class<?> commandClass);

}
