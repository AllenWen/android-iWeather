package cn.allen.iweather.repository;

import android.content.Context;

import cn.allen.iweather.utils.PreferencesUtil;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class SpRepository {
    private static final String KEY_HAS_READ_DATA = "hasReadData";

    private static SpRepository mInstance;

    public static SpRepository getInstance() {
        if (mInstance == null) {
            mInstance = new SpRepository();
        }
        return mInstance;
    }

    public boolean hasReadData(Context context) {
        return PreferencesUtil.getBoolean(context, KEY_HAS_READ_DATA, false);
    }

    public void setReadData(Context context, boolean hasRead) {
        PreferencesUtil.putBoolean(context, KEY_HAS_READ_DATA, hasRead);
    }
}
