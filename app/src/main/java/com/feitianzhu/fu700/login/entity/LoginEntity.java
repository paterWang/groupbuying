package com.feitianzhu.fu700.login.entity;

/**
 * Description：
 * Author：Lee
 * Date：2017/9/4 10:31
 */

public class LoginEntity {

    public String accessToken;
    public String userId;

    @Override
    public String toString() {
        return "LoginEntity{" +
                "accessToken='" + accessToken + '\'' +
                "userId='" + userId + '\'' +
                '}';
    }
}
