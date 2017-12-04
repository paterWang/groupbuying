package com.feitianzhu.fu700.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by dicallc on 2017/9/22 0022.
 */

public class ShopOrderModel implements Parcelable {

    /**
     * list : [{"orderNo":"BB20170915141253888730","userId":14,"type":2,"consumePlaceImg":"http://118.190.156.13/USER/merchant/2017/09/06/19f4c145a13d49e49fcacf995fba5e7d.png","merchantName":"华为手看看机店","consumeAmount":1,"handleFee":0,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-15 14:12:54","status":"0","merchant":{"merchantId":7,"merchantName":"华为手看看机店","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png"}},{"orderNo":"BB20170914210559139718","userId":14,"type":2,"consumePlaceImg":"http://118.190.156.13/USER/merchant/2017/09/06/19f4c145a13d49e49fcacf995fba5e7d.png","merchantName":"华为手看看机店","consumeAmount":1,"handleFee":0,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 21:05:59","status":"1","merchant":{"merchantId":7,"merchantName":"华为手看看机店","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png"}}]
     * pager : {"totalRows":2,"pageRows":10,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
     */

    public PagerEntity pager;
    public List<ListEntity> list;

    public static class PagerEntity implements Parcelable {

      /**
       * totalRows : 2
       * pageRows : 10
       * pageIndex : 1
       * hasPrevPage : false
       * hasNextPage : false
       */

      public int totalRows;
      public int pageRows;
      public int pageIndex;
      public boolean hasPrevPage;
      public boolean hasNextPage;

      @Override public int describeContents() {
        return 0;
      }

      @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalRows);
        dest.writeInt(this.pageRows);
        dest.writeInt(this.pageIndex);
        dest.writeByte(hasPrevPage ? (byte) 1 : (byte) 0);
        dest.writeByte(hasNextPage ? (byte) 1 : (byte) 0);
      }

      public PagerEntity() {
      }

      protected PagerEntity(Parcel in) {
        this.totalRows = in.readInt();
        this.pageRows = in.readInt();
        this.pageIndex = in.readInt();
        this.hasPrevPage = in.readByte() != 0;
        this.hasNextPage = in.readByte() != 0;
      }

      public static final Creator<PagerEntity> CREATOR = new Creator<PagerEntity>() {
        public PagerEntity createFromParcel(Parcel source) {
          return new PagerEntity(source);
        }

        public PagerEntity[] newArray(int size) {
          return new PagerEntity[size];
        }
      };
    }

    public static class ListEntity implements Parcelable {

      /**
       * orderNo : BB20170915141253888730
       * userId : 14
       * type : 2
       * consumePlaceImg : http://118.190.156.13/USER/merchant/2017/09/06/19f4c145a13d49e49fcacf995fba5e7d.png
       * merchantName : 华为手看看机店
       * consumeAmount : 1.0
       * handleFee : 0.0
       * handleFeeRate : 19.0
       * isPay : 1
       * createDate : 2017-09-15 14:12:54
       * status : 0
       * merchant : {"merchantId":7,"merchantName":"华为手看看机店","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png"}
       */

      public String orderNo;
      public int userId;
      public int type;
      public String consumePlaceImg;
      public String merchantName;
      public double consumeAmount;
      public double handleFee;
      public double handleFeeRate;
      public String isPay;
      public String createDate;
      public String status;
      public String isEval;
      public MerchantEntity merchant;

      public static class MerchantEntity implements Parcelable {

        /**
         * merchantId : 7
         * merchantName : 华为手看看机店
         * merchantHeadImg : http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png
         */

        public int merchantId;
        public String merchantName;
        public String provinceName;
        public String cityName;
        public String areaName;
        public String dtlAddr;
        public String merchantHeadImg;

        @Override public int describeContents() {
          return 0;
        }

        @Override public void writeToParcel(Parcel dest, int flags) {
          dest.writeInt(this.merchantId);
          dest.writeString(this.merchantName);
          dest.writeString(this.provinceName);
          dest.writeString(this.cityName);
          dest.writeString(this.areaName);
          dest.writeString(this.dtlAddr);
          dest.writeString(this.merchantHeadImg);
        }

        public MerchantEntity() {
        }

        protected MerchantEntity(Parcel in) {
          this.merchantId = in.readInt();
          this.merchantName = in.readString();
          this.provinceName = in.readString();
          this.cityName = in.readString();
          this.areaName = in.readString();
          this.dtlAddr = in.readString();
          this.merchantHeadImg = in.readString();
        }

        public static final Parcelable.Creator<MerchantEntity> CREATOR =
            new Parcelable.Creator<MerchantEntity>() {
              public MerchantEntity createFromParcel(Parcel source) {
                return new MerchantEntity(source);
              }

              public MerchantEntity[] newArray(int size) {
                return new MerchantEntity[size];
              }
            };
      }

      @Override public int describeContents() {
        return 0;
      }

      @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderNo);
        dest.writeInt(this.userId);
        dest.writeInt(this.type);
        dest.writeString(this.consumePlaceImg);
        dest.writeString(this.merchantName);
        dest.writeDouble(this.consumeAmount);
        dest.writeDouble(this.handleFee);
        dest.writeDouble(this.handleFeeRate);
        dest.writeString(this.isPay);
        dest.writeString(this.createDate);
        dest.writeString(this.status);
        dest.writeParcelable(this.merchant, 0);
      }

      public ListEntity() {
      }

      protected ListEntity(Parcel in) {
        this.orderNo = in.readString();
        this.userId = in.readInt();
        this.type = in.readInt();
        this.consumePlaceImg = in.readString();
        this.merchantName = in.readString();
        this.consumeAmount = in.readDouble();
        this.handleFee = in.readDouble();
        this.handleFeeRate = in.readDouble();
        this.isPay = in.readString();
        this.createDate = in.readString();
        this.status = in.readString();
        this.merchant = in.readParcelable(MerchantEntity.class.getClassLoader());
      }

      public static final Parcelable.Creator<ListEntity> CREATOR =
          new Parcelable.Creator<ListEntity>() {
            public ListEntity createFromParcel(Parcel source) {
              return new ListEntity(source);
            }

            public ListEntity[] newArray(int size) {
              return new ListEntity[size];
            }
          };
    }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.pager, flags);
    dest.writeTypedList(list);
  }

  public ShopOrderModel() {
  }

  protected ShopOrderModel(Parcel in) {
    this.pager = in.readParcelable(PagerEntity.class.getClassLoader());
    this.list = in.createTypedArrayList(ListEntity.CREATOR);
  }

  public static final Parcelable.Creator<ShopOrderModel> CREATOR =
      new Parcelable.Creator<ShopOrderModel>() {
        public ShopOrderModel createFromParcel(Parcel source) {
          return new ShopOrderModel(source);
        }

        public ShopOrderModel[] newArray(int size) {
          return new ShopOrderModel[size];
        }
      };
}
