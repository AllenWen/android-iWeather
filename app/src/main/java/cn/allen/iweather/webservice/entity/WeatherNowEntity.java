package cn.allen.iweather.webservice.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/10
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class WeatherNowEntity extends BaseEntity implements Parcelable{
    @SerializedName("location")
    private LocationEntity location;
    @SerializedName("now")
    private NowEntity now;
    @SerializedName("last_update")
    private String last_update;

    public WeatherNowEntity(LocationEntity location, NowEntity now, String last_update) {
        this.location = location;
        this.now = now;
        this.last_update = last_update;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public NowEntity getNow() {
        return now;
    }

    public void setNow(NowEntity now) {
        this.now = now;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "WeatherNowEntity{" +
                "location=" + location +
                ", now=" + now +
                ", last_update='" + last_update + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeParcelable(this.now, flags);
        dest.writeString(this.last_update);
    }

    public WeatherNowEntity() {
    }

    protected WeatherNowEntity(Parcel in) {
        super(in);
        this.location = in.readParcelable(LocationEntity.class.getClassLoader());
        this.now = in.readParcelable(NowEntity.class.getClassLoader());
        this.last_update = in.readString();
    }

    public static final Creator<WeatherNowEntity> CREATOR = new Creator<WeatherNowEntity>() {
        @Override
        public WeatherNowEntity createFromParcel(Parcel source) {
            return new WeatherNowEntity(source);
        }

        @Override
        public WeatherNowEntity[] newArray(int size) {
            return new WeatherNowEntity[size];
        }
    };

    public static class NowEntity implements Parcelable {
        @SerializedName("text")
        private String text;//天气现象文字
        @SerializedName("code")
        private int code;//天气现象代码
        @SerializedName("temperature")
        private int temperature;//温度，单位为c摄氏度或f华氏度

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return "NowEntity{" +
                    "text='" + text + '\'' +
                    ", code=" + code +
                    ", temperature=" + temperature +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.text);
            dest.writeInt(this.code);
            dest.writeInt(this.temperature);
        }

        public NowEntity() {
        }

        protected NowEntity(Parcel in) {
            this.text = in.readString();
            this.code = in.readInt();
            this.temperature = in.readInt();
        }

        public static final Creator<NowEntity> CREATOR = new Creator<NowEntity>() {
            @Override
            public NowEntity createFromParcel(Parcel source) {
                return new NowEntity(source);
            }

            @Override
            public NowEntity[] newArray(int size) {
                return new NowEntity[size];
            }
        };
    }

}
