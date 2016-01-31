package com.xpleemoon.carouselviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xpleemoon.library.CarouselPagerAdapter;
import com.xpleemoon.library.CarouselViewPager;

public class MainActivity extends AppCompatActivity {
    private CarouselViewPager mCarouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCarouselView = (CarouselViewPager) findViewById(R.id.carousel);
        PagerAdapter adapter = new SimpleCarouselAdapter(mCarouselView,
                new int[]{R.layout.page1, R.layout.page2, R.layout.page3});
        mCarouselView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCarouselView.setLifeCycle(CarouselViewPager.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCarouselView.setLifeCycle(CarouselViewPager.PAUSE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCarouselView.setLifeCycle(CarouselViewPager.DESTROY);
    }

    private static class SimpleCarouselAdapter extends CarouselPagerAdapter<CarouselViewPager> {
        private int[] viewResIds;

        public SimpleCarouselAdapter(CarouselViewPager viewPager, int[] viewResIds) {
            super(viewPager);
            this.viewResIds = viewResIds;
        }

        @Override
        public int getRealDataCount() {
            return viewResIds != null ? viewResIds.length : 0;
        }

        @Override
        public Object instantiateRealItem(ViewGroup container, int position) {
            int resId = viewResIds[position];
            View bannerView = LayoutInflater.from(container.getContext()).inflate(resId, null);
            container.addView(bannerView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return bannerView;
        }
    }
}
