package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by Vya on 2017/9/13.
 */

public class ShopRecordDetailModel {


    /**
     * pager : {"totalRows":10,"pageRows":6,"pageIndex":1,"hasPrevPage":false,"hasNextPage":true}
     * list : [{"orderNo":"BB20170914204721650899","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/b61c985d533f4e6aad3fc989cfd32057.png","merchantName":"华为手看看机店","consumeAmount":10,"handleFee":1.9,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 20:47:22","status":"0"},{"orderNo":"BB20170914204035196157","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/2776ae1d3e384b468cec4a0e5ded66ec.png","merchantName":"华为手看看机店","consumeAmount":1,"handleFee":0.19,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 20:40:35","status":"0"},{"orderNo":"BB20170914200831354290","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/2e4e42d36249469d8065c43594d069ef.png","merchantName":"华为手看看机店","consumeAmount":133,"handleFee":25.27,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 20:08:32","status":"0"},{"orderNo":"BB20170914180435570013","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/29252ea9826b4195b5d6b9ddc6dceb3a.png","merchantName":"华为手看看机店","consumeAmount":186,"handleFee":35.34,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 18:04:36","status":"0"},{"orderNo":"BB20170914143101710661","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/945afdc800d44e63bab5853e52e84b08.png","merchantName":"华为手看看机店","consumeAmount":789,"handleFee":149.91,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 14:31:02","status":"0"},{"orderNo":"BB20170914142800741503","userId":1,"type":3,"consumePlaceImg":"http://118.190.156.13/buybill/consume/2017/09/14/562f1b27982c44d7b5ce1d8436678ff2.png","merchantName":"华为手看看机店","consumeAmount":733,"handleFee":139.27,"handleFeeRate":19,"isPay":"1","createDate":"2017-09-14 14:28:00","status":"0"}]
     * statusCnts : [{"cnt":0,"status":-1},{"cnt":0,"status":0},{"cnt":0,"status":1}]
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
         * totalRows : 10
         * pageRows : 6
         * pageIndex : 1
         * hasPrevPage : false
         * hasNextPage : true
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
         * orderNo : BB20170914204721650899
         * userId : 1
         * type : 3
         * consumePlaceImg : http://118.190.156.13/buybill/consume/2017/09/14/b61c985d533f4e6aad3fc989cfd32057.png
         * merchantName : 华为手看看机店
         * consumeAmount : 10
         * handleFee : 1.9
         * handleFeeRate : 19
         * isPay : 1
         * createDate : 2017-09-14 20:47:22
         * status : 0
         */

        private String orderNo;
        private int userId;
        private int type;
        private String consumePlaceImg;
        private String merchantName;
        private int consumeAmount;
        private double handleFee;
        private int handleFeeRate;
        private String isPay;
        private String createDate;
        private String status;

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
    }

    public static class StatusCntsBean {
        /**
         * cnt : 0
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
