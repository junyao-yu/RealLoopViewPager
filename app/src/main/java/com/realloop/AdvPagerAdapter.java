package com.realloop;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2016/8/5 17:41
 * Email：yujunyao@yonglibao.com
 */
public class AdvPagerAdapter extends PagerAdapter {
    private List<Integer> bannerList = new ArrayList<>();
    private List<ImageView> imgList = new ArrayList<>();
    private AdvView.BannerListener bannerListener;

    public AdvPagerAdapter(List<Integer> bannerList, List<ImageView> imgList, AdvView.BannerListener bannerListener) {
        this.bannerList = bannerList;
        this.imgList = imgList;
        this.bannerListener = bannerListener;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index = position % getCount();
        ImageView imageView = imgList.get(index);
        //TODO 加载网络图片
        imageView.setBackgroundResource(bannerList.get(index));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bannerListener.backResult();
            }
        });
        container.addView(imageView);
        return imageView;
    }

}
