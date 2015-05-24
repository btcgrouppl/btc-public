package pl.btcgrouppl.btc.backend.commons.cqrs.impl;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandExecutorService;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandler;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;
import pl.btcgrouppl.btc.backend.commons.cqrs.annotations.CommandAnnotation;
import pl.btcgrouppl.btc.backend.commons.cqrs.exceptions.CqrsException;
import rx.Observable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 23.05.15.
 * <p>
 *     Command executor service for all CQRS commands. Default implementation. Using
 *     {@link pl.btcgrouppl.btc.backend.commons.cqrs.impl.CommandExecutorServiceFactoryBean} to create instance
 * </p>
 */
@Data
public class CommandExecutorServiceImpl implements CommandExecutorService, AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(CommandExecutorServiceImpl.class);

    private ExecutorService executorService;
    private CommandHandlerRegistry commandHandlerRegistry;

    /**
     * Create instance by {@link #create(ExecutorService, pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry)}
     * @param executorService
     * @param commandHandlerRegistry
     */
    private CommandExecutorServiceImpl(ExecutorService executorService, CommandHandlerRegistry commandHandlerRegistry) {
        this.executorService = executorService;
        this.commandHandlerRegistry = commandHandlerRegistry;
    }

    /**
     * If you manually create this object, do it by this command
     * @param executorService
     * @param commandHandlerRegistry
     * @throws pl.btcgrouppl.btc.backend.commons.cqrs.exceptions.CqrsException if no command hander for command, or execution error
     */
    public static CommandExecutorServiceImpl create(ExecutorService executorService, CommandHandlerRegistry commandHandlerRegistry) {
        executorService = Preconditions.checkNotNull(executorService);
        commandHandlerRegistry = Preconditions.checkNotNull(commandHandlerRegistry);
        return new CommandExecutorServiceImpl(executorService, commandHandlerRegistry);
    }

    @Override
    public <R> Observable<R> execute(@NonNull final Object command) {
        try {
            final CommandHandler commandHandler = commandHandlerRegistry.getCommandHandler(command.getClass());
            Class<?> commandClass = command.getClass();
            if(commandClass.isAnnotationPresent(CommandAnnotation.class)) {
                CommandAnnotation annotation = commandClass.getAnnotation(CommandAnnotation.class);
                if (annotation.isAsync()) {
                    return Observable.from(
                        executorService.submit(new Callable<R>() {
                            @Override
                            public R call() throws Exception {
                                return (R) commandHandler.handleCommand(command);
                            }
                        })
                    );
                }
            }
            return Observable.<R> just((R) commandHandler.handleCommand(command));   //By default, sync execution
        }
        catch(IllegalArgumentException e) { //No command handler
            LOG.error("Error during command handler execution!", e);
            throw new CqrsException("Error during command handler execution!", e);
        }
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }
}
