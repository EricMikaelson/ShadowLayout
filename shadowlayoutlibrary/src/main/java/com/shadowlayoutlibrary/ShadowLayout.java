package com.shadowlayoutlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by guojunjie on 2018/7/6.
 * 阴影控件
 */

public class ShadowLayout extends LinearLayout {


    /***
     * 阴影的影响范围 方向
     */
    private int mShadowGravigy = ShadowGravity.ALL;

    /**
     * 控件背景颜色
     */
    private int mBackagetColorValue = Color.WHITE;

    /**
     * 阴影颜色
     */
    private int mShadowColorValue = Color.GRAY;

    /**
     * 阴影大小 - 设置的值应该为原值的一半， 因为会预留位置时，可能 *2 了
     */
    private float mElevationValue = 10;

    /**
     * 阴影四个角的 圆角半径
     */
    private float mCornerRadiusValue = 20;

    /**
     * 阴影的Y轴偏移量
     */
    private float mOffsetY = 0;

    /**
     * 阴影的形状
     */
    private int mShadowShape = ShadowShape.RECTANGLE;

    private Drawable mDrawable;

    public ShadowLayout(Context context) {
        super(context);
        initAttrs(null);
        initBackground();
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initBackground();
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initBackground();
    }

    private void initAttrs(@Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Shadow);
        if (typedArray != null) {
            mBackagetColorValue = typedArray.getColor(R.styleable.Shadow_shadow_bg_color,
                    Color.TRANSPARENT);
            mShadowColorValue = typedArray.getColor(R.styleable.Shadow_shadow_color,
                    Color.GRAY);
            mCornerRadiusValue = typedArray.getDimension(R.styleable.Shadow_shadow_radius, 20);
            mElevationValue = typedArray.getDimension(R.styleable.Shadow_shadow_range, 10);
            mShadowGravigy = typedArray.getInt(R.styleable.Shadow_shadow_gravity, ShadowGravity.ALL);
            mOffsetY = typedArray.getDimension(R.styleable.Shadow_shadow_offset_y, 0);
            mShadowShape = typedArray.getInt(R.styleable.Shadow_shadow_shape, ShadowShape.RECTANGLE);
            typedArray.recycle();
        }
    }

    private void initBackground() {
        if (mDrawable == null) {
            mDrawable = ShadowUtils.generateBackgroundWithShadow(
                    this,
                    mBackagetColorValue,
                    mShadowShape,
                    mCornerRadiusValue,
                    mShadowColorValue,
                    (int) mElevationValue,
                    mShadowGravigy,
                    (int) mOffsetY
            );
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(mDrawable);
        } else {
            setBackgroundDrawable(mDrawable);
        }
    }
}
