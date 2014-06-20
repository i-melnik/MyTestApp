//package com.melnik.odesktest.adapter;
//
//import android.support.v4.view.PagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.jfeinstein.jazzyviewpager.JazzyViewPager;
//
//import java.util.List;
//
///**
// * Created by i.melnik on 02.06.2014.
// */
//public class MyPagerAdapter extends PagerAdapter{
//
//    private JazzyViewPager mPager;
//    private List<View> pages;
//
//    public MyPagerAdapter(List<View> pages)
//    {
//        this.pages = pages;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, final int position) {
//        Object obj = super.instantiateItem(container, position);
//        mPager.setObjectForPosition(obj, position);
//        return obj;
//    }
//
//    @Override
//    public void destroyItem(View collection, int position, Object view){
//        ((JazzyViewPager) collection).removeView((View) view);
//    }
//
//    @Override
//    public int getCount() {
//        return pages.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return false;
//    }
//}
