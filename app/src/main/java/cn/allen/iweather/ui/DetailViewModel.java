package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import cn.allen.iweather.repository.WeatherRepository;
import cn.allen.iweather.utils.Configs;
import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.LifeSuggestEntity;
import cn.allen.iweather.webservice.entity.WeatherDailyEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/17
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class DetailViewModel extends ViewModel {

    public LiveData<ApiResponse<BaseWrapperEntity<WeatherDailyEntity>>> daily(String location, int start, int end) {
        return WeatherRepository.getInstance().daily(Configs.KEY, location, Configs.LANG, Configs.UNIT, start, end);
    }

    public LiveData<ApiResponse<BaseWrapperEntity<LifeSuggestEntity>>> life(String location) {
        return WeatherRepository.getInstance().life(Configs.KEY, location, Configs.LANG);
    }

}
