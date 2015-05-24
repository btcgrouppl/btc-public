package pl.btcgrouppl.btc.backend.commons.cqrs;


import rx.Observable;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 23.05.15.
 */
public interface CommandExecutorService {

    /**
     * <p>
     *     Executing requested command. Getting CommandHandler for command class. Additionally, getting info about
     *     {@link pl.btcgrouppl.btc.backend.commons.cqrs.annotations.CommandAnnotation} annotation. If it's
     *     async command, passing it to executor, and returning Future
     * </p>
     *
     * @param command
     * @param <R> result type of CommandHandler execution
     * @return Observable&lt;Object&gt;. You can subscribe to it and get result as soon as there is some.
     */
    <R> Observable<R> execute(Object command);

}
