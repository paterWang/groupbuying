package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by Vya on 2017/9/18 0018.
 * 我的收藏-服务
 */

public class MineCollectionServiceModel {


    /**
     * list : [{"name":"dd","address":"北京市东城区老庙胡同1104号","cover":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","headImg":null,"createDate":null,"price":null,"rebate":null,"provinceName":"北京市","cityName":"东城区","id":11,"type":1,"nickName":null,"userId":null,"contactTel":null,"collectId":25},{"name":"d哈哈哈","address":"北京市东城区老庙胡同1103号","cover":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","headImg":null,"createDate":null,"price":null,"rebate":null,"provinceName":"北京市","cityName":"东城区","id":10,"type":1,"nickName":null,"userId":null,"contactTel":null,"collectId":24},{"name":"dd","address":"北京市东城区老庙胡同1108号","cover":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","headImg":null,"createDate":null,"price":null,"rebate":null,"provinceName":"北京市","cityName":"东城区","id":15,"type":1,"nickName":null,"userId":null,"contactTel":null,"collectId":12},{"name":"杂货铺","address":"你猜我猜不猜","cover":"http://118.190.156.13/user/merchant/2017/09/14/68d94d9b1e2145f0b32adc21c83dadc4.png","headImg":null,"createDate":null,"price":null,"rebate":null,"provinceName":"北京市","cityName":"石景山区","id":21,"type":1,"nickName":null,"userId":null,"contactTel":null,"collectId":11},{"name":"在一起打打","address":"是因为这些刺激性强拆被","cover":"http://118.190.156.13/user/merchant/2017/09/18/2512625322974a8f88f84000f9611440.jpg","headImg":null,"createDate":null,"price":null,"rebate":null,"provinceName":"北京市","cityName":"东城区","id":22,"type":1,"nickName":null,"userId":null,"contactTel":null,"collectId":10},{"name":"华为手看看机店","address":"北京市东城区老庙胡同1101号","cover":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","headImg":null,"createDate":null,"price":null,"rebate":null,"provinceName":"北京市","cityName":"东城区","id":7,"type":1,"nickName":null,"userId":null,"contactTel":null,"collectId":9}]
     * pager : {"totalRows":6,"pageRows":6,"pageIndex":1,"paged":false,"hasPrevPage":false,"hasNextPage":false,"defaultPageRows":20,"totalPages":1,"currPageRows":6,"pageStartRow":0,"pageEndRow":5}
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
         * totalRows : 6
         * pageRows : 6
         * pageIndex : 1
         * paged : false
         * hasPrevPage : false
         * hasNextPage : false
         * defaultPageRows : 20
         * totalPages : 1
         * currPageRows : 6
         * pageStartRow : 0
         * pageEndRow : 5
         */

        private int totalRows;
        private int pageRows;
        private int pageIndex;
        private boolean paged;
        private boolean hasPrevPage;
        private boolean hasNextPage;
        private int defaultPageRows;
        private int totalPages;
        private int currPageRows;
        private int pageStartRow;
        private int pageEndRow;

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

        public int getDefaultPageRows() {
            return defaultPageRows;
        }

        public void setDefaultPageRows(int defaultPageRows) {
            this.defaultPageRows = defaultPageRows;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
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
    }

    public static class ListBean {
        /**
         * name : dd
         * address : 北京市东城区老庙胡同1104号
         * cover : http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png
         * headImg : null
         * createDate : null
         * price : null
         * rebate : null
         * provinceName : 北京市
         * cityName : 东城区
         * id : 11
         * type : 1
         * nickName : null
         * userId : null
         * contactTel : null
         * collectId : 25
         */

        private String name;
        private String address;
        private String cover;
        private Object headImg;
        private Object createDate;
        private Object price;
        private Object rebate;
        private String provinceName;
        private String cityName;
        private int id;
        private int type;
        private Object nickName;
        private Object userId;
        private Object contactTel;
        private int collectId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public Object getHeadImg() {
            return headImg;
        }

        public void setHeadImg(Object headImg) {
            this.headImg = headImg;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getRebate() {
            return rebate;
        }

        public void setRebate(Object rebate) {
            this.rebate = rebate;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getContactTel() {
            return contactTel;
        }

        public void setContactTel(Object contactTel) {
            this.contactTel = contactTel;
        }

        public int getCollectId() {
            return collectId;
        }

        public void setCollectId(int collectId) {
            this.collectId = collectId;
        }
    }
}
