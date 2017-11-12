package cn.allen.iweather.webservice;

import android.arch.lifecycle.LiveData;

import cn.allen.iweather.webservice.entity.BaseEntity;
import cn.allen.iweather.webservice.entity.BaseWrapperEntity;
import cn.allen.iweather.webservice.entity.WeatherNowEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public interface Api {

    /**
     * 天气实况
     */
    @GET("weather/now.json")
    public LiveData<ApiResponse<BaseWrapperEntity<WeatherNowEntity>>> now(@Query("key") String key, @Query("location") String location, @Query("language") String language, @Query("unit") String unit);

    /**
     * 逐日天气预报和昨日天气
     */
    @GET("weather/daily.json")
    public void daily(@Query("key") String key, @Query("location") String location, @Query("language") String language, @Query("unit") String unit,
                      @Query("start") String start, @Query("days") String days);

    /**
     * 生活指数
     */
    @GET("life/suggestion.json")
    public void life(@Query("key") String key, @Query("location") String location, @Query("language") String language);
}
