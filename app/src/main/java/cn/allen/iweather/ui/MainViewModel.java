package cn.allen.iweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import cn.allen.iweather.repository.CountryRepository;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/9
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class MainViewModel extends ViewModel {
    CountryRepository mCountryRepository;

    public MainViewModel() {
        mCountryRepository = new CountryRepository();
    }

    public LiveData<List<String>> getContinents() {
        return mCountryRepository.getContinents();
    }

}
