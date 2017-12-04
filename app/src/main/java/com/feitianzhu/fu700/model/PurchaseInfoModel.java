package com.feitianzhu.fu700.model;

/**
 * Created by Vya on 2017/8/29 0029.
 */

public class PurchaseInfoModel {
    public PurchaseInfoModel(String content) {
        this.content = content;
    }
    public PurchaseInfoModel(){}

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
