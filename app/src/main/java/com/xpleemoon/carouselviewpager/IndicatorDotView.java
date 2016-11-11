package com.xpleemoon.carouselviewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * indicator
 * <ul>
 * <li>default style{@link com.xpleemoon.carouselviewpager.R.drawable#shape_indicator_dot_default default style}
 * <li>{@link com.xpleemoon.carouselviewpager.R.drawable#shape_indicator_dot_selected selected style}
 * </ul>
 *
 * @author xpleemoon
 */
public class IndicatorDotView extends LinearLayout {
    private int mCount;
    private int mDotDefaultResId;
    private int mDotSelectedResId;
    private int mSelectedPosition;

    public IndicatorDotView(Context context) {
        super(context);
        init(null);
    }

    public IndicatorDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public IndicatorDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorDotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IndicatorDotView);
            mCount = typedArray.getInt(R.styleable.IndicatorDotView_dotCount, 0);
            mDotDefaultResId = typedArray.getResourceId(R.styleable.IndicatorDotView_dotDefault, R.drawable.shape_indicator_dot_default);
            mDotSelectedResId = typedArray.getResourceId(R.styleable.IndicatorDotView_dotSelected, R.drawable.shape_indicator_dot_selected);
            mSelectedPosition = typedArray.getInt(R.styleable.IndicatorDotView_dotSelectedPosition, 0);
            typedArray.recycle();
        }
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        initDots();
    }

    private void initDots() {
        int dotMargin = (int) getResources().getDimension(R.dimen.dot_margin);
        for (int i = 0; i < mCount; i++) {
            ImageView dot = new ImageView(getContext());
            dot.setBackgroundResource(i == mSelectedPosition ? mDotSelectedResId : mDotDefaultResId);// 设置引导页默认圆点
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = dotMargin;
            }
            dot.setLayoutParams(params);// 设置圆点的大小
            addView(dot);
        }
    }

    public void setSelectPosition(int selectPosition) {
        if (mCount > 0 && selectPosition > mCount - 1) {
            return;
        }
        this.mSelectedPosition = selectPosition;
        for (int i = 0; i < mCount; i++) {
            getChildAt(i).setBackgroundResource(i == selectPosition ? mDotSelectedResId : mDotDefaultResId);
        }
    }

    public void setCount(int count, int selectPosition) {
        this.mCount = count >= 0 ? count : 0;
        this.mSelectedPosition = selectPosition >= 0 ? selectPosition : 0;
        removeAllViews();
        initDots();
    }
}
