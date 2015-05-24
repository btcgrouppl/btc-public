package pl.btcgrouppl.btc.backend.commons.cqrs.impl;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.FactoryBean;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;

import java.util.concurrent.Executors;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 23.05.15.
 */
public class CommandExecutorServiceFactoryBean implements FactoryBean<CommandExecutorService> {

    public static final Integer DEFAUT_THREAD_POOL_SIZE = 4;

    private CommandHandlerRegistry commandHandlerRegistry;
    private Integer threadPoolSize;


    public CommandExecutorServiceFactoryBean(CommandHandlerRegistry commandHandlerRegistry) {
        this(commandHandlerRegistry, DEFAUT_THREAD_POOL_SIZE);
    }

    public CommandExecutorServiceFactoryBean(CommandHandlerRegistry commandHandlerRegistry, Integer threadPoolSize) {
        this.commandHandlerRegistry = Preconditions.checkNotNull(commandHandlerRegistry);
        this.threadPoolSize = Preconditions.checkNotNull(threadPoolSize);
    }

    @Override
    public CommandExecutorService getObject() throws Exception {
        return CommandExecutorServiceImpl.create(Executors.newFixedThreadPool(threadPoolSize), commandHandlerRegistry);
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
