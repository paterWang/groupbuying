package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by Vya on 2017/11/13 0013.
 */

public class ReleaseTotalDetailModel {

    /**
     * list : [{"rebateId":19136,"totalAmount":3,"createDate":"2017-11-04 14:53:29"}]
     * pager : {"totalRows":14,"pageRows":20,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
     */

    private PagerBean pager;
    private List<ListBean> list;

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

    public static class PagerBean {
        /**
         * totalRows : 14
         * pageRows : 20
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
         * rebateId : 19136
         * totalAmount : 3.0
         * createDate : 2017-11-04 14:53:29
         */

        private int rebateId;
        private double totalAmount;
        private String createDate;

        public int getRebateId() {
            return rebateId;
        }

        public void setRebateId(int rebateId) {
            this.rebateId = rebateId;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
