package com.feitianzhu.fu700.bankcard.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description：
 * Author：Lee
 * Date：2017/9/7 16:34
 */

public class UserBankCardEntity implements Parcelable {


    /**
     * bankCardId : 1
     * bankName : 农业银行
     * icon : null
     * bankCardNo : 622848*********0018
     */

    public int bankCardId;
    public String bankName;
    public String icon;
    public String bankCardNo;

    @Override
    public String toString() {
        return "UserBankCardEntity{" +
                "bankCardId=" + bankCardId +
                ", bankName='" + bankName + '\'' +
                ", icon='" + icon + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bankCardId);
        dest.writeString(this.bankName);
        dest.writeString(this.icon);
        dest.writeString(this.bankCardNo);
    }

    public UserBankCardEntity() {
    }

    protected UserBankCardEntity(Parcel in) {
        this.bankCardId = in.readInt();
        this.bankName = in.readString();
        this.icon = in.readString();
        this.bankCardNo = in.readString();
    }

    public static final Parcelable.Creator<UserBankCardEntity> CREATOR = new Parcelable.Creator<UserBankCardEntity>() {
        @Override
        public UserBankCardEntity createFromParcel(Parcel source) {
            return new UserBankCardEntity(source);
        }

        @Override
        public UserBankCardEntity[] newArray(int size) {
            return new UserBankCardEntity[size];
        }
    };
}
