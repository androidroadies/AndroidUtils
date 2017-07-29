package com.xmpp.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.xmpp.library.service.XMPPService;
import com.xmpp.library.utils.XmppPreference;

/*
   RECEIVE INTERNET STATE AND MANAGE XMPP CONNECTION ON BASED CONNECTION CHANGE
*/

public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called]");

        if (isNetworkAvailable(context)) {
            Log.e(TAG, "Internet available");
            if (!XmppPreference.getInstance(context).getUserId().equals("")) {
                context.startService(XMPPService.getConnectIntent(context));
            }

        } else {
            Log.e(TAG, "Internet is not available");
            context.stopService(new Intent(context, XMPPService.class));
        }

    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null
                && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
