package com.feitianzhu.fu700.payforme.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Lee on 2017/8/31.
 */

public class PayForMeEntity {

    /**
     * pager : {"totalRows":8,"pageRows":20,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
     * list : [{"orderNo":"BB20171012151757406612","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/12/7c16401e8fcf4d1c9fb5c3fabe96fd1e.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/12/d15d9462d71f4611bb0efc26d035e537.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/12/d4ba54ed992a42168effe41b114239cd.png","merchantName":"1","merchantAddr":"2","goodsName":"3","consumeAmount":0.1,"handleFee":0.02,"handleFeeRate":20,"isPay":"1","createDate":"2017-10-12 15:17:57","status":"0","isEval":"0"},{"orderNo":"BB20171012151245798536","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/12/4d9cc3bf804e4a4fb5098d023a432c88.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/12/2562600fe2664a4cbff1125c917a73fd.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/12/8a0c5433f8bc4b6593ac7419a6677f83.png","merchantName":"1","merchantAddr":"2","goodsName":"3","consumeAmount":0.1,"handleFee":0.02,"handleFeeRate":20,"isPay":"1","createDate":"2017-10-12 15:12:45","status":"0","isEval":"0"},{"orderNo":"BB20171012150155783599","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/12/1fccf172ce0a4fb281a134586fd0ef53.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/12/d58a64ac11bd4985a954c2fb4d049eb6.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/12/da81f25231eb4a7f96569b683a8655b0.png","merchantName":"2","merchantAddr":"3","goodsName":"3","consumeAmount":0.1,"handleFee":0.02,"handleFeeRate":20,"isPay":"1","createDate":"2017-10-12 15:01:56","status":"0","isEval":"0"},{"orderNo":"BB20170924154349328791","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/24/afcc963475ab41319797f50ecb73fed2.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/09/24/f95c1f5aa1004a1fbf64901a8702e37f.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/09/24/e30103efcfda4cc3803f3198a787b0b2.png","merchantName":"1111111111111111111111111111111111111111111111,","merchantAddr":"2","goodsName":"3","consumeAmount":1,"handleFee":0.1,"handleFeeRate":10,"isPay":"1","createDate":"2017-09-24 15:43:50","status":"0","isEval":"0"},{"orderNo":"BB20170919104738520750","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/19/57e1e7d548c14a9e9c20c42b38d83c1a.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/09/19/0e284e144db44be482a97353be87af97.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/09/19/dc037612c2f241fc9c8a4aa539704ac2.png","merchantName":"2","merchantAddr":"3","goodsName":"4","consumeAmount":3,"handleFee":0.3,"handleFeeRate":10,"isPay":"1","createDate":"2017-09-19 10:47:39","status":"0","isEval":"0"},{"orderNo":"BB20170914232121946118","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/e57a076639054931a67fcbd195b377a9.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/09/14/fb962e900c414b54a34029d1e2e6d9e2.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/09/14/a0fcf520b17b40fbbdbb249305b87e49.png","merchantName":"dfsaffdsafewrweqrwqerewqrwqereqr2343242314324213rewqdasfda","merchantAddr":"fdsarfewqrew","goodsName":"reqwrewq","consumeAmount":5,"handleFee":0.95,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 23:21:22","status":"0","isEval":"0"},{"orderNo":"BB20170914231606908256","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/e5ccd3e1a04c4936bcc2490d6b3d17ba.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/09/14/28d3858ea77346318c66294191dfaa01.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/09/14/c0be88660d7d423a8afbe96f2f298422.png","merchantName":"erwq","merchantAddr":"rewq","goodsName":"re","consumeAmount":3,"handleFee":0.57,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 23:16:06","status":"0","isEval":"0"},{"orderNo":"BB20170914231202935819","userId":15,"type":1,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/21/19a9a6acd6f54974b3a6902f54b7a6e3.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/09/21/dd555c27e6b7482f898bedae1127dd7a.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/09/21/9f72811edb25491d9a14c99f1647351d.png","merchantName":"sssssss","merchantAddr":"aaaaaaa","goodsName":"ffffffffff","consumeAmount":2,"handleFee":0.38,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 23:12:02","status":"0","refuseReason":"钱太少","isEval":"0"}]
     * statusCnts : [{"cnt":2,"status":-1},{"cnt":10,"status":0},{"cnt":1,"status":1}]
     */

    public PagerBean pager;
    public List<ListBean> list;
    public List<StatusCntsBean> statusCnts;

    public static class PagerBean {
        /**
         * totalRows : 8
         * pageRows : 20
         * pageIndex : 1
         * hasPrevPage : false
         * hasNextPage : false
         */

        public int totalRows;
        public int pageRows;
        public int pageIndex;
        public boolean hasPrevPage;
        public boolean hasNextPage;
    }

    public static class ListBean implements Parcelable {
        /**
         * orderNo : BB20171012151757406612
         * userId : 15
         * type : 1
         * consumePlaceImg : http://118.190.156.13/buybill/consume/2017/10/12/7c16401e8fcf4d1c9fb5c3fabe96fd1e.png
         * consumeObjImg : http://118.190.156.13/buybill/consume/2017/10/12/d15d9462d71f4611bb0efc26d035e537.png
         * consumeRcptImg : http://118.190.156.13/buybill/consume/2017/10/12/d4ba54ed992a42168effe41b114239cd.png
         * merchantName : 1
         * merchantAddr : 2
         * goodsName : 3
         * consumeAmount : 0.1
         * handleFee : 0.02
         * handleFeeRate : 20
         * isPay : 1
         * createDate : 2017-10-12 15:17:57
         * status : 0
         * isEval : 0
         * refuseReason : 钱太少
         */

        public String orderNo;
        public int userId;
        public int type;
        public String consumePlaceImg;
        public String consumeObjImg;
        public String consumeRcptImg;
        public String merchantName;
        public String merchantAddr;
        public String goodsName;
        public double consumeAmount;
        public double handleFee;
        public int handleFeeRate;
        public String isPay;
        public String createDate;
        public String status;
        public String isEval;
        public String refuseReason;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.orderNo);
            dest.writeInt(this.userId);
            dest.writeInt(this.type);
            dest.writeString(this.consumePlaceImg);
            dest.writeString(this.consumeObjImg);
            dest.writeString(this.consumeRcptImg);
            dest.writeString(this.merchantName);
            dest.writeString(this.merchantAddr);
            dest.writeString(this.goodsName);
            dest.writeDouble(this.consumeAmount);
            dest.writeDouble(this.handleFee);
            dest.writeInt(this.handleFeeRate);
            dest.writeString(this.isPay);
            dest.writeString(this.createDate);
            dest.writeString(this.status);
            dest.writeString(this.isEval);
            dest.writeString(this.refuseReason);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.orderNo = in.readString();
            this.userId = in.readInt();
            this.type = in.readInt();
            this.consumePlaceImg = in.readString();
            this.consumeObjImg = in.readString();
            this.consumeRcptImg = in.readString();
            this.merchantName = in.readString();
            this.merchantAddr = in.readString();
            this.goodsName = in.readString();
            this.consumeAmount = in.readDouble();
            this.handleFee = in.readDouble();
            this.handleFeeRate = in.readInt();
            this.isPay = in.readString();
            this.createDate = in.readString();
            this.status = in.readString();
            this.isEval = in.readString();
            this.refuseReason = in.readString();
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    public static class StatusCntsBean {
        /**
         * cnt : 2
         * status : -1
         */

        public int cnt;
        public int status;
    }
}
