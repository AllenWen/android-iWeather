package cn.allen.iweather.webservice.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/17
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class WeatherDailyEntity extends BaseEntity implements Parcelable {
    @SerializedName("location")
    private LocationEntity location;
    @SerializedName("daily")
    private List<DailyEntity> daily;
    @SerializedName("last_update")
    private String last_update;

    public LocationEntity getLocation() {
        return location;
    }

    public List<DailyEntity> getDaily() {
        return daily;
    }

    public String getLast_update() {
        return last_update;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeTypedList(this.daily);
        dest.writeString(this.last_update);
    }

    public WeatherDailyEntity() {
    }

    protected WeatherDailyEntity(Parcel in) {
        super(in);
        this.location = in.readParcelable(LocationEntity.class.getClassLoader());
        this.daily = in.createTypedArrayList(DailyEntity.CREATOR);
        this.last_update = in.readString();
    }

    public static final Creator<WeatherDailyEntity> CREATOR = new Creator<WeatherDailyEntity>() {
        @Override
        public WeatherDailyEntity createFromParcel(Parcel source) {
            return new WeatherDailyEntity(source);
        }

        @Override
        public WeatherDailyEntity[] newArray(int size) {
            return new WeatherDailyEntity[size];
        }
    };

    public static class DailyEntity implements Parcelable {
        @SerializedName("date")
        private String date; //日期
        @SerializedName("text_day")
        private String text_day; //白天天气现象文字
        @SerializedName("code_day")
        private int code_day; //白天天气现象代码
        @SerializedName("text_night")
        private String text_night; //晚间天气现象文字
        @SerializedName("code_night")
        private int code_night; //晚间天气现象代码
        @SerializedName("high")
        private int high; //当天最高温度
        @SerializedName("low")
        private int low; //当天最低温度
//        @SerializedName("precip")
//        private int precip; //降水概率，范围0~100，单位百分比
        @SerializedName("wind_direction")
        private String wind_direction; //风向文字
//        @SerializedName("wind_direction_degree")
//        private int wind_direction_degree; //风向角度，范围0~360
        @SerializedName("wind_speed")
        private int wind_speed; //风速，单位km/h（当unit=c时）、mph（当unit=f时）
        @SerializedName("wind_scale")
        private int wind_scale; //风力等级

        public String getDate() {
            return date;
        }

        public String getText_day() {
            return text_day;
        }

        public int getCode_day() {
            return code_day;
        }

        public String getText_night() {
            return text_night;
        }

        public int getCode_night() {
            return code_night;
        }

        public int getHigh() {
            return high;
        }

        public int getLow() {
            return low;
        }

//        public int getPrecip() {
//            return precip;
//        }

        public String getWind_direction() {
            return wind_direction;
        }

//        public int getWind_direction_degree() {
//            return wind_direction_degree;
//        }

        public int getWind_speed() {
            return wind_speed;
        }

        public int getWind_scale() {
            return wind_scale;
        }

        @Override
        public String toString() {
            return "Daily{" +
                    "date='" + date + '\'' +
                    ", text_day='" + text_day + '\'' +
                    ", code_day=" + code_day +
                    ", text_night='" + text_night + '\'' +
                    ", code_night=" + code_night +
                    ", high=" + high +
                    ", low=" + low +
//                    ", precip=" + precip +
                    ", wind_direction='" + wind_direction + '\'' +
//                    ", wind_direction_degree=" + wind_direction_degree +
                    ", wind_speed=" + wind_speed +
                    ", wind_scale=" + wind_scale +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.date);
            dest.writeString(this.text_day);
            dest.writeInt(this.code_day);
            dest.writeString(this.text_night);
            dest.writeInt(this.code_night);
            dest.writeInt(this.high);
            dest.writeInt(this.low);
//            dest.writeInt(this.precip);
            dest.writeString(this.wind_direction);
//            dest.writeInt(this.wind_direction_degree);
            dest.writeInt(this.wind_speed);
            dest.writeInt(this.wind_scale);
        }

        public DailyEntity() {
        }

        protected DailyEntity(Parcel in) {
            this.date = in.readString();
            this.text_day = in.readString();
            this.code_day = in.readInt();
            this.text_night = in.readString();
            this.code_night = in.readInt();
            this.high = in.readInt();
            this.low = in.readInt();
//            this.precip = in.readInt();
            this.wind_direction = in.readString();
//            this.wind_direction_degree = in.readInt();
            this.wind_speed = in.readInt();
            this.wind_scale = in.readInt();
        }

        public static final Creator<DailyEntity> CREATOR = new Creator<DailyEntity>() {
            @Override
            public DailyEntity createFromParcel(Parcel source) {
                return new DailyEntity(source);
            }

            @Override
            public DailyEntity[] newArray(int size) {
                return new DailyEntity[size];
            }
        };
    }

}
