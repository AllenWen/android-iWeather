package cn.allen.iweather.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/16
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class ToastUtils {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Toast mToast = null;

    public static void show(final Context context, final String msg) {
        showMsg(context, msg, Toast.LENGTH_SHORT);
    }

    public static void show(final Context context, final int msgResId) {
        showMsg(context, context.getString(msgResId), Toast.LENGTH_SHORT);
    }

    private static void showMsg(final Context context, final String msg, final int duration) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mToast != null) {
                    mToast.setText(msg);
                    mToast.setDuration(duration);
                } else {
                    mToast = Toast.makeText(context, msg, duration);
                }
                mToast.show();
            }
        });
    }
}
