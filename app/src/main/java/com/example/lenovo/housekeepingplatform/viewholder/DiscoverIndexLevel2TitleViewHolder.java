package com.example.lenovo.housekeepingplatform.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.adapter.ExRvItemViewHolderBase;
import com.example.lenovo.housekeepingplatform.module.classfiy.DiscoverOper;
import com.example.lenovo.housekeepingplatform.util.TextUtil;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2019/4/2.
 */

public class DiscoverIndexLevel2TitleViewHolder extends ExRvItemViewHolderBase {

//    @BindView(R.id.tvTitle)
    TextView mTvTitle;


    public DiscoverIndexLevel2TitleViewHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.classify_index_level2_vh_title);

        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(DiscoverOper oper) {

        mTvTitle = (TextView)getConvertView().findViewById(R.id.tvTitle);


        if (oper == null) {

            mTvTitle.setText(TextUtil.TEXT_EMPTY);

        } else {
            mTvTitle.setText(oper.getTitle());
        }

    }
}
