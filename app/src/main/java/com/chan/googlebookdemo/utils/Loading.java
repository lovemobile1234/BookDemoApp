package com.chan.googlebookdemo.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Loading {

    private static ProgressDialog progressDialog;

    public static void show(Context context, boolean cancelable, String message) {
        if(progressDialog == null || !progressDialog.isShowing()) {
            if(context != null){
                progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(cancelable);
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        }
    }

    public static void cancel() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
    }
}
