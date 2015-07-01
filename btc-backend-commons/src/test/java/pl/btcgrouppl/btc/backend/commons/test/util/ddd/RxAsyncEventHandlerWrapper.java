package pl.btcgrouppl.btc.backend.commons.test.util.ddd;

import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.AsyncEventHandlerWrapper;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.07.15.
 * <p>
 *     Async event handler wrapper with RX publish subject prepared for tests
 * </p>
 */
public final class RxAsyncEventHandlerWrapper extends AsyncEventHandlerWrapper {

    private PublishSubject<Boolean> executionSubject = PublishSubject.create();

    public RxAsyncEventHandlerWrapper(EventHandler wrappedEventHandler) {
        super(wrappedEventHandler);
    }

    @Override
    public void handle(Object event) {
        executionSubject.onNext(Boolean.TRUE);
        super.handle(event);
    }

    @Override
    public void onFailure(Exception e) {
        super.onFailure(e);
        executionSubject.onNext(Boolean.FALSE);
    }

    public Observable<Boolean> asObservable() {
        return executionSubject.asObservable();
    }
}