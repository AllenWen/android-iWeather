package cn.allen.iweather.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.io.InputStream;

import cn.allen.iweather.db.datebase.AppDatabase;
import cn.allen.iweather.db.entity.CityEntity;
import cn.allen.iweather.db.entity.CountryEntity;
import cn.allen.iweather.repository.SpRepository;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class AssetsManager {
    private static AssetsManager mInstance;

    public static AssetsManager getInstance() {
        if (mInstance == null) {
            mInstance = new AssetsManager();
        }
        return mInstance;
    }

    private AssetsManager() {
    }

    public void readExcelToDB(Context context) {
        try {
            InputStream is = context.getAssets().open("data.xls");
            Workbook wb = Workbook.getWorkbook(is);
            wb.getNumberOfSheets();
            // 获得第一个工作表对象--国家
            Sheet sheet1 = wb.getSheet(0);
            int rows1 = sheet1.getRows();
            CountryEntity countryEntity;
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
            CityEntity cityEntity;
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
            SpRepository.getInstance().setReadData(context, true);
        } catch (Exception e) {
            SpRepository.getInstance().setReadData(context, false);
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void saveCountry2DB(final CountryEntity countryEntity) {
        new AsyncTask<CountryEntity, Integer, Integer>() {

            @Override
            protected Integer doInBackground(CountryEntity... entities) {
                AppDatabase.getInstance().countryDao().insertCountry(entities[0]);
                return 0;
            }
        }.execute(countryEntity);
    }

    @SuppressLint("StaticFieldLeak")
    private void saveCity2DB(final CityEntity cityEntity) {
        new AsyncTask<CityEntity, Integer, Integer>() {

            @Override
            protected Integer doInBackground(CityEntity... entities) {
                AppDatabase.getInstance().cityDao().insertCity(entities[0]);
                return 0;
            }
        }.execute(cityEntity);
    }

}
