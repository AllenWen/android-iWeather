package cn.allen.iweather.persistence.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.allen.iweather.App;
import cn.allen.iweather.persistence.dao.CityDao;
import cn.allen.iweather.persistence.dao.CountryDao;
import cn.allen.iweather.persistence.dao.FavoriteDao;
import cn.allen.iweather.persistence.entity.CityEntity;
import cn.allen.iweather.persistence.entity.CountryEntity;
import cn.allen.iweather.persistence.entity.FavoriteEntity;
import cn.allen.iweather.repository.SpRepository;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */
@Database(entities = {CityEntity.class, CountryEntity.class, FavoriteEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "weather_db";
    private static AppDatabase Instance;

    public static AppDatabase getInstance() {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
                checkCopyDB();
            }
        }
        return Instance;
    }

    public static void init() {
        Log.d(TAG, "database init...");
        Instance = Room.databaseBuilder(App.getAppContext(), AppDatabase.class, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
        checkCopyDB();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `favorite` (`id` TEXT, `name_zh` TEXT, `name_en` TEXT, `country_name` TEXT, `country_code` TEXT, "
//                    + "`province_zh` TEXT, `province_en` TEXT, `city_zh` TEXT, `city_en` TEXT, PRIMARY KEY(`id`))");
        }
    };

    private static void checkCopyDB() {
        if (SpRepository.getInstance().hasCopyDB(App.getAppContext())) {
            Log.d(TAG, "database already copied !");
            return;
        }
        Log.d(TAG, "database copying...");
        File file = App.getAppContext().getDatabasePath(DATABASE_NAME);
        try {
            AssetManager am = App.getAppContext().getAssets();
            InputStream is = am.open("weather_db");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
            is.close();
            Log.d(TAG, "copy database success !");
            SpRepository.getInstance().setCopyDB(App.getAppContext(), true);
        } catch (IOException e) {
            Log.d(TAG, "copy database failed !");
            e.printStackTrace();
        }
    }

    public abstract CityDao cityDao();

    public abstract CountryDao countryDao();

    public abstract FavoriteDao favoriteDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
