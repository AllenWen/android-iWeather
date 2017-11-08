package cn.allen.iweather.utils;

import android.content.Context;

import java.io.InputStream;

import cn.allen.iweather.db.entity.City;
import cn.allen.iweather.db.entity.Country;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class AssetManager {

    private static AssetManager instance = null;

    public static AssetManager getInstance(Context context) {
        if (instance == null) {
            instance = new AssetManager(context.getApplicationContext());
        }
        return instance;
    }

    private AssetManager(Context context) {
        if (PreferencesUtil.getBoolean(context, PreferencesUtil.KEY_IS_READ_DATA,true)) {
            readExcelToDB(context);
        }
    }

    private void readExcelToDB(Context context) {
        try {
            InputStream is = context.getAssets().open("data.xls");
            Workbook wb = Workbook.getWorkbook(is);
            wb.getNumberOfSheets();
            // 获得第一个工作表对象--国家
            Sheet sheet1 = wb.getSheet(0);
            int rows1 = sheet1.getRows();
            Country country = null;
            for (int i = 1; i < rows1; ++i) {
                String code = (sheet1.getCell(0, i)).getContents();
                String name_zh = (sheet1.getCell(1, i)).getContents();
                String name_en = (sheet1.getCell(2, i)).getContents();
//                String name_zh_tw = (sheet1.getCell(3, i)).getContents();
                String continent = (sheet1.getCell(4, i)).getContents();

                country = new Country(name_zh, name_en, code, continent);
                saveCountry2DB(country);
            }
            // 获得第一个工作表对象--城市
            Sheet sheet2 = wb.getSheet(1);
            int rows2 = sheet1.getRows();
            City city = null;
            for (int i = 1; i < rows2; ++i) {
                String id = (sheet1.getCell(0, i)).getContents();
                String name_zh = (sheet1.getCell(1, i)).getContents();
                String name_en = (sheet1.getCell(2, i)).getContents();
                String country_name = (sheet1.getCell(3, i)).getContents();
                String country_code = (sheet1.getCell(4, i)).getContents();
                String province_zh = (sheet1.getCell(5, i)).getContents();
                String province_en = (sheet1.getCell(6, i)).getContents();
                String city_zh = (sheet1.getCell(7, i)).getContents();
                String city_en = (sheet1.getCell(8, i)).getContents();
//                String time_zone = (sheet1.getCell(9, i)).getContents();
//                String level = (sheet1.getCell(10, i)).getContents();

                city = new City(id, name_zh, name_en, country_name, country_code, province_zh, province_en, city_zh, city_en);
                saveCity2DB(city);
            }

            wb.close();
            PreferencesUtil.putBoolean(context, PreferencesUtil.KEY_IS_READ_DATA, false);
        } catch (Exception e) {
            PreferencesUtil.putBoolean(context, PreferencesUtil.KEY_IS_READ_DATA, true);
            e.printStackTrace();
        }
    }

    private void saveCountry2DB(Country country) {

    }

    private void saveCity2DB(City city) {

    }


}
