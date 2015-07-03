package pl.btcgrouppl.btc.backend.commons.test.util.ddd;

import pl.btcgrouppl.btc.backend.commons.ddd.events.EventHandler;
import pl.btcgrouppl.btc.backend.commons.ddd.events.impl.AsyncEventHandlerWrapper;
import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 01.07.15.
 * <p>
 *     Async event handler wrapper with RX publish subject prepared for tests.
 * </p>
 */
public final class RxAsyncEventHandlerWrapper extends AsyncEventHandlerWrapper {

    private ReplaySubject<Boolean> executionSubject = ReplaySubject.create();

    public RxAsyncEventHandlerWrapper(EventHandler wrappedEventHandler) {
        super(wrappedEventHandler);
    }

    @Override
    public void onSuccessHandle(Object result) {
        super.onSuccessHandle(result);
        executionSubject.onNext(Boolean.TRUE);
        if(!executionSubject.hasCompleted()) {
            executionSubject.onCompleted();
        }
    }

    @Override
    public void onFailureHandle(Exception e) {
        super.onFailureHandle(e);
        executionSubject.onNext(Boolean.FALSE);
        if(!executionSubject.hasCompleted()) {
            executionSubject.onCompleted();
        }
    }

    public Observable<Boolean> asObservable() {
        return executionSubject.asObservable();
    }
}