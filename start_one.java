package com.koddev.chatapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;


public class start_one extends AppCompatActivity {

    ImageView logo,splashImg;
    ImageView appName;
    LottieAnimationView lottieAnimationView;
    private static final int NUM_PAGES=3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_one);

        logo = findViewById (R.id.Logo);
       // appName=findViewById(R.id.app_name);
        splashImg=findViewById(R.id.img);
        lottieAnimationView=findViewById(R.id.Lottie);

        viewPager=findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        splashImg.animate().translationY(-2000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
       // appName.animate().translationY(1400).setDuration(1009).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1500).setDuration(1000).setStartDelay(4000);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm)
        {
            super(fm);
        }

        @Override
        @NonNull
        public Fragment getItem(int position){
            switch (position){
                case 0:
                OnBoardingFragment1 tab1=new OnBoardingFragment1();
                return tab1;
                case 1:
                    OnBoardingFragment2 tab2=new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3=new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount(){
            return NUM_PAGES;
        }

    }
}
