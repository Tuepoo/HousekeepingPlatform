package com.example.lenovo.housekeepingplatform.viewholder;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.adapter.ExRvItemViewHolderBase;
import com.example.lenovo.housekeepingplatform.fresco.FrescoImageView;
import com.example.lenovo.housekeepingplatform.module.classfiy.Oper;
import com.example.lenovo.housekeepingplatform.util.DensityUtil;
import com.example.lenovo.housekeepingplatform.util.ViewUtil;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2019/4/2.
 */
public class DiscoverIndexLevel2BannerViewHolder extends ExRvItemViewHolderBase {

//    @BindView(R.id.fivCover)
    FrescoImageView mAivCover;

    private int mBannerWidth;

    public DiscoverIndexLevel2BannerViewHolder(ViewGroup viewGroup, int bannerWidth) {
        super(viewGroup, R.layout.classify_index_level2_vh_banner);

        ButterKnife.bind(this, itemView);
        mBannerWidth = bannerWidth;
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Oper oper) {

        mAivCover = (FrescoImageView) getConvertView().findViewById(R.id.fivCover);

        if (oper == null) {
            mAivCover.setImageUri((String) null);
        } else {
            ViewUtil.scaleLayoutParams(mAivCover, oper.getPic_width(), oper.getPic_height(), mBannerWidth, DensityUtil.dip2px(80f));
            Uri uri = Uri.parse("res://com.example.lenovo.housekeepingplatform/"+(R.drawable.classify1+oper.getIndex()));
            mAivCover.setImageUri(uri);
        }

    }
}
