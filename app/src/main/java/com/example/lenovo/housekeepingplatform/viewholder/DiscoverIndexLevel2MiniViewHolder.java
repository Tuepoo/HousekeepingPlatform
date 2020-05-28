package com.example.lenovo.housekeepingplatform.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.adapter.ExRvItemViewHolderBase;
import com.example.lenovo.housekeepingplatform.module.classfiy.Oper;
import com.example.lenovo.housekeepingplatform.util.TextUtil;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2019/4/2.
 */

public class DiscoverIndexLevel2MiniViewHolder extends ExRvItemViewHolderBase {


//    @BindView(R.id.tvName)
    TextView mTvName;

    public DiscoverIndexLevel2MiniViewHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.classify_index_level2_vh_mini);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Oper oper) {

        mTvName = (TextView)getConvertView().findViewById(R.id.tvName) ;

        if (oper == null) {
            mTvName.setText(TextUtil.TEXT_EMPTY);

        } else {
            mTvName.setText(oper.getTitle());

        }
    }
}
