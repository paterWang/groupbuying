package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by Vya on 2017/10/18 0018.
 */

public class ShopRecordRefuseModel {


    /**
     * pager : {"totalRows":6,"pageRows":6,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
     * list : [{"orderNo":"BB20171018115111991921","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/18/8f9fd44c8f354a989b7a96ae39f28cf4.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/18/cefdb29f5b514435845d579b1a95138a.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/18/627fe6aab785449ca4fd65c5fbba5cb8.png","merchantName":"北京豫森珠宝","consumeAmount":42,"handleFee":8.4,"handleFeeRate":20,"isPay":"1","createDate":"2017-10-18 11:51:11","status":"-1","refuseReason":"XX","isEval":"0"},{"orderNo":"BB20171012180151118504","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/12/983c25a1e01c4eb9b3fb116048923a4c.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/12/12e7783b37e64fb4a3f63eea52ac814f.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/12/f60a7633cfd94a74ae5f67ce20fff2f1.png","merchantName":"北京豫森珠宝","consumeAmount":1,"handleFee":0.2,"handleFeeRate":20,"isPay":"1","createDate":"2017-10-12 18:01:52","status":"-1","refuseReason":"xxxxxxxxxx","isEval":"0"},{"orderNo":"BB20171012180002341089","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/12/6189b30f755340d797dcb9934e6ac0a7.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/12/53daf2df2d81424f9bf4026557178546.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/12/705196bcb48c484a94827f50ff6afd7b.png","merchantName":"北京豫森珠宝","consumeAmount":122,"handleFee":24.4,"handleFeeRate":20,"isPay":"1","createDate":"2017-10-12 18:00:02","status":"-1","refuseReason":"xxxxxxxxxx","isEval":"0"},{"orderNo":"BB20171007172005179588","userId":31,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/07/cdafedb2dfe6417c8759c088e4d3dfed.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/07/2dfe9a371d9340a6abfd5ef5987823f9.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/07/469646d43d674c80846f5e36b4372350.png","merchantName":"明江玻璃","consumeAmount":4645,"handleFee":464.5,"handleFeeRate":10,"isPay":"1","createDate":"2017-10-07 17:20:06","status":"-1","refuseReason":"xxxxxxxxxx","isEval":"0"},{"orderNo":"BB20171004203913125963","userId":31,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/10/04/b3d6fd60b90b41cd9e7a9738139b4a8f.jpg","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/10/04/e9c991dfd2974a1ab2a3d7ff74e14aea.jpg","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/10/04/c8b8b0127cbf4d339c051c21563fa919.jpg","merchantName":"明江玻璃","consumeAmount":1000,"handleFee":100,"handleFeeRate":10,"isPay":"1","createDate":"2017-10-04 20:39:14","status":"-1","refuseReason":"xxxxxxxxxx","isEval":"0"},{"orderNo":"BB20170926141112266511","userId":25,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/26/6686aa1da5e9418eb1ae267c78510ca3.png","consumeObjImg":"http://118.190.156.13/buybill/consume/2017/09/26/79047381fd2941d4bb1fb127e0015ef1.png","consumeRcptImg":"http://118.190.156.13/buybill/consume/2017/09/26/4935f3c21ce8483a99f63c94d3094aec.png","merchantName":"明江玻璃","consumeAmount":1000,"handleFee":200,"handleFeeRate":20,"isPay":"1","createDate":"2017-09-26 14:11:12","status":"-1","refuseReason":"xxxxxxxxxx","isEval":"0"}]
     * statusCnts : [{"cnt":6,"status":-1},{"cnt":1,"status":0},{"cnt":8,"status":1}]
     */

    private PagerBean pager;
    private List<ListBean> list;
    private List<StatusCntsBean> statusCnts;

    public PagerBean getPager() {
        return pager;
    }

    public void setPager(PagerBean pager) {
        this.pager = pager;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<StatusCntsBean> getStatusCnts() {
        return statusCnts;
    }

    public void setStatusCnts(List<StatusCntsBean> statusCnts) {
        this.statusCnts = statusCnts;
    }

    public static class PagerBean {
        /**
         * totalRows : 6
         * pageRows : 6
         * pageIndex : 1
         * hasPrevPage : false
         * hasNextPage : false
         */

        private int totalRows;
        private int pageRows;
        private int pageIndex;
        private boolean hasPrevPage;
        private boolean hasNextPage;

        public int getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }

        public int getPageRows() {
            return pageRows;
        }

        public void setPageRows(int pageRows) {
            this.pageRows = pageRows;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public boolean isHasPrevPage() {
            return hasPrevPage;
        }

        public void setHasPrevPage(boolean hasPrevPage) {
            this.hasPrevPage = hasPrevPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }
    }

    public static class ListBean {
        /**
         * orderNo : BB20171018115111991921
         * userId : 1
         * type : 3
         * consumePlaceImg : http://118.190.156.13/buybill/consume/2017/10/18/8f9fd44c8f354a989b7a96ae39f28cf4.png
         * consumeObjImg : http://118.190.156.13/buybill/consume/2017/10/18/cefdb29f5b514435845d579b1a95138a.png
         * consumeRcptImg : http://118.190.156.13/buybill/consume/2017/10/18/627fe6aab785449ca4fd65c5fbba5cb8.png
         * merchantName : 北京豫森珠宝
         * consumeAmount : 42
         * handleFee : 8.4
         * handleFeeRate : 20
         * isPay : 1
         * createDate : 2017-10-18 11:51:11
         * status : -1
         * refuseReason : XX
         * isEval : 0
         */

        private String orderNo;
        private int userId;
        private int type;
        private String consumePlaceImg;
        private String consumeObjImg;
        private String consumeRcptImg;
        private String merchantName;
        private int consumeAmount;
        private double handleFee;
        private int handleFeeRate;
        private String isPay;
        private String createDate;
        private String status;
        private String refuseReason;
        private String isEval;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getConsumePlaceImg() {
            return consumePlaceImg;
        }

        public void setConsumePlaceImg(String consumePlaceImg) {
            this.consumePlaceImg = consumePlaceImg;
        }

        public String getConsumeObjImg() {
            return consumeObjImg;
        }

        public void setConsumeObjImg(String consumeObjImg) {
            this.consumeObjImg = consumeObjImg;
        }

        public String getConsumeRcptImg() {
            return consumeRcptImg;
        }

        public void setConsumeRcptImg(String consumeRcptImg) {
            this.consumeRcptImg = consumeRcptImg;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getConsumeAmount() {
            return consumeAmount;
        }

        public void setConsumeAmount(int consumeAmount) {
            this.consumeAmount = consumeAmount;
        }

        public double getHandleFee() {
            return handleFee;
        }

        public void setHandleFee(double handleFee) {
            this.handleFee = handleFee;
        }

        public int getHandleFeeRate() {
            return handleFeeRate;
        }

        public void setHandleFeeRate(int handleFeeRate) {
            this.handleFeeRate = handleFeeRate;
        }

        public String getIsPay() {
            return isPay;
        }

        public void setIsPay(String isPay) {
            this.isPay = isPay;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public String getIsEval() {
            return isEval;
        }

        public void setIsEval(String isEval) {
            this.isEval = isEval;
        }
    }

    public static class StatusCntsBean {
        /**
         * cnt : 6
         * status : -1
         */

        private int cnt;
        private int status;

        public int getCnt() {
            return cnt;
        }

        public void setCnt(int cnt) {
            this.cnt = cnt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
