package pl.btcgrouppl.btc.backend.commons.cqrs.impl;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.FactoryBean;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;
import pl.btcgrouppl.btc.backend.commons.utils.impl.BtcContext;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 23.05.15.
 */
public class CommandExecutorServiceFactoryBean implements FactoryBean<CommandExecutorService> {

    private CommandHandlerRegistry commandHandlerRegistry;


    public CommandExecutorServiceFactoryBean(CommandHandlerRegistry commandHandlerRegistry) {
        this.commandHandlerRegistry = Preconditions.checkNotNull(commandHandlerRegistry);
    }

    @Override
    public CommandExecutorService getObject() throws Exception {
        return CommandExecutorServiceImpl.create(BtcContext.INSTANCE.getGlobalExecutor(), commandHandlerRegistry);
    }

    @Override
    public Class<?> getObjectType() {
        return CommandExecutorServiceImpl.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
