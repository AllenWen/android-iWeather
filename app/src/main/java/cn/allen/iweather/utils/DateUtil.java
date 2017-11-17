package cn.allen.iweather.utils;

import android.content.Context;

import java.util.Calendar;

import cn.allen.iweather.R;

/**
 * Created by allen on 2017/11/17.
 */

public class DateUtil {

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    public static String getWeek(Context context, Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = context.getString(R.string.sunday);
                break;
            case 2:
                week = context.getString(R.string.monday);
                break;
            case 3:
                week = context.getString(R.string.tuesday);
                break;
            case 4:
                week = context.getString(R.string.wednesday);
                break;
            case 5:
                week = context.getString(R.string.thursday);
                break;
            case 6:
                week = context.getString(R.string.friday);
                break;
            case 7:
                week = context.getString(R.string.saturday);
                break;
        }
        return week;
    }
}
