package com.realloop;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2016/8/8 14:27
 * Email：yujunyao@yonglibao.com
 */
public class ScrollPoints extends LinearLayout {
    private List<ImageView> points = new ArrayList<ImageView>();
    private LinearLayout pointBox;
    private int marginWidth = 4; // 点点点之间的距离
    private int unfocusImageID; // 正常情况下图标
    private int focusImageID; // 选中状态下图标

    public ScrollPoints(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initPoints(Context context, int count, int selected) {
        initPoints(context, count, selected, R.drawable.index_unchoose, R.drawable.index_choose);
    }

    public void initPoints(Context context, int count, int selected, int mUnfocusImageID, int mFocusImageID) {
        pointBox = new LinearLayout(context);
        points.clear();
        unfocusImageID = mUnfocusImageID;
        focusImageID = mFocusImageID;

        for (int i = 0; i < count; i++) {
            ImageView slidePot = new ImageView(context);
            LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(marginWidth, 0, marginWidth, 0);
            slidePot.setLayoutParams(lp);

            if (i == selected) {
                slidePot.setBackgroundResource(focusImageID);
            } else {
                slidePot.setBackgroundResource(unfocusImageID);
            }

            points.add(slidePot);
            pointBox.addView(slidePot);
        }
        removeAllViews();
        addView(pointBox);
    }

    public void changeSelectedPoint(int position) {
        position %= points.size();
        if (position < 0){
            position = points.size() + position;
        }

        for (int i = 0; i < points.size(); i++) {
            ImageView point = points.get(i);
            if (i == position) {
                point.setBackgroundResource(focusImageID);
            } else {
                point.setBackgroundResource(unfocusImageID);
            }
        }
    }

}
