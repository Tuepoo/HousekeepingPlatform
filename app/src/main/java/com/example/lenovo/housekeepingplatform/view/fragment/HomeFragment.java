package com.example.lenovo.housekeepingplatform.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.activity.CitySelecterActivity;
import com.example.lenovo.housekeepingplatform.activity.SearchActivity;
import com.example.lenovo.housekeepingplatform.activity.ServiceDetailActivity;
import com.example.lenovo.housekeepingplatform.adapter.PhotoPagerAdapter;
import com.example.lenovo.housekeepingplatform.module.recommand.BaseRecommandModel;
import com.example.lenovo.housekeepingplatform.view.viewpagerindictor.CirclePageIndicator;
import com.tuepoo.okhttp.listener.DisposeDataListener;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by lenovo on 2018/8/31.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    public static final int REQUEST_CITYCODE = 0x02;
    /**
     * UI
     */
    private View mContentView;
    private RecyclerView mRecycleView;
    private RelativeLayout mLocationView;
    private TextView mSearchView;
    private TextView mCityView;
    private ImageView mLoadingView;

    View mHeaderView;

    View categoryLayout;
    View shopcartLayout;
    View orderLayout;

    View couponLayout;
    private AutoScrollViewPager mViewPager;
    private CirclePageIndicator mPagerIndictor;
    private PhotoPagerAdapter mAdapter;


    RelativeLayout upLeftImgv;			//上-> 左
    RelativeLayout upRightTopImgv;		//上-> 右 -> 上
    RelativeLayout upRightBottomImgv;	//上-> 右 -> 下

    RelativeLayout bottomLeftImgv;	//下-> 左
    RelativeLayout bottomRightImgv;	//下-> 右

    /**
     * data
     */
    //private ServicesAdapter mAdapter;
    private BaseRecommandModel mRecommandData;

    public HomeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestReccommmandData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout,container,false);
        initView();

//        mCityView.setText(intent.getStringExtra("cityName"));
        return mContentView;

    }

    private void initView(){
        mLocationView = (RelativeLayout) mContentView.findViewById(R.id.location_view);
        mLocationView.setOnClickListener(this);
        mCityView = (TextView) mContentView.findViewById(R.id.area_view) ;
        mSearchView = (TextView) mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);

        categoryLayout = mContentView.findViewById(R.id.home_menu_categroy_layout);
        categoryLayout.setOnClickListener(this);
        shopcartLayout = mContentView.findViewById(R.id.home_menu_shopcart_layout);
        shopcartLayout.setOnClickListener(this);
        orderLayout = mContentView.findViewById(R.id.home_menu_order_layout);
        orderLayout.setOnClickListener(this);
        couponLayout = mContentView.findViewById(R.id.home_menu_coupon_layout);
        couponLayout.setOnClickListener(this);

        upLeftImgv = (RelativeLayout)mContentView.findViewById(R.id.up_left_imgv_layout);
        upLeftImgv.setOnClickListener(this);
        upRightTopImgv = (RelativeLayout)mContentView.findViewById(R.id.up_right_top_imgv_layout);
        upRightTopImgv.setOnClickListener(this);
        upRightBottomImgv = (RelativeLayout)mContentView.findViewById(R.id.up_right_bottom_imgv_layout);
        upRightBottomImgv.setOnClickListener(this);
        bottomLeftImgv = (RelativeLayout)mContentView.findViewById(R.id.bottom_left_imgv_layout);
        bottomLeftImgv.setOnClickListener(this);
        bottomRightImgv = (RelativeLayout)mContentView.findViewById(R.id.bottom_right_imgv_layout);
        bottomRightImgv.setOnClickListener(this);

        mViewPager = (AutoScrollViewPager) mContentView.findViewById(R.id.pager);
        mPagerIndictor = (CirclePageIndicator) mContentView.findViewById(R.id.pager_indictor_view);
        mAdapter = new PhotoPagerAdapter(mContext,true);
        mViewPager.setAdapter(mAdapter);
        mViewPager.startAutoScroll(3000);
        mPagerIndictor.setViewPager(mViewPager);
        //为listview添加头
//        mListView.setEmptyView(mHeaderView);
////
//        mLoadingView = (ImageView)mContentView.findViewById(R.id.loading_view);
//        mLoadingView.setVisibility(View.GONE);
//        AnimationDrawable anim = (AnimationDrawable)mLoadingView.getDrawable();
//        anim.start();
    }

    private void requestReccommmandData(){
        com.example.lenovo.housekeepingplatform.network.http.RequestCenter.requestServiceList(new DisposeDataListener(){

            @Override
            public void onSuccess(Object responseObj) {
                mRecommandData = (BaseRecommandModel)responseObj;
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });

    }

    private void showSuccessView(){
        if (mRecommandData.data.list != null && mRecommandData.data.list.size() > 0) {
            mLoadingView.setVisibility(View.GONE);
            mRecycleView.setVisibility(View.VISIBLE);
            //为listview添加头
//            mRecycleView.addHeaderView(new HomeHeaderLayout(mContext));
//            mAdapter = new CourseAdapter(mContext, mRecommandData.data.list);
//            mListView.setAdapter(mAdapter);
//            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    mAdapter.updateAdInScrollView();
//                }
//            });
        } else {
            showErrorView();
        }
    }

    private void showErrorView() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_view:
                Intent intent = new Intent(mContext, CitySelecterActivity.class);
                startActivityForResult(intent,REQUEST_CITYCODE);
                break;
            case R.id.search_view:
                Intent searchintent = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(searchintent);
                break;
            case R.id.home_menu_categroy_layout:
            case R.id.up_left_imgv_layout:
                Intent cleanintent = new Intent(mContext, ServiceDetailActivity.class);
                cleanintent.putExtra("SERVICE_TITLE","日常保洁");
                cleanintent.putExtra("SERVICE_SUB_TITLE","家庭室内表面清洁、物品整理、基础除尘除垢");
                cleanintent.putExtra("SERVICE_PIC",4);
                cleanintent.putExtra("SERVICE_TIME","2小时起");
                cleanintent.putExtra("SERVICE_PRICE","35");
                mContext.startActivity(cleanintent);
                break;
            case R.id.home_menu_shopcart_layout:
                Intent peopleintent = new Intent(mContext, ServiceDetailActivity.class);
                peopleintent.putExtra("SERVICE_TITLE","保姆");
                peopleintent.putExtra("SERVICE_TIME","1小时起");
                peopleintent.putExtra("SERVICE_PRICE","80");
                mContext.startActivity(peopleintent);
                break;
            case R.id.home_menu_order_layout:
                Intent repairintent = new Intent(mContext, ServiceDetailActivity.class);
                repairintent.putExtra("SERVICE_TITLE","家电维修");
                repairintent.putExtra("SERVICE_SUB_TITLE","3分钟相应，90天内免费上门返修");
                repairintent.putExtra("SERVICE_PIC",8);
                repairintent.putExtra("SERVICE_TIME","会员享8.5折");
                repairintent.putExtra("SERVICE_PRICE","100");
                mContext.startActivity(repairintent);
                break;
            case R.id.home_menu_coupon_layout:
                Intent moveintent = new Intent(mContext, ServiceDetailActivity.class);
                moveintent.putExtra("SERVICE_TITLE","搬家");
                moveintent.putExtra("SERVICE_SUB_TITLE","10公里运输及小件行李物品搬运");
                moveintent.putExtra("SERVICE_PIC",6);
                moveintent.putExtra("SERVICE_TIME","总价=基础费用+额外需求费用");
                moveintent.putExtra("SERVICE_PRICE","700");
                mContext.startActivity(moveintent);
                break;
            case R.id.up_right_top_imgv_layout:
                Intent hoodintent = new Intent(mContext, ServiceDetailActivity.class);
                hoodintent.putExtra("SERVICE_TITLE","油烟机清洗");
                hoodintent.putExtra("SERVICE_SUB_TITLE","清除油污、高温消毒、预防腐蚀、节省耗电");
                hoodintent.putExtra("SERVICE_PIC",4);
                hoodintent.putExtra("SERVICE_TIME","会员享8.5折");
                hoodintent.putExtra("SERVICE_PRICE","180");
                mContext.startActivity(hoodintent);
                break;
            case R.id.up_right_bottom_imgv_layout:
                Intent wcintent = new Intent(mContext, ServiceDetailActivity.class);
                wcintent.putExtra("SERVICE_TITLE","卫生间清洗");
                wcintent.putExtra("SERVICE_SUB_TITLE","清洗消毒、除虫除异味");
                wcintent.putExtra("SERVICE_PIC",4);
                wcintent.putExtra("SERVICE_TIME","会员享9折");
                wcintent.putExtra("SERVICE_PRICE","100");
                mContext.startActivity(wcintent);
                break;
            case R.id.bottom_left_imgv_layout:
                Intent kecheenintent = new Intent(mContext, ServiceDetailActivity.class);
                kecheenintent.putExtra("SERVICE_TITLE","厨房清洗");
                kecheenintent.putExtra("SERVICE_SUB_TITLE","油烟机+灶台清洗、水槽清理消毒");
                kecheenintent.putExtra("SERVICE_PIC",4);
                kecheenintent.putExtra("SERVICE_TIME","会员享9折");
                kecheenintent.putExtra("SERVICE_PRICE","50");
                mContext.startActivity(kecheenintent);
                break;
            case R.id.bottom_right_imgv_layout:
                Intent houseintent = new Intent(mContext, ServiceDetailActivity.class);
                houseintent.putExtra("SERVICE_TITLE","新房除甲醛");
                houseintent.putExtra("SERVICE_SUB_TITLE","专业设备、科学流程、十年质保");
                houseintent.putExtra("SERVICE_PIC",0);
                houseintent.putExtra("SERVICE_TIME","2小时起");
                houseintent.putExtra("SERVICE_PRICE","90");
                mContext.startActivity(houseintent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CITYCODE:
                if (resultCode == 1110) {
                    mCityView.setText(data.getStringExtra("cityName"));
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


}
