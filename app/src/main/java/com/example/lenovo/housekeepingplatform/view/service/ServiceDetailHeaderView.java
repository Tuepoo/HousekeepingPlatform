package com.example.lenovo.housekeepingplatform.view.service;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.module.service.ServiceHeaderValue;
import com.tuepoo.adutil.ImageLoaderUtil;

/**
 * Created by lenovo on 2019/4/13.
 */

public class ServiceDetailHeaderView extends RelativeLayout {

    private Context mContext;
    /**
     * UI
     */
    private RelativeLayout mRootView;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private ImageView mPhotoView;
    /**
     * data
     */
    private ServiceHeaderValue mData;
    private ImageLoaderUtil mImageLoader;

    public ServiceDetailHeaderView(Context context, ServiceHeaderValue value) {
        this(context, null, value);
    }

    public ServiceDetailHeaderView(Context context, AttributeSet attrs, ServiceHeaderValue value) {
        super(context, attrs);
        mContext = context;
        mData = value;
        mImageLoader = ImageLoaderUtil.getInstance(mContext);
        initView();
    }

    private void initView() {
        mRootView = (RelativeLayout) LayoutInflater.from(mContext).
                inflate(R.layout.listview_service_comment_head_layout, this);
        mTitleView = (TextView) mRootView.findViewById(R.id.title_view);
        mSubTitleView = (TextView) mRootView.findViewById(R.id.sub_title_view);
        mPhotoView = (ImageView) mRootView.findViewById(R.id.photo_view) ;
        mImageLoader.displayImage(mPhotoView, "drawable://"+ (R.drawable.classify1+mData.pic));
        mTitleView.setText(mData.title);
        mSubTitleView.setText(mData.sub_title);

    }


}
