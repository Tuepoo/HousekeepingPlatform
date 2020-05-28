package com.example.lenovo.housekeepingplatform.module.speech;

import com.example.lenovo.housekeepingplatform.module.BaseModel;

import java.util.Map;

/**
 * Created by lenovo on 2018/9/1.
 */

public class intentModel extends BaseModel {
    public int code;
    public String intentName;
    public String actionName;
    public Map<String,String> parameters;
}
