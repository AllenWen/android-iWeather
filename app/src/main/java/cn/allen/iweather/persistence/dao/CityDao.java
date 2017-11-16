package cn.allen.iweather.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.allen.iweather.persistence.entity.CityEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

@Dao
public interface CityDao {

    /**
     * 查询城市
     */
    @Query("SELECT * FROM city WHERE name_zh = :name_zh")
    LiveData<List<CityEntity>> findCity(String name_zh);

    /**
     * 省
     */
    @Query("SELECT DISTINCT province_zh FROM city WHERE country_name = :country_name")
    LiveData<List<String>> getProvinces(String country_name);

    /**
     * 市
     */
    @Query("SELECT DISTINCT city_zh FROM city WHERE province_zh = :province_zh")
    LiveData<List<String>> getCities(String province_zh);

    /**
     * 县(地级市下)
     */
    @Query("SELECT * FROM city WHERE city_zh = :city_zh")
    LiveData<List<CityEntity>> getCounties(String city_zh);

    /**
     * 县(直辖市下)
     */
    @Query("SELECT * FROM city WHERE province_zh = :province_zh")
    LiveData<List<CityEntity>> getMunicipalCounties(String province_zh);

}
