package cn.allen.iweather.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by allen on 2017/11/17.
 */

public class DeviceUtils {
    private static DisplayMetrics mDisplayMetrics;


    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (null == mDisplayMetrics) {
            mDisplayMetrics = context.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics;
    }

    /**
     * 屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }


}
