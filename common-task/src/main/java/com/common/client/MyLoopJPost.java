package com.common.client;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Sanjay on 07-11-2015.
 */
public class MyLoopJPost {


    private final OnLoopJPostCallComplete onLoopJPostCallComplete;
    AsyncHttpClient client = new AsyncHttpClient();
    private ProgressDialog dialog;
    private Context context;

    /**
     * @param ctx
     * @param onLoopJPostCallComplete
     * @param url
     * @param requestParams
     */
    public MyLoopJPost(Context ctx , final OnLoopJPostCallComplete onLoopJPostCallComplete, String url, RequestParams requestParams) {

        this.context = ctx;
        this.onLoopJPostCallComplete = onLoopJPostCallComplete;
        System.out.println("Url : " + url);
//        System.out.println("Parameter : " + requestParams);
        client.setTimeout(50 * 1000);
        client.post(url.replace(" ", "%20"), requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                onLoopJPostCallComplete.response(response.toString());
                System.out.println("Response POST: " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                onLoopJPostCallComplete.response(responseString);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }


    public interface OnLoopJPostCallComplete {
        void response(String result);
    }
}
