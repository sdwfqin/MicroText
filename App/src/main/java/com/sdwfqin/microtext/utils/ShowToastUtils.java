package com.sdwfqin.microtext.utils;

import android.app.Activity;
import android.widget.Toast;

public class ShowToastUtils {

    public static void showToast(final Activity context, final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
