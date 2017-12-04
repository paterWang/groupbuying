package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by Vya on 2017/9/8 0008.
 */

public class InterestOutModel {

    /**
     * interestId : 1
     * interestName : 兴趣
     * parentId : 0
     * childrenList : [{"interestId":8,"interestName":"旅行","parentId":1,"childrenList":null},{"interestId":9,"interestName":"摄影","parentId":1,"childrenList":null},{"interestId":10,"interestName":"影视","parentId":1,"childrenList":null},{"interestId":11,"interestName":"音乐","parentId":1,"childrenList":null},{"interestId":12,"interestName":"文学","parentId":1,"childrenList":null},{"interestId":13,"interestName":"游戏","parentId":1,"childrenList":null},{"interestId":14,"interestName":"动漫","parentId":1,"childrenList":null},{"interestId":15,"interestName":"运动","parentId":1,"childrenList":null},{"interestId":16,"interestName":"戏曲","parentId":1,"childrenList":null},{"interestId":17,"interestName":"桌游","parentId":1,"childrenList":null},{"interestId":18,"interestName":"另类","parentId":1,"childrenList":null},{"interestId":19,"interestName":"其他","parentId":1,"childrenList":null},{"interestId":20,"interestName":"健康","parentId":1,"childrenList":null},{"interestId":21,"interestName":"美食","parentId":1,"childrenList":null},{"interestId":22,"interestName":"宠物","parentId":1,"childrenList":null}]
     */

    private int interestId;
    private String interestName;
    private int parentId;
    private List<ChildrenListBean> childrenList;

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<ChildrenListBean> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<ChildrenListBean> childrenList) {
        this.childrenList = childrenList;
    }

    public static class ChildrenListBean {
        /**
         * interestId : 8
         * interestName : 旅行
         * parentId : 1
         * childrenList : null
         */

        private int interestId;
        private String interestName;
        private int parentId;
        private Object childrenList;

        public int getInterestId() {
            return interestId;
        }

        public void setInterestId(int interestId) {
            this.interestId = interestId;
        }

        public String getInterestName() {
            return interestName;
        }

        public void setInterestName(String interestName) {
            this.interestName = interestName;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public Object getChildrenList() {
            return childrenList;
        }

        public void setChildrenList(Object childrenList) {
            this.childrenList = childrenList;
        }
    }
}
