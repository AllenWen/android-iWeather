package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.repository.CityRepository;
import cn.allen.iweather.repository.FavoriteRepository;

/**
 * Created by allen on 2017/11/16.
 */

public class RegionCountyViewModel extends ViewModel {

    public LiveData<List<CityEntity>> getCounties(String city) {
        return CityRepository.getInstance().getCounties(city);
    }

    public void insertFavorite(final FavoriteEntity favoriteEntity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FavoriteRepository.getInstance().insertFavorite(favoriteEntity);
            }
        }).start();
    }
}
