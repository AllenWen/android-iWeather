package cn.allen.iweather.db.datebase;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import cn.allen.iweather.db.dao.CityDao;
import cn.allen.iweather.db.dao.CountryDao;
import cn.allen.iweather.db.entity.CityEntity;
import cn.allen.iweather.db.entity.CountryEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */
@Database(entities = {CityEntity.class, CountryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase Instance;
    public static final String DATABASE_NAME = "weather_db";

    public abstract CityDao cityDao();

    public abstract CountryDao countryDao();

    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return Instance;
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
