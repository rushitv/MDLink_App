package com.mdlink;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Mr.Prashant_Patel on 01-09-2018.
 */
public class CustomView extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    int[] imgdata;


    public CustomView(Guide_Page advocate_uplode_info, int[] imgdata) {
        this.context = advocate_uplode_info;
        this.imgdata = imgdata;
    }

    @Override
    public int getCount() {
        return imgdata.length;

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.customviewpager, container,false);
        ImageView imageView =  view.findViewById(R.id.cus_pagerview);
        imageView.setImageResource(imgdata[position]);
        container.addView(view);
        /*ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);*/


      /*  viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });*/


        return view;

    }
}


