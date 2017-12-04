package com.feitianzhu.fu700.model;

/**
 * Created by dicallc on 2017/9/4 0004.
 */

public class UserVeriModel {
    public UserVeriModel() {
    }

    public UserVeriModel(int mAuthStatus) {
        authStatus = mAuthStatus;
    }

    /**
     * realName : jdk
     * certifNo : 4311021993
     * authStatus : 0
     * refuseReason : null
     */

    public String realName;
    public String certifNo;
    public int authStatus;
    public Object refuseReason;
}
