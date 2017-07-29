package com.common.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;



public class NetworkUtils {

    /**
     * Checks if the Internet connection is available.
     *
     * @return Returns true if the Internet connection is available. False otherwise.
     * *
     */
    private static boolean isInternetAvailable(Context context) {
        ConnectivityManager mConMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return mConMgr.getActiveNetworkInfo() != null
                && mConMgr.getActiveNetworkInfo().isAvailable()
                && mConMgr.getActiveNetworkInfo().isConnected();
    }

    private static void showNetworkDialog(Context context) {
        String message = context.getString(R.string.error_network_connectivity);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.setTitle("No internet connectivity");
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    public static boolean checkNetwork(Context context) {
        if (isInternetAvailable(context)) {
            return true;
        } else {
            showNetworkDialog(context);
            return false;
        }
    }
}