package com.feitianzhu.fu700.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dicallc on 2017/9/6 0006.
 */

public class ShopsInfo implements Parcelable {

    /**
     * merchantId : 7
     * merchantName : MSN
     * star : null
     * clsId : 1
     * longitude : 114.0259736573215
     * latitude : 22.546053546205247
     * introduce : 中融
     * adImgs : http://118.190.156.13/user/merchant/9b254244eafa477c8b4eaf0c72c32b63.png,
     * dtlAddr : 明松
     * servicePhone : 1364975649
     */

    public int merchantId;
    public String merchantName;
    public String star;
    public int clsId;
    public String longitude;
    public String clsName;
    public String latitude;
    public String introduce;
    public String adImgs;
    public String dtlAddr;
    public String collectId;
    public String servicePhone;
    public String merchantHeadImg;
    public String provinceName;
    public String cityName;
    public String shareUrl;

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.merchantId);
        dest.writeString(this.merchantName);
        dest.writeString(this.star);
        dest.writeInt(this.clsId);
        dest.writeString(this.longitude);
        dest.writeString(this.clsName);
        dest.writeString(this.latitude);
        dest.writeString(this.introduce);
        dest.writeString(this.adImgs);
        dest.writeString(this.dtlAddr);
        dest.writeString(this.collectId);
        dest.writeString(this.servicePhone);
        dest.writeString(this.merchantHeadImg);
        dest.writeString(this.provinceName);
        dest.writeString(this.cityName);
        dest.writeString(this.shareUrl);
    }

    public ShopsInfo() {
    }

    protected ShopsInfo(Parcel in) {
        this.merchantId = in.readInt();
        this.merchantName = in.readString();
        this.star = in.readString();
        this.clsId = in.readInt();
        this.longitude = in.readString();
        this.clsName = in.readString();
        this.latitude = in.readString();
        this.introduce = in.readString();
        this.adImgs = in.readString();
        this.dtlAddr = in.readString();
        this.collectId = in.readString();
        this.servicePhone = in.readString();
        this.merchantHeadImg = in.readString();
        this.provinceName = in.readString();
        this.cityName = in.readString();
        this.shareUrl = in.readString();
    }

    public static final Parcelable.Creator<ShopsInfo> CREATOR =
        new Parcelable.Creator<ShopsInfo>() {
            public ShopsInfo createFromParcel(Parcel source) {
                return new ShopsInfo(source);
            }

            public ShopsInfo[] newArray(int size) {
                return new ShopsInfo[size];
            }
        };
}
