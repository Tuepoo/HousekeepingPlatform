package com.example.lenovo.housekeepingplatform.view.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.adapter.PhotoPagerAdapter;
import com.example.lenovo.housekeepingplatform.view.viewpagerindictor.CirclePageIndicator;
import com.tuepoo.adutil.ImageLoaderUtil;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by lenovo on 2019/3/30.
 */

public class HomeHeaderLayout extends RelativeLayout {

    private Context mContext;

    /**
     * UI
     */
    private RelativeLayout mRootView;
    private AutoScrollViewPager mViewPager;
    private CirclePageIndicator mPagerIndictor;
//    private TextView mHotView;
    private PhotoPagerAdapter mAdapter;
    private ImageView[] mImageViews = new ImageView[4];
    private LinearLayout mFootLayout;

    /**
     * Data
     */
//    private RecommandHeadValue mHeaderValue;
    private ImageLoaderUtil mImageLoader;

    public HomeHeaderLayout(Context context) {
        this(context, null);
    }

    public HomeHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mImageLoader = ImageLoaderUtil.getInstance(mContext);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootView = (RelativeLayout) inflater.inflate(R.layout.listview_home_head_layout, this);
        mViewPager = (AutoScrollViewPager) mRootView.findViewById(R.id.pager);
        mPagerIndictor = (CirclePageIndicator) mRootView.findViewById(R.id.pager_indictor_view);
//        mHotView = (TextView) mRootView.findViewById(R.id.zuixing_view);
//        mImageViews[0].setImageResource(R.drawable.viewpager1);
//        mImageViews[1] .setImageResource(R.drawable.viewpager1);
//        mImageViews[2] .setImageResource(R.drawable.viewpager1);
//        mImageViews[3] .setImageResource(R.drawable.viewpager1);
//        mFootLayout = (LinearLayout) mRootView.findViewById(R.id.content_layout);

//        mAdapter = new PhotoPagerAdapter(mContext,true);
//        mViewPager.setAdapter(mAdapter);
//        mViewPager.startAutoScroll(3000);
//        mPagerIndictor.setViewPager(mViewPager);

//        for (int i = 0; i < mImageViews.length; i++) {
//            mImageLoader.displayImage(mImageViews[i], mHeaderValue.middle.get(i));
//        }

//        for (RecommandFooterValue value : mHeaderValue.footer) {
//            mFootLayout.addView(createItem(value));
//        }
//        mHotView.setText(mContext.getString(R.string.today_zuixing));
    }

//    private HomeBottomItem createItem(RecommandFooterValue value) {
//        HomeBottomItem item = new HomeBottomItem(mContext, value);
//        return item;
//    }

}
