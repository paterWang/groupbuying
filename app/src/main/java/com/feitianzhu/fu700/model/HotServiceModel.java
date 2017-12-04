package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by Vya on 2017/9/12 0012.
 */

public class HotServiceModel {


    /**
     * list : [{"serviceId":67,"serviceName":"节省时间","price":125,"rebate":25,"adImg":"http://118.190.156.13/user/merchant/service/3c617630f34a4e28b424cbb483b883cf.jpg","userId":31,"headImg":null,"contactPerson":"四","contactTel":"13446464646","serviceAddr":"四简单的","merchantId":27},{"serviceId":52,"serviceName":"新鲜小玩意儿","price":180,"rebate":5,"adImg":"http://118.190.156.13/user/merchant/service/496fd61f88904412a0ede89ba6328f4f.jpg","userId":22,"headImg":null,"contactPerson":"lj","contactTel":"13144805345","serviceAddr":"南山","merchantId":25},{"serviceId":56,"serviceName":"沙拉维","price":800,"rebate":2,"adImg":"http://118.190.156.13/user/merchant/service/29ff6e2088344e18a5d9c3ee4a17c2ab.jpg","userId":22,"headImg":null,"contactPerson":"jk","contactTel":"13144805345","serviceAddr":"南山","merchantId":25},{"serviceId":54,"serviceName":"一杆见底","price":8000,"rebate":369,"adImg":"http://118.190.156.13/user/merchant/service/ce4e7523f8314d37b053f757c36286ef.jpg","userId":22,"headImg":null,"contactPerson":"kill","contactTel":"13144805345","serviceAddr":"北京","merchantId":25},{"serviceId":53,"serviceName":"清风","price":7000,"rebate":50,"adImg":"http://118.190.156.13/user/merchant/service/54f4c4318d9b4d5fabb35d118f8d8922.jpg","userId":22,"headImg":null,"contactPerson":"本","contactTel":"13144805345","serviceAddr":"软件园","merchantId":25},{"serviceId":64,"serviceName":"金士顿内存条","price":580,"rebate":0,"adImg":"http://118.190.156.13/user/merchant/service/5ea3026b8d3b42039a39eeb71e055013.jpg","userId":33,"headImg":null,"contactPerson":"伍先生","contactTel":"13798568946","serviceAddr":"北京","merchantId":29}]
     * pager : {"totalRows":29,"pageRows":6,"pageIndex":1,"paged":false,"hasPrevPage":false,"hasNextPage":true,"currPageRows":6,"pageStartRow":0,"pageEndRow":5,"totalPages":5,"defaultPageRows":20}
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
         * totalRows : 29
         * pageRows : 6
         * pageIndex : 1
         * paged : false
         * hasPrevPage : false
         * hasNextPage : true
         * currPageRows : 6
         * pageStartRow : 0
         * pageEndRow : 5
         * totalPages : 5
         * defaultPageRows : 20
         */

        private int totalRows;
        private int pageRows;
        private int pageIndex;
        private boolean paged;
        private boolean hasPrevPage;
        private boolean hasNextPage;
        private int currPageRows;
        private int pageStartRow;
        private int pageEndRow;
        private int totalPages;
        private int defaultPageRows;

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

        public boolean isPaged() {
            return paged;
        }

        public void setPaged(boolean paged) {
            this.paged = paged;
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

        public int getCurrPageRows() {
            return currPageRows;
        }

        public void setCurrPageRows(int currPageRows) {
            this.currPageRows = currPageRows;
        }

        public int getPageStartRow() {
            return pageStartRow;
        }

        public void setPageStartRow(int pageStartRow) {
            this.pageStartRow = pageStartRow;
        }

        public int getPageEndRow() {
            return pageEndRow;
        }

        public void setPageEndRow(int pageEndRow) {
            this.pageEndRow = pageEndRow;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getDefaultPageRows() {
            return defaultPageRows;
        }

        public void setDefaultPageRows(int defaultPageRows) {
            this.defaultPageRows = defaultPageRows;
        }
    }

    public static class ListBean {
        /**
         * serviceId : 67
         * serviceName : 节省时间
         * price : 125
         * rebate : 25
         * adImg : http://118.190.156.13/user/merchant/service/3c617630f34a4e28b424cbb483b883cf.jpg
         * userId : 31
         * headImg : null
         * contactPerson : 四
         * contactTel : 13446464646
         * serviceAddr : 四简单的
         * merchantId : 27
         */

        private int serviceId;
        private String serviceName;
        private int price;
        private int rebate;
        private String adImg;
        private int userId;
        private Object headImg;
        private String contactPerson;
        private String contactTel;
        private String serviceAddr;
        private int merchantId;

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getRebate() {
            return rebate;
        }

        public void setRebate(int rebate) {
            this.rebate = rebate;
        }

        public String getAdImg() {
            return adImg;
        }

        public void setAdImg(String adImg) {
            this.adImg = adImg;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getHeadImg() {
            return headImg;
        }

        public void setHeadImg(Object headImg) {
            this.headImg = headImg;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public String getContactTel() {
            return contactTel;
        }

        public void setContactTel(String contactTel) {
            this.contactTel = contactTel;
        }

        public String getServiceAddr() {
            return serviceAddr;
        }

        public void setServiceAddr(String serviceAddr) {
            this.serviceAddr = serviceAddr;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }
    }
}
