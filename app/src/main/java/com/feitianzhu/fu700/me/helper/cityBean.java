package com.feitianzhu.fu700.me.helper;

import java.util.List;

/**
 * Created by Vya on 2017/9/7 0007.
 */

public class cityBean {

    /**
     * name : 北京市
     * pid : 0
     * cityList : [{"name":"东城区","pid":"110000","areaList":[],"id":"110100"},{"name":"西城区","pid":"110000","areaList":[],"id":"110200"},{"name":"朝阳区","pid":"110000","areaList":[],"id":"110500"},{"name":"丰台区","pid":"110000","areaList":[],"id":"110600"},{"name":"石景山区","pid":"110000","areaList":[],"id":"110700"},{"name":"海淀区","pid":"110000","areaList":[],"id":"110800"},{"name":"门头沟区","pid":"110000","areaList":[],"id":"110900"},{"name":"房山区","pid":"110000","areaList":[],"id":"111100"},{"name":"通州区","pid":"110000","areaList":[],"id":"111200"},{"name":"顺义区","pid":"110000","areaList":[],"id":"111300"},{"name":"昌平区","pid":"110000","areaList":[],"id":"111400"},{"name":"大兴区","pid":"110000","areaList":[],"id":"111500"},{"name":"怀柔区","pid":"110000","areaList":[],"id":"111600"},{"name":"平谷区","pid":"110000","areaList":[],"id":"111700"},{"name":"密云县","pid":"110000","areaList":[],"id":"112800"},{"name":"延庆县","pid":"110000","areaList":[],"id":"112900"}]
     * id : 110000
     */

    private String name;
    private String pid;
    private String id;
    private List<CityListBean> cityList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBean {
        /**
         * name : 东城区
         * pid : 110000
         * areaList : []
         * id : 110100
         */

        private String name;
        private String pid;
        private String id;
        private List<?> areaList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<?> getAreaList() {
            return areaList;
        }

        public void setAreaList(List<?> areaList) {
            this.areaList = areaList;
        }
    }
}
