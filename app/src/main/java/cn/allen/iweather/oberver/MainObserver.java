package cn.allen.iweather.oberver;

import android.content.Context;

import cn.allen.iweather.repository.SpRepository;
import cn.allen.iweather.utils.AssetsManager;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class MainObserver implements BaseLifecycleObserver {
    private Context mContext;

    public MainObserver(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        if (SpRepository.getInstance().hasReadData(mContext)) {
            return;
        }
        AssetsManager.getInstance().readExcelToDB(mContext);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
