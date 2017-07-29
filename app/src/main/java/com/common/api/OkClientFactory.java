package com.common.api;

import android.app.Application;
import android.support.annotation.NonNull;


import com.common.constant.Constant;
import com.common.utils.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkClientFactory {
    // Cache size for the OkHttpClient

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final int OKHTTP_TIMEOUT = 60; // seconds

    private OkClientFactory() {
    }

    @NonNull
    public static OkHttpClient provideOkHttpClient(final Application app) {
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        OkHttpClient.Builder builder;


        builder = new OkHttpClient.Builder()
                .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request.Builder request = chain.request().newBuilder();
//                final String fcmToken = FirebaseUtils.getInstance().getFCMToken(app);

//                if (UserPreferenceUtils.getInstance(app).isUserLoggedIn()) {
//
//                    final String userId = UserPreferenceUtils.getInstance(app).getUserId();
//                    final String userToken = UserPreferenceUtils.getInstance(app).getUserToken();
//
//                    request.addHeader("token", userToken)
//                            .addHeader("user_id", userId)
//                            .addHeader("deviceid", "")
//                            .addHeader("devicetype", Constant.ANDROID) // Android Static - 1
//                    ;
//                }else {
//
//                    request.addHeader("deviceid", "")
//                            .addHeader("devicetype", Constant.ANDROID); // Android Static - 1
//                }
                return chain.proceed(request.build());
            }
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }
}
