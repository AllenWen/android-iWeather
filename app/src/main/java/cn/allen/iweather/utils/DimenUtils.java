package cn.allen.iweather.utils;

import android.content.res.Resources;

import cn.allen.iweather.App;

/**
 * Created by allen on 2017/11/17.
 */

public class DimenUtils {

    private static final Resources mResource;

    static {
        mResource = App.getAppContext().getResources();
    }

    public static int sp2px(float spValue) {
        float fontScale = mResource.getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(int pxValue) {
        float fontScale = mResource.getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dip2px(float dipValue) {
        final float scale = mResource.getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(int pxValue) {
        final float scale = mResource.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
