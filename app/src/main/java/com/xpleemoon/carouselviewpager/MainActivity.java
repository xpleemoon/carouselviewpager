package com.xpleemoon.carouselviewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xpleemoon.view.CarouselPagerAdapter;
import com.xpleemoon.view.CarouselViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CarouselViewPager mCarouselView;
    private CarouselPagerAdapter mAdapter;
    private IndicatorDotView mIndicatorDotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCarouselView = (CarouselViewPager) findViewById(R.id.carousel);
        mAdapter = new SimpleCarouselAdapter(mCarouselView,
                new int[]{R.layout.page1, R.layout.page2, R.layout.page3});
        mCarouselView.setAdapter(mAdapter);
        mIndicatorDotView = (IndicatorDotView) findViewById(R.id.indicator);
        mIndicatorDotView.setCount(mAdapter.getCountOfVisual(), 0); // init indicator
        mCarouselView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // position % mAdapter.getCountOfVisual()——因为CarouselViewPager实现的原因，这里的position已经不是我们视觉上所看到的position了
                mIndicatorDotView.setSelectPosition(position % mAdapter.getCountOfVisual());
            }
        });

        findViewById(R.id.resume).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resume:
                mCarouselView.setLifeCycle(CarouselViewPager.RESUME);
                Toast.makeText(getApplicationContext(), "resume carousel", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pause:
                mCarouselView.setLifeCycle(CarouselViewPager.PAUSE);
                Toast.makeText(getApplicationContext(), "pause carousel", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private static class SimpleCarouselAdapter extends CarouselPagerAdapter<CarouselViewPager> {
        private int[] viewResIds;

        public SimpleCarouselAdapter(CarouselViewPager viewPager, int[] viewResIds) {
            super(viewPager);
            this.viewResIds = viewResIds;
        }

        @Override
        public int getCountOfVisual() {
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
