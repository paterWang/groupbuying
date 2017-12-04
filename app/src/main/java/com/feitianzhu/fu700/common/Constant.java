package com.feitianzhu.fu700.common;

import com.feitianzhu.fu700.App;
import com.feitianzhu.fu700.login.entity.UserInfoEntity;
import com.feitianzhu.fu700.model.MyPoint;
import com.feitianzhu.fu700.model.PayInfo;
import com.feitianzhu.fu700.model.UserAuth;

/**
 * Created by dicallc on 2017/9/4 0004.
 */

public class Constant {
  /*公共常量*/
  public final static String ACCESSTOKEN = "accessToken";
  public final static String USERID = "userId";
  public final static String BUSILICENSENAME = "busiLicenseName";
  public final static String REGISTENO = "registeNo";
  public final static String LEGALPERSON = "legalPerson";
  public final static String BUSINATURE = "busiNature";
  public final static String REALNAME = "realName";
  public final static String CERTIFTYPE = "certifType";
  public final static String CERTIFNO = "certifNo";
  public final static String PROVINCEID = "provinceId";
  public final static String PROVINCENAME = "provinceName";
  public final static String THPASS = "thPass";
  public final static String CITYID = "cityId";
  public final static String CITYNAME = "cityName";
  public static String CommonACCESSTOKEN = "1be1a0caf9b74ea1a0020ac0faef9727";
  public static String CommonUSERID = "2";
  public final static String VERI_SHOPS = "veri_shops";
  public final static String MERCHANTNAME = "merchantName";
  public final static String MERCHANTID = "merchantId";
  public final static String DTLADDR = "dtlAddr";
  public final static String SERVICEPHONE = "servicePhone";
  public final static String CLSID = "clsId";
  public final static String INTRODUCE = "introduce";
  public final static String LONGITUDE = "longitude";
  public final static String LATITUDE = "latitude";
  public final static String AVATAR = "avatar";
  public final static String FILES = "files";
  public final static String PAGEINDEX = "pageIndex";
  public final static String PAGEROWS = "pageRows";
  public final static String ISADMIN = "isadmin";
  public final static String ADDRESS = "address";
  public final static String TYPE = "type";
  public final static String DEVICETOKEN = "deviceToken";
  public final static String IDVALUE = "idValue";
  public final static String COLLECTID = "collectId";
  public final static String CONSUMEAMOUNT = "consumeAmount";
  public final static String PAYCHANNEL = "payChannel";
  public final static String SHOPS_MODEL = "shops_model";
  public final static String PAYPASS = "payPass";
  public final static String REPORTCONTENT = "reportContent";
  public final static String VALUEID = "valueId";
  public final static String URL = "h5_url";
  public final static String H5_TITLE = "h5_title";
  public final static String SERVICEID = "serviceId";
  public final static String AMOUNT = "amount";
  public final static String CHANNEL = "channel";
  public final static String AREAID = "areaId";
  public final static String AREANAME = "areaName";
  public final static String ORDERNO = "orderNo";
  public final static String CONTENT = "content";
  public final static String STAR = "star";
  public final static String CLIENTTYPE = "clientType";
  public final static int SuccessCode = 200;
  public final static int FailCode = 404;

  /*URL常量*/

  //生产服务器
  public final static String  Common_HEADER = App.isDebug? "http://39.104.56.182:27019/":"http://app.fu700.com/";

  public final static String LOADER_USERINFO = "fhwl/user/getuserinfo";
  public final static String LOADER_VERI_SHOPS_INFO = "fhwl/user/getmauth";
  public final static String LOADER_VERI_USER_INFO = "fhwl/user/getrauth";
  public final static String POST_REALAUTH = "fhwl/user/realauth";
  public final static String POST_SHOPS_VERI = "fhwl/user/merchantauth";
  public final static String LOAD_SHOPS_INFO = "fhwl/merchant/getmerchantinfo";
  public final static String LOAD_SHOPS_SERVICES = "fhwl/merchant/getmerchantservice";
  public final static String LOAD_SHOPS_EVALUTE = "fhwl/merchant/getmevallist";
  public final static String POST_COLLECT = "fhwl/collect/insertcollect";
  public final static String DELETE_COLLECT = "fhwl/collect/deletecollect";
  public final static String LOAD_SHOPS_TYPE = "fhwl/commons/merchant/getmclslist";
  public final static String LOAD_RECMOOND_SHOP_LIST = "fhwl/merchant/getrmlist";
  public final static String LOAD_SHOPS_INDEX = "fhwl/index/getshopindex";
  public final static String LOAD_NEARBY_SHOPS = "fhwl/merchant/getnearbymlist";
  public final static String LOAD_FU_FRIEND = "fhwl/commons/fufriends";
  public final static String POST_SHOP_PAY = "fhwl/order/buybill/merchant-order";
  public final static String UAPDATE = "fhwl/soft/newv";
  public final static String JUBAO = "fhwl/user/userreport";
  public final static String SERVICE_ORDERS = "fhwl/order/svbill/list";
  public final static String SHOP_ORDERS = "fhwl/order/buybill/buy-mo-list";
  public final static String DELETE_SERVICE = "fhwl/merchant/deletemerchantservice";
  public final static String WX_PAY_URL = "fhwl/recharge/create";
  public final static String SHOP_EVA = "fhwl/order/buybill/eval";
  public final static String PUSHMSG = "fhwl/pushmsg/umdt";
       /*~~~~~~~~~~~~ H5url~~~~~~~~~~~~*/

  public final static String MYMONEY_INDEX = Common_HEADER + "static/balance.html";

  public final static String CHECK_URL_HEAD = App.isDebug?"http://39.104.56.182":"http://app.hialipay.com";
        /*~~~~~~~~~~~~ H5url~~~~~~~~~~~~*/
  /**
   * 通过商户名称查询商户集合
   */
  public final static String LOAD_SHOPS_SEARCH_DATA = "fhwl/index/getmlistbyname";
  public final static String LOAD_SHOPS_WALLET = "fhwl/wallet/record";
  public final static String LOAD_USER_AUTH = "fhwl/commons/auth/getuserauth";
  /**
   * 创建商铺信息
   */
  public final static String POST_SHOPS_INFO = "fhwl/merchant/createmerchant";

  /**
   * 获取商铺类型
   */
  public final static String POST_SHOPS_TYPE = "fhwl/commons/merchant/getmclslist";

  /**
   * 获取个人资料信息
   */
  public final static String POST_MINE_INFO = "fhwl/user/getuserinfo";
  public final static String EDIT_SHOPS_INFO = "fhwl/merchant/updatemerchant";
  public final static String EDIT_MINE_INFO = "fhwl/user/updateuserinfo";

  public final static String POST_INTEREST_INFO = "fhwl/commons/getilist";
  public final static String POST_INDUSTRY_INFO = "fhwl/commons/getindustrylist";

  /**
   * 获取服务详情
   */
  public final static String POST_SERVICE_DETAIL_INFO = "fhwl/merchant/getmerchantserviceinfo";

  /**
   * 发布商铺服务
   */
  public final static String POST_PUSH_SHOPSERVICE = "fhwl/merchant/insertmerchantservice";
  /**
   * 编辑服务
   */
  public final static String POST_PUSH_EDITSERVICE = "fhwl/merchant/insertmerchantservice";

  /**
   * 获取热门服务或推荐商户
   */
  public final static String POST_HOT_SERVICE = "fhwl/merchant/getrslist";
  /**
   * 查询策划推广比例列表
   */
  public final static String POST_SEARCH_PERCENT = "fhwl/sysparam/feerates";
  /**
   * 我要收款
   */
  public final static String POST_MINE_COLLECTION_MONEY = "fhwl/qrcode/merchant/create";
  /**
   * 商家查询录单记录
   */
  public final static String POST_SHOP_RECORDEDETAIL = "fhwl/order/buybill/merchant-orders";
  /**
   * 商家录单，发送数据
   */
  public final static String POST_SHOP_RECORD_SEND = "fhwl/order/buybill/merchant/create";

  /**
   * 商家路单记录，驳回订单重新提交
   */
  public final static String POST_SHOP_RECORED_RETRY = "fhwl/order/buybill/merchant/update";

  /**
   * 上传头像
   */
  public final static String POST_UPLOAD_PIC = "fhwl/user/uploadheadimg";
  /**
   * 我的收藏
   */
  public final static String POST_MINE_COLLECTION = "fhwl/user/getcollect";
  /**
   * 购买服务
   */
  public final static String POST_BUY_SERVICE = "fhwl/order/svbill/create";
  /**
   * 我的二维码
   */
  public final static String POST_MINE_QRCODE = "fhwl/share/app";

  /**
   * 分享的信息
   */
  public final static String GET_SHARED_INFO = "fhwl/share/people";

  /**
   * 联盟级别列表查询
   */
  public final static String POST_UNION_LEVEL = "fhwl/grade/list";
  public final static String POST_UNION_LEVEL_PAY = "fhwl/grade/buy"; //联盟级别 支付
  public final static String POST_UNION_APPLY_RECORD = "fhwl/grade/buy-record"; // 联盟申请记录

  /**
   * 总积分
   */
  public final static String POST_TOTALSCORE = "fhwl/points/get";
  public final static String POST_SCORE_GET = "fhwl/points/exch"; //积分兑换
  public final static String TOTAL_DETAIL = "http://118.190.156.13/static/points.html";

  public final static String POST_RELEASE_SCORE_DETAIL="fhwl/points/frozenpd"; //待释放积分明细

  /*内存缓存区*/
  /**
   * 定位
   */
  public static MyPoint mPoint;
  public static String mCity;

  public static final String SP_PHONE = "phone";
  public static final String SP_PASSWORD = "password";

  public static String ACCESS_TOKEN = "";
  public static String LOGIN_USERID = "";
  public static String DeviceToken_Value = "";
  public static String PHONE = "";

  public static UserInfoEntity USER_INFO;

  /**
   * 获取授权信息成功失败
   */
  public static boolean loadUserAuth = false;
  public static UserAuth mUserAuth;
  public static String provinceId;
  public static String cityId;
  public static String INTENT_GET_SET_PSW_TYPE = "intent_get_set_psw_type";
  public static String INTENT_IS_CHANGE_LOGIN_PWD = "is_change_login_pwd";
  public static String INTENT_OLD_PHONE = "intent_old_phone";
  public static String INTENT_OLD_SMSCODE = "intent_old_smscode";
  public static String INTENT_SELECTET_PAY_MODEL = "intent_selectet_pay_model";
  public static String INTENT_SELECTET_BANKCARD = "intent_selectet_bankcard";
  public static String INTENT_BALANCE = "intent_balance";
  public static String INTENT_WITHDRAW_TYPE = "intent_withdraw_type";
  public static String INTENT_REJECT_RECORD = "intent_reject_record";

  public static final int PAY_FOR_ME_STATUS_ING = 0;
  public static final int PAY_FOR_ME_STATUS_COM = 1;
  public static final int PAY_FOR_ME_STATUS_REJ = -1;

  public static final String PAY_FOR_ME_START_PAGE = "1";
  public static final String PAY_FOR_ME_PAGE_SIZE = "20";
  public static  String WX_APP_ID = "";

  public static int PayFlag = PayInfo.NoPay;
}
