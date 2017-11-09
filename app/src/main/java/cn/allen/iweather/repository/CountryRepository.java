package cn.allen.iweather.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cn.allen.iweather.persistence.database.AppDatabase;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class CountryRepository {

    public LiveData<List<String>> getContinents() {
        return AppDatabase.getInstance().countryDao().getContinents();
    }
}
