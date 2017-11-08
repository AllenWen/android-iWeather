package cn.allen.iweather.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/8
 * Email: wenxueguo@medlinker.com
 * Description:
 */
@Entity(tableName = "country")
public class Country {
    @PrimaryKey
    public String name_zh;
    public String name_en;
    public String code;
    public String continent;

    public Country(String name_zh, String name_en, String code, String continent) {
        this.name_zh = name_zh;
        this.name_en = name_en;
        this.code = code;
        this.continent = continent;
    }
}
