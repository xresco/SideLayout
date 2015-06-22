package com.abed.cobi.View.Components;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
/**
 * Created by abed on 6/22/15.
 */
public class WidthAdjustableView  extends CardView {

    public WidthAdjustableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(height, height);
    }

}