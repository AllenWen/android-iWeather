package cn.allen.iweather;

import android.app.Application;

import cn.allen.iweather.persistence.database.AppDatabase;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class App extends Application {
    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        AppDatabase.init();
    }

    public static App getAppContext() {
        return mApp;
    }
}
