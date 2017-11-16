package cn.allen.iweather.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import java.util.List;

import cn.allen.iweather.persistence.entity.FavoriteEntity;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/13
 * Email: wenxueguo@medlinker.com
 * Description:
 */

@Dao
public interface FavoriteDao {

    /**
     * 插入城市
     *
     * @param favoriteEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFavorite(FavoriteEntity favoriteEntity);

    /**
     * 查询所有城市
     *
     * @return
     */
    @Query("SELECT * FROM favorite")
    public LiveData<List<FavoriteEntity>> loadFavorites();

    /**
     * 删除城市
     *
     * @param id
     */
    @Query("DELETE FROM favorite WHERE id=:id")
    public void deleteFavorite(String id);
}
