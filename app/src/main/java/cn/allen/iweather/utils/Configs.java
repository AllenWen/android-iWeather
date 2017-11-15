package cn.allen.iweather.utils;

/**
 * Created by allen on 2017/11/12.
 */

public interface Configs {
    String KEY = "7melhqh9txwphnjt";
    String LANG = "zh-Hans";
    String UNIT = "c";// 当参数为c时，温度c、风速km/h、能见度km、气压mb；当参数为f时，温度f、风速mph、能见度mile、气压inch
    int LIMIT = 50;
    int OFFSET = 0;
}
