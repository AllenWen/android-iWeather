package cn.allen.iweather.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cn.allen.iweather.persistence.database.AppDatabase;
import cn.allen.iweather.persistence.entity.CountryEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class CountryRepository {

    public void insertCountry(CountryEntity countryEntity) {
        AppDatabase.getInstance().countryDao().insertCountry(countryEntity);
    }

    public LiveData<List<CountryEntity>> loadAllCountries() {
        return AppDatabase.getInstance().countryDao().loadAllCountries();
    }

    public LiveData<List<CountryEntity>> loadCountriesByContinent(String continent) {
        return AppDatabase.getInstance().countryDao().loadCountriesByContinent(continent);
    }

    public LiveData<List<String>> getContinents() {
        return AppDatabase.getInstance().countryDao().getContinents();
    }

}
