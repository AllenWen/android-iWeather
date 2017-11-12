package cn.allen.iweather.repository;

import android.arch.lifecycle.LiveData;

import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.Network;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
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

    public LiveData<ApiResponse<BaseWrapperEntity<WeatherNowEntity>>> now( String key,  String location,String language,  String unit){
        return Network.instance().getApi().now(key, location, language, unit);
    }

}
