package com.example.lenovo.housekeepingplatform.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lenovo.housekeepingplatform.adapter.ExRvItemViewHolderBase;
import com.example.lenovo.housekeepingplatform.util.DensityUtil;
import com.example.lenovo.housekeepingplatform.viewholder.DiscoverIndexLevel2BannerViewHolder;
import com.example.lenovo.housekeepingplatform.viewholder.DiscoverIndexLevel2MiniViewHolder;
import com.example.lenovo.housekeepingplatform.viewholder.DiscoverIndexLevel2TitleViewHolder;

/**
 * Created by lenovo on 2019/4/4.
 */

public class DiscoverIndexLevel2Decoration extends ExRvDecoration {

    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        ExRvItemViewHolderBase viewHolder = (ExRvItemViewHolderBase) parent.getChildViewHolder(view);
        if (viewHolder instanceof DiscoverIndexLevel2TitleViewHolder) {

            outRect.top = DensityUtil.dip2px(20f);
            outRect.left = DensityUtil.dip2px(14f);
            outRect.right = outRect.left;

        } else if (viewHolder instanceof DiscoverIndexLevel2MiniViewHolder) {

            GridLayoutManager.LayoutParams sglm = (GridLayoutManager.LayoutParams) viewHolder.getConvertView().getLayoutParams();
            int spanIndex = sglm.getSpanIndex();

            if (spanIndex == 0) {

                outRect.left = DensityUtil.dip2px(25);
            } else if (spanIndex == 2) {

                outRect.right = DensityUtil.dip2px(25);
            }

            outRect.top = DensityUtil.dip2px(15f);

        } else if (viewHolder instanceof DiscoverIndexLevel2BannerViewHolder) {

            outRect.top = DensityUtil.dip2px(19f);
            outRect.left = DensityUtil.dip2px(10f);
            outRect.right = outRect.left;
        }
    }
}