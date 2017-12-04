package com.feitianzhu.fu700.me.navigationbar;

/**
 * Created by Vya on 2017/5/10 0010.
 * 导航条的规范
 */

public interface INavigationbar {
    /**
     * 头部的规范
     */
    int bindLayoutId();

    /**
     * 绑定头部的参数
     */
    void applyView();
}
