package cn.allen.iweather.webservice.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allen on 2017/11/12.
 */

public class BaseEntity implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BaseEntity() {
    }

    protected BaseEntity(Parcel in) {
    }

    public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
        @Override
        public BaseEntity createFromParcel(Parcel in) {
            return new BaseEntity(in);
        }

        @Override
        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };
}
