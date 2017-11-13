package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.repository.CityRepository;
import cn.allen.iweather.repository.FavoriteRepository;

/**
 * Created by allen on 2017/11/12.
 */

public class AddCityViewModel extends ViewModel {

    public AddCityViewModel() {
    }

    public LiveData<List<CityEntity>> searchCity(String name) {
        return CityRepository.getInstance().loadCitiesByName(name);
    }

    public void insertFavorite(FavoriteEntity favoriteEntity) {
        FavoriteRepository.getInstance().insertFavorite(favoriteEntity);
    }

}
