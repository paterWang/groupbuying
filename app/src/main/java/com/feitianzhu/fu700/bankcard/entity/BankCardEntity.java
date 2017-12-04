package com.feitianzhu.fu700.bankcard.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lee on 2017/9/13.
 */

public class BankCardEntity implements Parcelable {

    /**
     * bankId : 1
     * bankName : 支付宝
     * icon : null
     */

    public int bankId;
    public String bankName;
    public String icon;

    @Override
    public String toString() {
        return "BankCardEntity{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bankId);
        dest.writeString(this.bankName);
        dest.writeString(this.icon);
    }

    public BankCardEntity() {
    }

    protected BankCardEntity(Parcel in) {
        this.bankId = in.readInt();
        this.bankName = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<BankCardEntity> CREATOR = new Parcelable.Creator<BankCardEntity>() {
        @Override
        public BankCardEntity createFromParcel(Parcel source) {
            return new BankCardEntity(source);
        }

        @Override
        public BankCardEntity[] newArray(int size) {
            return new BankCardEntity[size];
        }
    };
}
