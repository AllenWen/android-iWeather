package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import cn.allen.iweather.repository.WeatherRepository;
import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.WeatherNowEntity;

/**
 * Created by allen on 2017/11/12.
 */

public class AddCityViewModel extends ViewModel {

    public AddCityViewModel() {
    }

    public LiveData<ApiResponse<BaseWrapperEntity<WeatherNowEntity>>> now(String key, String location, String language, String unit){
        return WeatherRepository.getInstance().now(key, location, language, unit);
    }
}
