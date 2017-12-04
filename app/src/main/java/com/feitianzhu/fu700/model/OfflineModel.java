package com.feitianzhu.fu700.model;

/**
 * Created by Vya on 2017/11/7 0007.
 */

public class OfflineModel {

    /**
     * openBank : 建设银行
     * openSubbranch : 北京顺义光明街支行
     * accName : 吴萍
     * accNo : 6217000010098822284
     * bankLogo : http://118.190.156.13/conf/bank/20171106104030.png
     */

    private String openBank;
    private String openSubbranch;
    private String accName;
    private String accNo;
    private String bankLogo;

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getOpenSubbranch() {
        return openSubbranch;
    }

    public void setOpenSubbranch(String openSubbranch) {
        this.openSubbranch = openSubbranch;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
