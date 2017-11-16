package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cn.allen.iweather.repository.CityRepository;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/16
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class RegionCityViewModel extends ViewModel {

    public LiveData<List<String>> getCities(String province) {
        return CityRepository.getInstance().getCities(province);
    }
}
