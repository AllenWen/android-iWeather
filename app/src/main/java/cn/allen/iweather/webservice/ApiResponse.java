package cn.allen.iweather.webservice;

import retrofit2.Response;

/**
 * Created by allen on 2017/11/9.
 */

public class ApiResponse<T> {
    public ApiResponse(Throwable error) {
    }

    public ApiResponse(Response<T> response) {
    }
}

