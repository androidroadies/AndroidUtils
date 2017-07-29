package com.common.api.requestmodel;

import com.common.api.RxApiCallHelper;
import com.common.api.RxApiCallback;
import com.google.gson.JsonObject;

import rx.Observable;



public class APIRequest {

    public static final String TAG = APIRequest.class.getSimpleName();

    public void APICall(Observable observable, final RxApiCallback<JsonObject> callback) {

        RxApiCallHelper.call(observable, new RxApiCallback<JsonObject>() {

            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (jsonObject != null){
                    callback.onSuccess(jsonObject);
                }else {
                    callback.onFailed("Empty ProfileResponse");
                }
            }

            @Override
            public void onFailed(String errorMsg) {
                callback.onFailed(errorMsg);
            }
        });
    }
}
