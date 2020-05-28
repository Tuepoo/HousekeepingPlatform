package com.example.lenovo.housekeepingplatform.network.http;

import com.example.lenovo.housekeepingplatform.module.recommand.BaseRecommandModel;
import com.example.lenovo.housekeepingplatform.module.service.ServiceModel;
import com.example.lenovo.housekeepingplatform.module.speech.SpeechModel;
import com.example.lenovo.housekeepingplatform.module.user.User;
import com.tuepoo.okhttp.CommonOkHttpClient;
import com.tuepoo.okhttp.listener.DisposeDataHandle;
import com.tuepoo.okhttp.listener.DisposeDataListener;
import com.tuepoo.okhttp.request.CommonRequest;
import com.tuepoo.okhttp.request.RequestParams;

/**
 * Created by lenovo on 2018/8/31.
 */

public class RequestCenter {

    public static void postGetRequest(String url, RequestParams params, DisposeDataListener listener,Class<?> clazz){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,params),new DisposeDataHandle(listener,clazz));
    }

    public static void postPostRequest(String url,RequestParams params,DisposeDataListener listener,Class<?> clazz){
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url,params),new DisposeDataHandle(listener,clazz));
    }

    /**
     * 用户登录请求
     *
     * @param userName
     * @param passwd
     * @param listener
     */
    public static void login(String userName,String passwd,DisposeDataListener listener){

        RequestParams params = new RequestParams();
        params.put("mb",userName);
        params.put("pwd",passwd);
        RequestCenter.postGetRequest(HttpConstants.LOGIN,params,listener,User.class);
    }

    public static void requestServiceList(DisposeDataListener listener){
        RequestCenter.postGetRequest(HttpConstants.SERVICE_LIST,null,listener, BaseRecommandModel.class);
    }

    public static void requestTuling(String key,String droph,DisposeDataListener listener){
        RequestParams params = new RequestParams();
        params.put("key",key);
        params.put("info",droph);
        RequestCenter.postGetRequest(HttpConstants.Tuling,params,listener, SpeechModel.class);

    }

    /**
     * 请求服务详情
     *
     * @param
     */
    public static void requestServiceDetail(String serviceId, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("serviceId", serviceId);
        RequestCenter.postGetRequest(HttpConstants.SERVICE_DETAIL, params, listener, ServiceModel.class);
    }


}
