package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.repository.CityRepository;
import cn.allen.iweather.repository.FavoriteRepository;
import cn.allen.iweather.utils.Configs;
import cn.allen.iweather.webservice.ApiResponse;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.LocationEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/15
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class AddRemoteViewModel extends ViewModel {

    public LiveData<ApiResponse<BaseWrapperEntity<LocationEntity>>> searchCity(String q) {
        return CityRepository.getInstance().searchCity(Configs.KEY, q, Configs.LANG, Configs.LIMIT, Configs.OFFSET);
    }

    public void insertFavorite(FavoriteEntity favoriteEntity) {
        FavoriteRepository.getInstance().insertFavorite(favoriteEntity);
    }
}
