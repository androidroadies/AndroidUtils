package com.common.api;

import rx.android.schedulers.AndroidSchedulers;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxApiCallHelper {

    public static <T> Subscription call(Observable<T> observable, final RxApiCallback<T> rxApiCallback) {

        if (observable == null) {
            throw new IllegalArgumentException("Observable must not be null.");
        }

        if (rxApiCallback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .onErrorResumeNext(Observable.<T>empty())
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        rxApiCallback.onSuccess(t);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        rxApiCallback.onFailed(throwable.getMessage());
                    }
                });
    }
}
