package cn.allen.iweather.webservice;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class Network {
    public static final String TAG = Network.class.getSimpleName();
    public static final String BASE_URL = "https://api.seniverse.com/v3/";

    private static Retrofit mRetrofit;
    private static Network mNetwork;
    private static Api mApi;

    public static Network instance() {
        if (mNetwork == null) {
            mNetwork = new Network();
        }
        return mNetwork;
    }

    private Network() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(buildHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }

    private OkHttpClient buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG,"OkHttp====LOG:"+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//日志显示级别
        builder.addNetworkInterceptor(loggingInterceptor);
        return  builder.build();
    }

    public Api getApi() {
        if (mApi == null) {
            mApi = mRetrofit.create(Api.class);
        }
        return mApi;
    }
}
