package com.example.lenovo.housekeepingplatform.util;

import com.example.lenovo.housekeepingplatform.module.classfiy.ChildrenOpers;
import com.example.lenovo.housekeepingplatform.module.classfiy.DiscoverOper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2019/4/4.
 */

public class DiscoverIndexUtil {

    /**
     * 过滤 WebView列表和Banner列表为null 情况
     *
     * @param opers
     */
    public static void filterNullOperList(List<DiscoverOper> opers) {

        if (CollectionUtil.isEmpty(opers))
            return;

        Iterator<DiscoverOper> iter = opers.iterator();
        while (iter.hasNext()) {

            if (isFilterNullOper(iter.next()))
                iter.remove();
        }
    }

    /**
     * 过滤为NULL 运营位
     *
     * @param discoverOper
     * @return
     */
    private static boolean isFilterNullOper(DiscoverOper discoverOper) {

        if (discoverOper == null)
            return true;

        ChildrenOpers childrenOpers = discoverOper.getChildren();
        if (childrenOpers == null)
            return true;

        return CollectionUtil.isEmpty(childrenOpers.getWebview()) && (CollectionUtil.isEmpty(childrenOpers.getBanner()));
    }

    /**
     * 处理 右侧运营位 合并数据
     *
     * @param opers
     */
    public static List<DiscoverOper> merageOperLevel2List(List<DiscoverOper> opers) {

        if (opers == null)
            return null;

        List<DiscoverOper> merageOpers = new ArrayList<DiscoverOper>();
        for (int i = 0; i < CollectionUtil.size(opers); i++) {

            DiscoverOper oper = CollectionUtil.getItem(opers, i);
            if (oper == null)
                continue;

            ChildrenOpers childrenOpers = oper.getChildren();
            if (childrenOpers == null)
                continue;

            oper.setElement_type(DiscoverOper.TYPE_TITLE);
            oper.setParentTitle(oper.getTitle());
            oper.setParentPosition(i);
            oper.setChildPosition(i);
            oper.setParentId(oper.getElement_id());
            merageOpers.add(oper);

            // Banner
            List<DiscoverOper> operBanners = childrenOpers.getBanner();

            for (int k = 0; k < CollectionUtil.size(operBanners); k++) {

                DiscoverOper bannerOper = operBanners.get(k);
                bannerOper.setParentTitle(oper.getTitle());
                bannerOper.setParentPosition(i);
                bannerOper.setChildPosition(k);
                bannerOper.setParentId(oper.getElement_id());
                merageOpers.add(bannerOper);
            }

            // WebView
            List<DiscoverOper> operWebViews = childrenOpers.getWebview();

            for (int j = 0; j < CollectionUtil.size(operWebViews); j++) {

                DiscoverOper webViewOper = operWebViews.get(j);
                webViewOper.setParentTitle(oper.getTitle());
                webViewOper.setParentPosition(i);
                webViewOper.setChildPosition(j);
                webViewOper.setParentId(oper.getElement_id());
                merageOpers.add(webViewOper);
            }


        }
        return merageOpers;
    }

}
