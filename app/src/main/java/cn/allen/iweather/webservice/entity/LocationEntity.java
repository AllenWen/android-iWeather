package cn.allen.iweather.webservice.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by allen on 2017/11/12.
 */

public class LocationEntity implements Parcelable{
    @SerializedName("id")
    private String id;//城市代码
    @SerializedName("name")
    private String name;//城市名称
    @SerializedName("country")
    private String country;//国家代码
    @SerializedName("path")
    private String path;//完整行政等级
    @SerializedName("timezone")
    private String timezone;//时区
    @SerializedName("timezone_offset")
    private String timezone_offset;//时区偏移

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(String timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", path='" + path + '\'' +
                ", timezone='" + timezone + '\'' +
                ", timezone_offset='" + timezone_offset + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.country);
        dest.writeString(this.path);
        dest.writeString(this.timezone);
        dest.writeString(this.timezone_offset);
    }

    public LocationEntity() {
    }

    protected LocationEntity(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.country = in.readString();
        this.path = in.readString();
        this.timezone = in.readString();
        this.timezone_offset = in.readString();
    }

    public static final Creator<LocationEntity> CREATOR = new Creator<LocationEntity>() {
        @Override
        public LocationEntity createFromParcel(Parcel source) {
            return new LocationEntity(source);
        }

        @Override
        public LocationEntity[] newArray(int size) {
            return new LocationEntity[size];
        }
    };
}
