package cn.allen.iweather.repository;

import android.arch.lifecycle.LiveData;

import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.Network;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.LifeSuggestEntity;
import cn.allen.iweather.webservice.entity.WeatherDailyEntity;
import cn.allen.iweather.webservice.entity.WeatherNowEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class WeatherRepository {
    private static WeatherRepository mInstance;

    public static WeatherRepository getInstance() {
        if (mInstance == null) {
            mInstance = new WeatherRepository();
        }
        return mInstance;
    }

    public LiveData<ApiResponse<BaseWrapperEntity<WeatherNowEntity>>> now(String key, String location, String language, String unit) {
        return Network.instance().getApi().now(key, location, language, unit);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<WeatherDailyEntity>>> daily(String key, String location, String language, String unit, int start, int end) {
        return Network.instance().getApi().daily(key, location, language, unit, start, end);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<LifeSuggestEntity>>> life(String key, String location, String language) {
        return Network.instance().getApi().life(key, location, language);
    }

}
