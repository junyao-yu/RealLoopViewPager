package com.realloop;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2016/8/5 16:54
 * Email：yujunyao@yonglibao.com
 */
public class AdvView extends LinearLayout{
    private ViewPager mViewPager;
    private ScrollPoints mScrollPoints;
    private AdvPagerAdapter mAdapter;
    private List<Integer> bannerList = new ArrayList<>();
    private List<ImageView> imgList = new ArrayList<>();
    private boolean isLoop = true;
    private int mCurrentPagePosition = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    break;
            }
        }
    };

    public AdvView(Context context) {
        this(context, null);
    }

    public AdvView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_adv, this);
        mScrollPoints = (ScrollPoints) view.findViewById(R.id.scrollpoint);
        mViewPager = (ViewPager) view.findViewById(R.id.adv_viewpager);
        mViewPager.addOnPageChangeListener(new OnPageChangeListenerImp());
    }

    private class OnPageChangeListenerImp implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            calculatePosition(position);
            mScrollPoints.changeSelectedPoint(mCurrentPagePosition - 1);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (ViewPager.SCROLL_STATE_IDLE == state) {
                mViewPager.setCurrentItem(mCurrentPagePosition, false);
            }
        }
    }

    public void setBanner(List<Integer> list, BannerListener bannerListener) {
        if(list.isEmpty()) {
            return ;
        }

        int iSize = list.size();

        if(iSize == 1) {//不自动循环播放
            bannerList.addAll(list);

            ImageView imgView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.layout_adv_img, null);
            imgList.add(imgView);

            mAdapter = new AdvPagerAdapter(bannerList, imgList, bannerListener);
            mViewPager.setAdapter(mAdapter);
        }else {//自动循环播放
            bannerList.addAll(list);

            Integer firstObj = list.get(0);
            Integer lastObj = list.get(iSize - 1);
            bannerList.add(0, lastObj);
            bannerList.add(firstObj);

            for(int i=0;i<bannerList.size();i++) {
                ImageView imgView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.layout_adv_img, null);
                imgList.add(imgView);
            }

            mScrollPoints.initPoints(getContext(), list.size(), 0);

            mAdapter = new AdvPagerAdapter(bannerList, imgList, bannerListener);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(1);

            autoScroll();
        }
    }

    private void autoScroll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isLoop) {
                    SystemClock.sleep(2000);
                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(0);
                    }
                }

            }
        }).start();
    }

    private void calculatePosition(int position) {
        if(position < 1) {
            mCurrentPagePosition = bannerList.size() - 2;
        }else if(position > bannerList.size() - 2) {
            mCurrentPagePosition = 1;
        }else {
            mCurrentPagePosition = position;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isLoop = false;
        mHandler = null;
    }

    public interface BannerListener {
        /**想要返回的参数可自行添加*/
        void backResult();
    }
}
