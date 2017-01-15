package com.sdwfqin.microtext.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.graphics.Palette;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AppUtils {

    // 保存图片
    /**
     * 首先默认个文件保存路径
     */
    public static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() :
            "/mnt/sdcard";//保存到SD卡
    public static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/MicrotText";//保存的确切位置

    //下面就是保存的方法，传入参数就可以了
    public static void saveFile(Bitmap bm, String fileName, Context context) {
        BufferedOutputStream bos = null;
        try {
            String subForder = SAVE_REAL_PATH;
            File foder = new File(subForder);
            if (!foder.exists()) {
                foder.mkdirs();
            }
            File myCaptureFile = new File(subForder, fileName);
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(SAVE_REAL_PATH));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }


    public static int getPaletteColor(Bitmap bitmap) {
        int color = -12417291;
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch vibrant = palette.getVibrantSwatch();
        Palette.Swatch vibrantdark = palette.getDarkVibrantSwatch();
        Palette.Swatch vibrantlight = palette.getLightVibrantSwatch();
        Palette.Swatch Muted = palette.getMutedSwatch();
        Palette.Swatch Muteddark = palette.getDarkMutedSwatch();
        Palette.Swatch Mutedlight = palette.getLightMutedSwatch();

        if (vibrant != null) {
            color = vibrant.getRgb();
        } else if (vibrantdark != null) {
            color = vibrantdark.getRgb();
        } else if (vibrantlight != null) {
            color = vibrantlight.getRgb();
        } else if (Muted != null) {
            color = Muted.getRgb();
        } else if (Muteddark != null) {
            color = Muteddark.getRgb();
        } else if (Mutedlight != null) {
            color = Mutedlight.getRgb();
        }
        return color;
    }

    /**
     * 关闭键盘事件
     *
     * @author shimiso
     * @update 2012-7-4 下午2:34:34
     */
    public static void closeInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 检查3G网络 0 无网络； 1 3G网络；2WiFi
     *
     * @return
     */
    public static int checkNetworkInfo(Activity activity) {
        ConnectivityManager conMan = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // mobile 3G Data Network
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        // wifi
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接

        if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
            return 1;
        } else if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            return 2;
        }

        return 0;

    }

    // 获取图片所在文件夹名称
    public static String getDir(String path) {
        String subString = path.substring(0, path.lastIndexOf('/'));
        return subString.substring(subString.lastIndexOf('/') + 1,
                subString.length());
    }
}
