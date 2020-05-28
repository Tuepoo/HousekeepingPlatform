package com.example.lenovo.housekeepingplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.module.service.ServiceCommentValue;
import com.example.lenovo.housekeepingplatform.util.ImageLoaderManager;

/**
 * Created by lenovo on 2019/4/13.
 */

public class ServiceCommentAdapter extends BaseAdapter {

    private LayoutInflater mInflate;
    private Context mContext;

    private ServiceCommentValue mData;
    private ViewHolder mViewHolder;
    private ImageLoaderManager mImagerLoader;

    public ServiceCommentAdapter(Context context, ServiceCommentValue data) {
        mContext = context;
        mData = data;
        mInflate = LayoutInflater.from(mContext);
        mImagerLoader = ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ServiceCommentValue value = (ServiceCommentValue) getItem(position);
        //无tag时
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflate.inflate(R.layout.item_comment_layout, parent, false);
            mViewHolder.mServiceView = (TextView) convertView.findViewById(R.id.item_service);
            mViewHolder.mServiceTimeView = (TextView) convertView.findViewById(R.id.item_service_time);
            mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //填充数据
        mViewHolder.mServiceView.setText(mData.title);
        mViewHolder.mServiceTimeView.setText(mData.time);
        mViewHolder.mPriceView.setText(mData.price);
        return convertView;
    }

//    public void addComment(ServiceCommentValue value) {
//        mData.add(0, value);
//        notifyDataSetChanged();
//    }
//
//    public int getCommentCount() {
//        return mData.size();
//    }

    private static class ViewHolder {

        TextView mServiceView;
        TextView mServiceTimeView;
        TextView mPriceView;

    }

}
