package cn.allen.iweather.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.allen.iweather.db.entity.CityEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

@Dao
public interface CityDao {

    /**
     * 插入城市
     *
     * @param cityEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCity(CityEntity cityEntity);

    /**
     * 查询所有城市
     *
     * @return
     */
    @Query("SELECT * FROM city")
    public LiveData<List<CityEntity>> loadAllCities();

    /**
     * 根据国家查询城市
     *
     * @param country_name
     * @return
     */
    @Query("SELECT * FROM city WHERE country_name = :country_name")
    public LiveData<List<CityEntity>> loadCitiesByCountry(String country_name);

    /**
     * 根据省级行政区查询城市
     *
     * @param province_zh
     * @return
     */
    @Query("SELECT * FROM city WHERE province_zh = :province_zh")
    public LiveData<List<CityEntity>> loadCitiesByProvince(String province_zh);

    /**
     * 根据地级行政区查询城市
     *
     * @param city_zh
     * @return
     */
    @Query("SELECT * FROM city WHERE city_zh = :city_zh")
    public LiveData<List<CityEntity>> loadCitiesByCity(String city_zh);

    /**
     * 获取一个国家的省级行政区
     *
     * @return
     */
    @Query("SELECT DISTINCT province_zh FROM city WHERE country_name = :country_name")
    public LiveData<List<String>> getProvinces(String country_name);

    /**
     * 获取一个省级行政区的地级行政区
     *
     * @return
     */
    @Query("SELECT DISTINCT city_zh FROM city WHERE province_zh = :province_zh")
    public LiveData<List<String>> getCities(String province_zh);

}
