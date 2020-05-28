package com.example.lenovo.housekeepingplatform.network.http;

/**
 * Tuepoo
 * 所有请求相关的地址
 */

public class HttpConstants {

    private static final String ROOT_URL = "http://housekeeping.com/api";

    /**
     * 请求本地服务信息
     */
    public static String SERVICE_LIST = ROOT_URL + "/fund/search.php";

    /**
     * 登录接口
     */
    public static String LOGIN = ROOT_URL + "/user/login.php";

    /**
     * 图灵机器人接口
     */
    public static String Tuling = "http://www.tuling123.com/openapi/api";

    /**
     * 服务详情接口
     */
    public static String SERVICE_DETAIL = ROOT_URL + "/product/service_detail.php";

}
