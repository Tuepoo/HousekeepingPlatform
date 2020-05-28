package com.example.lenovo.housekeepingplatform.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by lenovo on 2018/8/30.
 */

public class MoreImageView extends AppCompatImageView {

    public MoreImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        clipPath.addCircle((float)((getRight()-getLeft()) / 2),((float)((getBottom()-getTop())) / 2), (float)(w / 2),Path.Direction.CCW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}

