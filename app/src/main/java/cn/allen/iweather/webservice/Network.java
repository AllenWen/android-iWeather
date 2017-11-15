package cn.allen.iweather.webservice;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "OkHttp====LOG:" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);//日志显示级别
        builder.addNetworkInterceptor(loggingInterceptor);
        builder.addInterceptor(new LoggingInterceptor());
        return builder.build();
    }

    public Api getApi() {
        if (mApi == null) {
            mApi = mRetrofit.create(Api.class);
        }
        return mApi;
    }

    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            //不能直接使用response.body().string()的方式输出日志，
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d(TAG, responseBody.string());
            return response;
        }
    }
}
