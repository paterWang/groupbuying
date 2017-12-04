package com.feitianzhu.fu700.utils;

import com.feitianzhu.fu700.App;
import com.feitianzhu.fu700.common.Constant;

/**
 * Created by Lee on 2017/9/3.
 */

public class Urls {
    public static final String HOST_URL =App.isDebug?"http://39.104.56.182": "http://app.fu700.com/";



    private static final String BASE_URL = Constant.Common_HEADER;

    /**
     * 注册
     */
    public static final String REGISTER = BASE_URL + "fhwl/commons/account/registeruser";

    /**
     * 登录
     */
    public static final String LOGIN = BASE_URL + "fhwl/commons/account/login";

    /**
     * 获取手机验证码：ype 1-注册 2-更换手机号码(旧号码) 3-更换手机号码(新号码) 4-找回密码 5-设置二级密码 6-找回二级密码
     */
    public static final String GET_SMSCODE = BASE_URL + "fhwl/commons/getsmscode";

    /**
     * 获取首页数据
     */
    public static final String GET_INDEX = BASE_URL + "fhwl/index/getindex";

    /**
     * 更换手机号
     */
    public static final String UPDATE_PHONE = BASE_URL + "fhwl/user/phone/updatephone";

    /**
     * 查询个人信息
     */
    public static final String GET_USERINFO = BASE_URL + "fhwl/user/getuserinfo";

    /**
     * 修改密码
     */
    public static final String UPDATE_ULPASS = BASE_URL + "fhwl/user/updateulpass";

    /**
     * 设置二级密码
     */
    public static final String SET_PAYPASS = BASE_URL + "fhwl/user/setpaypass";

    /**
     * 重置二级密码
     */
    public static final String UPDATE_UPAYPASS = BASE_URL + "fhwl/user/updateupaypass";

    /**
     * 找回登录密码
     */
    public static final String GET_MYPASSWORD = BASE_URL + "fhwl/user/getmypassword";

    /**
     * 找回二级支付密码
     */
    public static final String GET_UPAYPASS = BASE_URL + "fhwl/user/getupaypass";

    /**
     * 二级密码校验
     */
    public static final String CHECK_PAYPASS = BASE_URL + "fhwl/user/checkpaypass";
    /**
     *TH密码校验
     */
    public static final String POST_THPASS =  BASE_URL + "fhwl/commons/checkThPass";

    /**
     * 意见反馈
     */
    public static final String USER_FEEDBACK = BASE_URL + "fhwl/user/userfeedback";


    /**
     * 删除银行卡
     */
    public static final String DELETE_UBC = BASE_URL + "fhwl/user/bankcard/deleteubc";

    /**
     * 新增银行卡信息
     */
    public static final String ADD_UBC = BASE_URL + "fhwl/user/bankcard/addubc";

    /**
     * 获取用户银行卡列表
     */
    public static final String GET_USER_BCLIST = BASE_URL + "fhwl/user/bankcard/getbclist";

    /**
     * 获取银行信息列表
     */
    public static final String GET_BANKLIST = BASE_URL + "fhwl/commons/bank/getbanklist";
    /**
     * 获取线下支付信息
     */
    public static final String GET_OFFLINE = BASE_URL + "fhwl/sysparam/pay-acc";

    /**
     * 为我买单
     */
    public static final String PAY_FOR_ME = BASE_URL + "fhwl/order/buybill/create";

    /**
     * 修改已驳回订单（为我买单）
     */
    public static final String UPDATE_ORDER = BASE_URL + "fhwl/order/buybill/update";

    /**
     * 为我买单记录
     */
    public static final String PAY_FOR_ME_RECORD = BASE_URL + "fhwl/order/buybill/list";

    /**
     * 查询默认策划推广比例
     */
    public static final String DEFAULT_PROPORTION = BASE_URL + "fhwl/sysparam/deft";

    /**
     * 兑换
     */
    public static final String WITHDRAW = BASE_URL + "fhwl/withdrawal/create";

    /**
     * 兑换手续费
     */
    public static final String WITHDRAW_FEE_RATE = BASE_URL + "fhwl/withdrawal/fee-rate";

    /**
     * 人气商家列表
     */
    public static final String GET_RMLIST = BASE_URL + "fhwl/merchant/getrmlist";

    /**
     * 黄花梨详情
     */
    public static final String HUANGHUALI_WEBVIEW = BASE_URL + "fhwl/yellowpear/get";

    /**
     * 购买黄花梨
     */
    public static final String HUANGHUALI_BUY = BASE_URL + "fhwl/yellowpear/buy";
    /**
     * 黄花梨购买记录
     */
    public static final String HUANGHUALI_LIST = BASE_URL + "fhwl/yellowpear/list";

    /**
     * 消息列表
     */
    public static final String NOTICE_LIST = BASE_URL + "fhwl/pushmsg/list";

    /**
     * 注册协议
     */
    public static final String H5_REGISTER_PROTOCOL = HOST_URL + "/static/protocol.html";
    /**
     * 帮助
     */
    public static final String H5_HELPER = HOST_URL + "/static/help.html";

    /**
     * 关于
     */
    public static final String H5_ABOUT_ME = HOST_URL + "/static/about.html";

}
