package com.example.lenovo.housekeepingplatform.module.classfiy;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by lenovo on 2019/4/4.
 */

public class DiscoverIndexResult {


    private List<DiscoverOper> discoverList;
    private List<DiscoverOper> localLevel2List;

    public List<DiscoverOper> getLocalLevel2List() {

        return localLevel2List;
    }

    public void setLocalLevel2List(List<DiscoverOper> localLevel2List) {

        this.localLevel2List = localLevel2List;
    }

    public List<DiscoverOper> getDiscoverList() {

        return discoverList;
    }

    @JSONField(name = "zhekou_discover_left_nav")
    public void setDiscoverList(List<DiscoverOper> discoverList) {

        this.discoverList = discoverList;
    }

    @Override
    public String toString() {
        return "DiscoverIndexResult{" +
                "discoverList=" + discoverList +
                '}';
    }

}
