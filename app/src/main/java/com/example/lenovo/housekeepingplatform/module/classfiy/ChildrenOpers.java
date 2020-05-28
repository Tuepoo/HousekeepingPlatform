package com.example.lenovo.housekeepingplatform.module.classfiy;

import java.util.List;

/**
 * Created by lenovo on 2019/4/2.
 */

public class ChildrenOpers {


    private List<DiscoverOper> banner;  //Banner

    private List<DiscoverOper> webview; // WebView

    public List<DiscoverOper> getBanner() {

        return banner;
    }

    public void setBanner(List<DiscoverOper> banner) {

        this.banner = banner;
    }

    public List<DiscoverOper> getWebview() {

        return webview;
    }

    public void setWebview(List<DiscoverOper> webview) {

        this.webview = webview;
    }

    @Override
    public String toString() {
        return "ChildrenOpers{" +
                "banner=" + banner +
                ", webview=" + webview +
                '}';
    }

}
