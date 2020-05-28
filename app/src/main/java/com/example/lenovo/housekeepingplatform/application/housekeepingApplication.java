package com.example.lenovo.housekeepingplatform.application;

import android.app.Application;
import android.content.Context;

import com.example.lenovo.housekeepingplatform.db.DBManager;
import com.example.lenovo.housekeepingplatform.fresco.FrescoInitUtil;

/**
 * Created by lenovo on 2018/8/28.
 */


public class housekeepingApplication extends Application{

    private DBManager dbHelper;
    private static Context mContext;
    private  static  housekeepingApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //导入数据库
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        mApplication = this;
        mContext = getApplicationContext();
        initFrescoConfig();
    }

    public static housekeepingApplication getInstance(){

        return mApplication;
    }

    private void initFrescoConfig() {

        FrescoInitUtil.initFrescoConfig();
    }

    public static Context getContext() {

        return mContext;
    }


}
