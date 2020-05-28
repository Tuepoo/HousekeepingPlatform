package com.example.lenovo.housekeepingplatform.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.adapter.ExRvItemViewHolderBase;
import com.example.lenovo.housekeepingplatform.module.classfiy.Oper;
import com.example.lenovo.housekeepingplatform.util.TextUtil;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2019/4/2.
 */

public class DiscoverIndexLevel1ViewHolder extends ExRvItemViewHolderBase {

//    @BindView(R.id.rlRoot)
    RelativeLayout mRlRoot;

//    @BindView(R.id.tvName)
    TextView mTvName;

//    @BindView(R.id.ivTip)
    ImageView mIvTip;

    public DiscoverIndexLevel1ViewHolder(ViewGroup viewGroup) {

        super(viewGroup, R.layout.classify_index_level1_vh);
        ButterKnife.bind(this, itemView);
    }


    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Oper oper, boolean isSelected) {
        mIvTip = (ImageView)getConvertView().findViewById(R.id.ivTip) ;
        mRlRoot = (RelativeLayout)getConvertView().findViewById(R.id.rlRoot) ;
        mTvName = (TextView) getConvertView().findViewById(R.id.tvName);
        mTvName.setText(oper == null ? TextUtil.TEXT_EMPTY : oper.getTitle());
        if (isSelected)
            setSelectedStyle();
        else
            setNormalStyle();
    }

    public void setSelectedStyle() {

        //        mIvTip.setVisibility(View.VISIBLE);
        mTvName.setTextColor(0XFFFF2A24);
        mTvName.setTextSize(13.4f);
        mRlRoot.setBackgroundColor(0XFFFFFF);
    }

    public void setNormalStyle() {

        //        mIvTip.setVisibility(View.INVISIBLE);
        mTvName.setTextColor(0XFF444444);
        mTvName.setTextSize(12.5f);
        mRlRoot.setBackgroundColor(0XFFF6F6F6);
    }
}

