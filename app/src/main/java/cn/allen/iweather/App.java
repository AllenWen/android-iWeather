package cn.allen.iweather;

import android.app.Application;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        readAssets();
    }

    private void readAssets() {

    }
}
