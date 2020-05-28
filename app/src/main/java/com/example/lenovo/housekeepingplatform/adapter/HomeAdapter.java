package com.example.lenovo.housekeepingplatform.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.module.recommand.RecommandBodyValue;
import com.example.lenovo.housekeepingplatform.util.ImageLoaderManager;

import java.util.ArrayList;

/**
 * Created by lenovo on 2019/3/26.
 */

public class HomeAdapter extends BaseAdapter{

    private LayoutInflater mInflate;
    private Context mContext;
    private ArrayList<RecommandBodyValue> mData;
    private ViewHolder mViewHolder;
    private ImageLoaderManager mImagerLoader;

    HomeAdapter(Context context, ArrayList<RecommandBodyValue> data){

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RecommandBodyValue value = (RecommandBodyValue) getItem(position);
        if (convertView == null){
            convertView.setTag(mViewHolder);
        }//有tag时
        else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //填充item的数据
        
        return convertView;
    }

    private static class ViewHolder {
        //所有Card共有属性

        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //Video Card特有属性
        private RelativeLayout mVieoContentLayout;
        private ImageView mShareView;

        //Video Card外所有Card具有属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mZanView;
        //Card One特有属性
        private LinearLayout mProductLayout;
        //Card Two特有属性
        private ImageView mProductView;
        //Card Three特有属性
        private ViewPager mViewPager;
    }

}
