package com.example.lenovo.housekeepingplatform.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.util.ImageLoaderManager;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by lenovo on 2019/3/30.
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ImageView[] mImageViews = new ImageView[4];
    private boolean mIsMatch;
    private String[] mData;

    private ImageLoaderManager mLoader;

    public PhotoPagerAdapter(Context context ,boolean isMatch) {
        mContext = context;
//        mImageViews = imageviews;
        mIsMatch = isMatch;
        mLoader = ImageLoaderManager.getInstance(mContext);
        mData = context.getResources().getStringArray(R.array.viewpager_ad);
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        ImageView photoView;
        if (mIsMatch) {
            photoView = new ImageView(mContext);
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
//            photoView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext,
//                            ServiceDetailActivity.class);
//                    mContext.startActivity(intent);
//                }
//            });
        } else {
            photoView = new PhotoView(mContext);
        }
        mLoader.displayImage(photoView, "drawable://"+ (R.drawable.viewpager1+position));
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
