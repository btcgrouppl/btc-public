package pl.btcgrouppl.btc.backend.commons.cqrs.impl;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandler;
import pl.btcgrouppl.btc.backend.commons.cqrs.CommandHandlerRegistry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 20.05.15.
 */
@Component
public class CommandHandlerRegistryImpl implements CommandHandlerRegistry, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LogManager.getLogger(CommandHandlerRegistryImpl.class);

    private final ConfigurableListableBeanFactory beanFactory;
    private final Map<Class<?>, String> commandHandlerMap = Collections.synchronizedMap(new HashMap<Class<?>, String>());

    @Autowired
    public CommandHandlerRegistryImpl(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Getting command by command class. Throwing {@link pl.btcgrouppl.btc.backend.commons.cqrs.models.exceptions.CqrsException} in case of no such element.
     * @param commandClass
     * @return CommandHandler
     * @throws java.lang.IllegalArgumentException
     */
    @Override
    public CommandHandler getCommandHandler(Class<?> commandClass) {
        if(!commandHandlerMap.containsKey(commandClass)) {
            throw new IllegalArgumentException("Sorry, no command handler for class: " + commandClass);
        }
        String commandBeanAsString = commandHandlerMap.get(commandClass);
        return (CommandHandler)beanFactory.getBean(commandBeanAsString);
    }

    /**
     * Called when context fully initialized. Storing information about all handlers
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        commandHandlerMap.clear();
        String[] beanNamesForType = beanFactory.getBeanNamesForType(CommandHandler.class);
        for(String item: beanNamesForType) {
            try {
                commandHandlerMap.put(getCommandClassForBeanName(item), item);
            }
            catch(ClassNotFoundException | IllegalArgumentException e) {
                LOG.error("Error while getting command class. Continue command handlers scanning..." + e);
            }
        }
    }

    protected Class<?> getCommandClassForBeanName(String beanName) throws ClassNotFoundException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
        Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
        Type[] genericInterfaces = beanClass.getGenericInterfaces();
        for(Type type: genericInterfaces) {
            if(type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (CommandHandler.class.equals(parameterizedType.getRawType())) {
                    return (Class<?>)parameterizedType.getActualTypeArguments()[0];
                }
            }
        }
        throw new IllegalArgumentException("No type args for command handler bean: " + beanName);
    }
}
