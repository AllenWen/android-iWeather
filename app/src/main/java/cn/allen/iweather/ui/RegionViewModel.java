package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.repository.CityRepository;
import cn.allen.iweather.repository.FavoriteRepository;
import cn.allen.iweather.utils.Configs;

/**
 * Created by allen on 2017/11/12.
 */

public class RegionViewModel extends ViewModel {

    public void insertFavorite(final FavoriteEntity favoriteEntity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FavoriteRepository.getInstance().insertFavorite(favoriteEntity);
            }
        }).start();
    }

    public LiveData<List<String>> getProvinces() {
        return CityRepository.getInstance().getProvinces(Configs.COUNTRY);
    }

    public LiveData<List<CityEntity>> getCounties(String city_zh) {
        return CityRepository.getInstance().getCounties(city_zh);
    }

}
