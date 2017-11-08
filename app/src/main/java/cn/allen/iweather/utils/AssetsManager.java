package cn.allen.iweather.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.allen.iweather.db.datebase.AppDatabase;
import cn.allen.iweather.db.entity.CityEntity;
import cn.allen.iweather.db.entity.CountryEntity;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class AssetsManager {

    private static AssetsManager mInstance = null;
    private static Context mContext;
    private Handler mHandler;



    public static AssetsManager getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new AssetsManager(context.getApplicationContext());
        }
        return mInstance;
    }

    private AssetsManager(Context context) {
        Looper.prepare();
        mHandler=new Handler();
    }

    public void readExcelToDB() {
        if (PreferencesUtil.getBoolean(mContext, PreferencesUtil.KEY_IS_READ_DATA, true)) {
            try {
                InputStream is = mContext.getAssets().open("data.xls");
                Workbook wb = Workbook.getWorkbook(is);
                wb.getNumberOfSheets();
                // 获得第一个工作表对象--国家
                Sheet sheet1 = wb.getSheet(0);
                int rows1 = sheet1.getRows();
                CountryEntity countryEntity = null;
                for (int i = 1; i < rows1; ++i) {
                    String code = (sheet1.getCell(0, i)).getContents();
                    String name_zh = (sheet1.getCell(1, i)).getContents();
                    String name_en = (sheet1.getCell(2, i)).getContents();
//                String name_zh_tw = (sheet1.getCell(3, i)).getContents();
                    String continent = (sheet1.getCell(4, i)).getContents();

                    countryEntity = new CountryEntity(name_zh, name_en, code, continent);
                    saveCountry2DB(countryEntity);
                }
                // 获得第一个工作表对象--城市
                Sheet sheet2 = wb.getSheet(1);
                int rows2 = sheet2.getRows();
                CityEntity cityEntity = null;
                for (int i = 1; i < rows2; ++i) {
                    String id = (sheet2.getCell(0, i)).getContents();
                    String name_zh = (sheet2.getCell(1, i)).getContents();
                    String name_en = (sheet2.getCell(2, i)).getContents();
                    String country_name = (sheet2.getCell(3, i)).getContents();
                    String country_code = (sheet2.getCell(4, i)).getContents();
                    String province_zh = (sheet2.getCell(5, i)).getContents();
                    String province_en = (sheet2.getCell(6, i)).getContents();
                    String city_zh = (sheet2.getCell(7, i)).getContents();
                    String city_en = (sheet2.getCell(8, i)).getContents();
//                String time_zone = (sheet2.getCell(9, i)).getContents();
//                String level = (sheet2.getCell(10, i)).getContents();

                    cityEntity = new CityEntity(id, name_zh, name_en, country_name, country_code, province_zh, province_en, city_zh, city_en);
                    saveCity2DB(cityEntity);
                }

                wb.close();
                PreferencesUtil.putBoolean(mContext, PreferencesUtil.KEY_IS_READ_DATA, false);
            } catch (Exception e) {
                PreferencesUtil.putBoolean(mContext, PreferencesUtil.KEY_IS_READ_DATA, true);
                e.printStackTrace();
            }
        }
    }

    private void saveCountry2DB(final CountryEntity countryEntity) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(mContext).countryDao().insertCountry(countryEntity);
            }
        });
    }

    private void saveCity2DB(final CityEntity cityEntity) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(mContext).cityDao().insertCity(cityEntity);
            }
        });
    }

}
