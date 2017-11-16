package cn.allen.iweather.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cn.allen.iweather.persistence.database.AppDatabase;
import cn.allen.iweather.persistence.entity.FavoriteEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/13
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class FavoriteRepository {
    private static FavoriteRepository mInstance;

    public static FavoriteRepository getInstance() {
        if (mInstance == null) {
            mInstance = new FavoriteRepository();
        }
        return mInstance;
    }

    public void insertFavorite(FavoriteEntity favoriteEntity) {
        AppDatabase.getInstance().favoriteDao().insertFavorite(favoriteEntity);
    }

    public LiveData<List<FavoriteEntity>> loadFavorites() {
        return AppDatabase.getInstance().favoriteDao().loadFavorites();
    }

    public void deleteFavorite(String id) {
        AppDatabase.getInstance().favoriteDao().deleteFavorite(id);
    }
}
