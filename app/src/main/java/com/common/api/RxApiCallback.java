package com.common.api;

public interface RxApiCallback<T> {
    void onSuccess(T t);

    void onFailed(String errorMsg);
}