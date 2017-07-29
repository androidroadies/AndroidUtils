package com.common.api;


import com.common.base.BaseApplication;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiFactory {

    /**
     * Base URL for API calls
     */
//    private static final String BASE_URL = "http://192.168.1.80:5151/api/appapi/";
    private static final String BASE_URL = "http://52.66.190.52:5151/api/appapi/";
    private static final String XMPP_URL = "";

    public ApiFactory() {
    }

    private static Retrofit provideRestAdapter(String baseUrl) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create()))
                .client(BaseApplication.getInstance().getOkHttpClient())
                .build();

    }

    private static Retrofit provideRestAdapter() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(BaseApplication.getInstance().getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return provideRestAdapter().create(serviceClass);
    }

//    public static SubscribeRoster subscribeUser() {
//        return provideRestAdapter(XMPP_URL).create(SubscribeRoster.class);
//    }
}