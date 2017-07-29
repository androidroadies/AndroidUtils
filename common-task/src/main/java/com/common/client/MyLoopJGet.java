package com.common.client;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Sanjay on 07-11-2015.
 */
public class MyLoopJGet {


    private final OnLoopJGetCallComplete onLoopJGetCallComplete;
    AsyncHttpClient client = new AsyncHttpClient();
    private ProgressDialog dialog;
    private Context context;

    /**
     * @param ctx
     * @param onLoopJGetCallComplete
     * @param url
     */
    public MyLoopJGet(Context ctx, final OnLoopJGetCallComplete onLoopJGetCallComplete, String url) {

        this.context = ctx;
        this.onLoopJGetCallComplete = onLoopJGetCallComplete;
        System.out.println("Url : " + url);
        client.setTimeout(50 * 1000);
        client.get(url.replace(" ", "%20"), new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                onLoopJGetCallComplete.response(response.toString());
                System.out.println("Response GET: " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                onLoopJGetCallComplete.response(responseString);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

    }

    public interface OnLoopJGetCallComplete {
        void response(String result);
    }
}
