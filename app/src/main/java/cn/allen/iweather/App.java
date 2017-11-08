package cn.allen.iweather;

import android.app.Application;

import cn.allen.iweather.utils.AssetsManager;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                AssetsManager.getInstance(getApplicationContext()).readExcelToDB();
            }
        }).start();

    }
}
