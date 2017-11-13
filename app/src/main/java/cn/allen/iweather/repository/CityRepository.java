package cn.allen.iweather.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cn.allen.iweather.persistence.database.AppDatabase;
import cn.allen.iweather.persistence.entity.CityEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class CityRepository {

    public void insertCity(CityEntity cityEntity) {
        AppDatabase.getInstance().cityDao().insertCity(cityEntity);
    }

    public LiveData<List<CityEntity>> loadAllCities() {
        return AppDatabase.getInstance().cityDao().loadAllCities();
    }

    public LiveData<List<CityEntity>> loadCitiesByCountry(String country_name) {
        return AppDatabase.getInstance().cityDao().loadCitiesByCountry(country_name);
    }

    public LiveData<List<CityEntity>> loadCitiesByProvince(String province_zh) {
        return AppDatabase.getInstance().cityDao().loadCitiesByProvince(province_zh);
    }

    public LiveData<List<CityEntity>> loadCitiesByCity(String city_zh) {
        return AppDatabase.getInstance().cityDao().loadCitiesByCity(city_zh);
    }

    public LiveData<List<String>> getProvinces(String country_name) {
        return AppDatabase.getInstance().cityDao().getProvinces(country_name);
    }

    public LiveData<List<String>> getCities(String province_zh) {
        return AppDatabase.getInstance().cityDao().getCities(province_zh);
    }

}