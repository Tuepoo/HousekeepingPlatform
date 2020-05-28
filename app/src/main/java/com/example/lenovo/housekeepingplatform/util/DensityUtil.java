package com.example.lenovo.housekeepingplatform.util;

import android.content.Context;

import com.example.lenovo.housekeepingplatform.application.housekeepingApplication;

/**
 * Created by lenovo on 2019/4/2.
 */

public class DensityUtil {

    private static float scale = housekeepingApplication.getContext().getResources().getDisplayMetrics().density;

    /**
     * dp 转 px
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {

        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {

        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     *
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
