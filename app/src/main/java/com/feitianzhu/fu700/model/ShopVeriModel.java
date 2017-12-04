package com.feitianzhu.fu700.model;

/**
 * Created by dicallc on 2017/9/5 0005.
 */

public class ShopVeriModel {
    public ShopVeriModel() {
    }

    public ShopVeriModel(int mAuthStatus) {
        authStatus = mAuthStatus;
    }

    /**
     * busiLicenseName : 精品白沙
     * registeNo : 123
     * legalPerson : jre
     * busiNature : 1
     * authStatus : 0
     * refuseReason : null
     */

    public String busiLicenseName;
    public String registeNo;
    public String legalPerson;
    public String busiNature;
    public int authStatus;
    public String refuseReason;

    @Override public String toString() {
        return "ShopVeriModel{"
            + "busiLicenseName='"
            + busiLicenseName
            + '\''
            + ", registeNo='"
            + registeNo
            + '\''
            + ", legalPerson='"
            + legalPerson
            + '\''
            + ", busiNature='"
            + busiNature
            + '\''
            + ", authStatus="
            + authStatus
            + ", refuseReason='"
            + refuseReason
            + '\''
            + '}';
    }
}
