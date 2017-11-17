package cn.allen.iweather.webservice.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/17
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class LifeSuggestEntity extends BaseEntity implements Parcelable {
    @SerializedName("location")
    private LocationEntity location;
    @SerializedName("suggestion")
    private SuggestEntity suggestion;
    @SerializedName("last_update")
    private String last_update;

    public LocationEntity getLocation() {
        return location;
    }

    public SuggestEntity getSuggestion() {
        return suggestion;
    }

    public String getLast_update() {
        return last_update;
    }

    @Override
    public String toString() {
        return "LifeSuggestEntity{" +
                "location=" + location +
                ", suggestion=" + suggestion +
                ", last_update='" + last_update + '\'' +
                '}';
    }

    public static class SuggestEntity implements Parcelable {
        @SerializedName("car_washing")
        private SuggestItem car_washing;
        @SerializedName("dressing")
        private SuggestItem dressing;
        @SerializedName("flu")
        private SuggestItem flu;
        @SerializedName("sport")
        private SuggestItem sport;
        @SerializedName("travel")
        private SuggestItem travel;
        @SerializedName("uv")
        private SuggestItem uv;

        public SuggestItem getCar_washing() {
            return car_washing;
        }

        public SuggestItem getDressing() {
            return dressing;
        }

        public SuggestItem getFlu() {
            return flu;
        }

        public SuggestItem getSport() {
            return sport;
        }

        public SuggestItem getTravel() {
            return travel;
        }

        public SuggestItem getUv() {
            return uv;
        }

        @Override
        public String toString() {
            return "SuggestEntity{" +
                    "car_washing=" + car_washing +
                    ", dressing=" + dressing +
                    ", flu=" + flu +
                    ", sport=" + sport +
                    ", travel=" + travel +
                    ", uv=" + uv +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.car_washing, flags);
            dest.writeParcelable(this.dressing, flags);
            dest.writeParcelable(this.flu, flags);
            dest.writeParcelable(this.sport, flags);
            dest.writeParcelable(this.travel, flags);
            dest.writeParcelable(this.uv, flags);
        }

        public SuggestEntity() {
        }

        protected SuggestEntity(Parcel in) {
            this.car_washing = in.readParcelable(SuggestItem.class.getClassLoader());
            this.dressing = in.readParcelable(SuggestItem.class.getClassLoader());
            this.flu = in.readParcelable(SuggestItem.class.getClassLoader());
            this.sport = in.readParcelable(SuggestItem.class.getClassLoader());
            this.travel = in.readParcelable(SuggestItem.class.getClassLoader());
            this.uv = in.readParcelable(SuggestItem.class.getClassLoader());
        }

        public static final Creator<SuggestEntity> CREATOR = new Creator<SuggestEntity>() {
            @Override
            public SuggestEntity createFromParcel(Parcel source) {
                return new SuggestEntity(source);
            }

            @Override
            public SuggestEntity[] newArray(int size) {
                return new SuggestEntity[size];
            }
        };
    }

    public static class SuggestItem implements Parcelable{
        @SerializedName("brief")
        private String brief;
        @SerializedName("details")
        private String details;

        public String getBrief() {
            return brief;
        }

        public String getDetails() {
            return details;
        }

        @Override
        public String toString() {
            return "SuggestItem{" +
                    "brief='" + brief + '\'' +
                    ", details='" + details + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.brief);
            dest.writeString(this.details);
        }

        public SuggestItem() {
        }

        protected SuggestItem(Parcel in) {
            this.brief = in.readString();
            this.details = in.readString();
        }

        public static final Creator<SuggestItem> CREATOR = new Creator<SuggestItem>() {
            @Override
            public SuggestItem createFromParcel(Parcel source) {
                return new SuggestItem(source);
            }

            @Override
            public SuggestItem[] newArray(int size) {
                return new SuggestItem[size];
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
        dest.writeParcelable(this.suggestion, flags);
        dest.writeString(this.last_update);
    }

    public LifeSuggestEntity() {
    }

    protected LifeSuggestEntity(Parcel in) {
        super(in);
        this.location = in.readParcelable(LocationEntity.class.getClassLoader());
        this.suggestion = in.readParcelable(SuggestEntity.class.getClassLoader());
        this.last_update = in.readString();
    }

    public static final Creator<LifeSuggestEntity> CREATOR = new Creator<LifeSuggestEntity>() {
        @Override
        public LifeSuggestEntity createFromParcel(Parcel source) {
            return new LifeSuggestEntity(source);
        }

        @Override
        public LifeSuggestEntity[] newArray(int size) {
            return new LifeSuggestEntity[size];
        }
    };
}
