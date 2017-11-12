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

public class WeatherNowEntity extends BaseEntity {
    @SerializedName("location")
    private LocationEntity location;
    @SerializedName("now")
    private NowEntity now;
    @SerializedName("last_update")
    private String last_update;

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



    public class NowEntity implements Parcelable {
        @SerializedName("text")
        private String text;//天气现象文字
        @SerializedName("code")
        private int code;//天气现象代码
        @SerializedName("temperature")
        private int temperature;//温度，单位为c摄氏度或f华氏度
        @SerializedName("feels_like")
        private int feels_like; //体感温度，单位为c摄氏度或f华氏度
        @SerializedName("pressure")
        private int pressure;//气压，单位为mb百帕或in英寸
        @SerializedName("humidity")
        private int humidity;//相对湿度，0~100，单位为百分比
        @SerializedName("visibility")
        private int visibility;//能见度，单位为km公里或mi英里
        @SerializedName("wind_direction")
        private String wind_direction;//风向文字
        @SerializedName("wind_direction_degree")
        private int wind_direction_degree;//风向角度，范围0~360，0为正北，90为正东，180为正南，270为正西
        @SerializedName("wind_speed")
        private int wind_speed;//风速，单位为km/h公里每小时或mph英里每小时
        @SerializedName("wind_scale")
        private int wind_scale;//风力等级，请参考：http://baike.baidu.com/view/465076.htm
        @SerializedName("clouds")
        private int clouds;//云量，范围0~100，天空被云覆盖的百分比 #目前不支持中国城市#
        @SerializedName("dew_point")
        private int dew_point;//露点温度，请参考：http://baike.baidu.com/view/118348.htm #目前不支持中国城市#

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

        public int getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(int feels_like) {
            this.feels_like = feels_like;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public String getWind_direction() {
            return wind_direction;
        }

        public void setWind_direction(String wind_direction) {
            this.wind_direction = wind_direction;
        }

        public int getWind_direction_degree() {
            return wind_direction_degree;
        }

        public void setWind_direction_degree(int wind_direction_degree) {
            this.wind_direction_degree = wind_direction_degree;
        }

        public int getWind_speed() {
            return wind_speed;
        }

        public void setWind_speed(int wind_speed) {
            this.wind_speed = wind_speed;
        }

        public int getWind_scale() {
            return wind_scale;
        }

        public void setWind_scale(int wind_scale) {
            this.wind_scale = wind_scale;
        }

        public int getClouds() {
            return clouds;
        }

        public void setClouds(int clouds) {
            this.clouds = clouds;
        }

        public int getDew_point() {
            return dew_point;
        }

        public void setDew_point(int dew_point) {
            this.dew_point = dew_point;
        }

        @Override
        public String toString() {
            return "NowEntity{" +
                    "text='" + text + '\'' +
                    ", code=" + code +
                    ", temperature=" + temperature +
                    ", feels_like=" + feels_like +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    ", visibility=" + visibility +
                    ", wind_direction='" + wind_direction + '\'' +
                    ", wind_direction_degree=" + wind_direction_degree +
                    ", wind_speed=" + wind_speed +
                    ", wind_scale=" + wind_scale +
                    ", clouds=" + clouds +
                    ", dew_point=" + dew_point +
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
            dest.writeInt(this.feels_like);
            dest.writeInt(this.pressure);
            dest.writeInt(this.humidity);
            dest.writeInt(this.visibility);
            dest.writeString(this.wind_direction);
            dest.writeInt(this.wind_direction_degree);
            dest.writeInt(this.wind_speed);
            dest.writeInt(this.wind_scale);
            dest.writeInt(this.clouds);
            dest.writeInt(this.dew_point);
        }

        public NowEntity() {
        }

        protected NowEntity(Parcel in) {
            this.text = in.readString();
            this.code = in.readInt();
            this.temperature = in.readInt();
            this.feels_like = in.readInt();
            this.pressure = in.readInt();
            this.humidity = in.readInt();
            this.visibility = in.readInt();
            this.wind_direction = in.readString();
            this.wind_direction_degree = in.readInt();
            this.wind_speed = in.readInt();
            this.wind_scale = in.readInt();
            this.clouds = in.readInt();
            this.dew_point = in.readInt();
        }

        public final Creator<NowEntity> CREATOR = new Creator<NowEntity>() {
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
}
