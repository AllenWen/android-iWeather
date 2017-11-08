package cn.allen.iweather.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.allen.iweather.db.entity.CountryEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

@Dao
public interface CountryDao {

    /**
     * 插入国家
     *
     * @param countryEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCountry(CountryEntity countryEntity);

    /**
     * 查询所有国家
     *
     * @return
     */
    @Query("SELECT * FROM country")
    public LiveData<List<CountryEntity>> loadAllCountries();

    /**
     * 根据大洲查询国家
     *
     * @param continent
     * @return
     */
    @Query("SELECT * FROM country WHERE continent = :continent")
    public LiveData<List<CountryEntity>> loadCountriesByContinent(String continent);

    /**
     * 获取大洲种类
     *
     * @return
     */
    @Query("SELECT DISTINCT continent FROM country")
    public LiveData<List<String>> getContinents();

}
