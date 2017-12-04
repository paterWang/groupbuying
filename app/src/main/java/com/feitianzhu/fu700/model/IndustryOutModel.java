package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by Vya on 2017/9/8 0008.
 */

public class IndustryOutModel {

    /**
     * industryId : 1
     * industryName : IT互联网
     * parentId : 0
     * childrenList : [{"industryId":18,"industryName":"产品","parentId":1,"childrenList":null},{"industryId":19,"industryName":"开发","parentId":1,"childrenList":null},{"industryId":20,"industryName":"销售","parentId":1,"childrenList":null},{"industryId":21,"industryName":"运营","parentId":1,"childrenList":null},{"industryId":22,"industryName":"市场","parentId":1,"childrenList":null},{"industryId":23,"industryName":"设计","parentId":1,"childrenList":null},{"industryId":24,"industryName":"人力资源","parentId":1,"childrenList":null},{"industryId":25,"industryName":"运维","parentId":1,"childrenList":null},{"industryId":26,"industryName":"安全","parentId":1,"childrenList":null},{"industryId":27,"industryName":"高管","parentId":1,"childrenList":null},{"industryId":28,"industryName":"测试","parentId":1,"childrenList":null},{"industryId":29,"industryName":"行政文秘","parentId":1,"childrenList":null},{"industryId":30,"industryName":"硬件","parentId":1,"childrenList":null},{"industryId":31,"industryName":"客服","parentId":1,"childrenList":null},{"industryId":32,"industryName":"游戏","parentId":1,"childrenList":null},{"industryId":33,"industryName":"云计算","parentId":1,"childrenList":null},{"industryId":34,"industryName":"财税审计","parentId":1,"childrenList":null},{"industryId":35,"industryName":"风险投资","parentId":1,"childrenList":null},{"industryId":36,"industryName":"项目管理","parentId":1,"childrenList":null},{"industryId":37,"industryName":"文案编辑","parentId":1,"childrenList":null},{"industryId":38,"industryName":"采购物流","parentId":1,"childrenList":null},{"industryId":39,"industryName":"IT培训","parentId":1,"childrenList":null},{"industryId":40,"industryName":"数据分析","parentId":1,"childrenList":null},{"industryId":41,"industryName":"法律法务","parentId":1,"childrenList":null},{"industryId":42,"industryName":"其他","parentId":1,"childrenList":null}]
     */

    private int industryId;
    private String industryName;
    private int parentId;
    private List<ChildrenListBean> childrenList;

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
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
         * industryId : 18
         * industryName : 产品
         * parentId : 1
         * childrenList : null
         */

        private int industryId;
        private String industryName;
        private int parentId;
        private Object childrenList;

        public int getIndustryId() {
            return industryId;
        }

        public void setIndustryId(int industryId) {
            this.industryId = industryId;
        }

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
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
