package com.shadowlayoutlibrary;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

import static android.view.View.LAYER_TYPE_SOFTWARE;

/**
 * Created by guojunjie on 2018/7/6.
 */

public class ShadowUtils {

    /**
     *
     * @param view
     * @param backgroundColor 背景颜色，必须指定控件的背景颜色
     * @param shadowShape 阴影的形状 ， rectangle: 方形， oval: 圆形
     * @param shadowCornerRadius 阴影 圆角半径
     * @param shadowColor 阴影颜色
     * @param shadowRange 阴影影响范围
     * @param shadowGravity 阴影 出现的方形
     * @param offsetY 阴影在Y轴的偏移量
     * @return
     */
    public static Drawable generateBackgroundWithShadow(View view,
                                                        int backgroundColor,
                                                        int shadowShape,
                                                        float shadowCornerRadius,
                                                        int shadowColor,
                                                        int shadowRange,
                                                        int shadowGravity,
                                                        int offsetY) {

        float[] outerRadius = {shadowCornerRadius, shadowCornerRadius, shadowCornerRadius,
                shadowCornerRadius, shadowCornerRadius, shadowCornerRadius, shadowCornerRadius,
                shadowCornerRadius};

        Rect shapeDrawablePadding = new Rect();

        int mInsertLeft = shadowRange;
        int mInsertRight = shadowRange;
        int mInsertTop = shadowRange;
        int mInsertBottom = shadowRange;

        int DY = offsetY;
        switch (shadowGravity) {
            case ShadowGravity.ALL:
                shapeDrawablePadding.left = shadowRange;
                shapeDrawablePadding.right = shadowRange ;
                shapeDrawablePadding.top = shadowRange;
                shapeDrawablePadding.bottom = shadowRange;
                // DY = offsetY;
                break;
            case ShadowGravity.TOP:
                shapeDrawablePadding.left = 0;
                shapeDrawablePadding.right = 0;
                shapeDrawablePadding.top = shadowRange;
                shapeDrawablePadding.bottom = 0;
                // DY =  offsetY;

                mInsertTop = shadowRange  + Math.abs(offsetY);
                mInsertBottom = 0;
                break;
            case ShadowGravity.LEFT:
                shapeDrawablePadding.left = shadowRange;
                shapeDrawablePadding.right = 0;
                shapeDrawablePadding.top = 0;
                shapeDrawablePadding.bottom = 0;
                // DY =  offsetY;

                mInsertRight = 0;
                break;
            case ShadowGravity.RIGHT:
                shapeDrawablePadding.left = 0;
                shapeDrawablePadding.right = shadowRange;
                shapeDrawablePadding.top = 0;
                shapeDrawablePadding.bottom = 0;
                // DY =  offsetY;

                mInsertLeft = 0;
                // DY = offsetY;
                break;

            case ShadowGravity.EXCEPT_TOP:
                shapeDrawablePadding.left = shadowRange ;
                shapeDrawablePadding.right = shadowRange;
                shapeDrawablePadding.top = 0;
                shapeDrawablePadding.bottom = shadowRange;
                // DY =offsetY;
                mInsertBottom = shadowRange + Math.abs(offsetY);
                break;

            case ShadowGravity.EXCEPT_BOTTOM:
                shapeDrawablePadding.left = shadowRange ;
                shapeDrawablePadding.right = shadowRange;
                shapeDrawablePadding.top = shadowRange;
                shapeDrawablePadding.bottom = 0;
                // DY =offsetY;
                mInsertBottom = shadowRange + Math.abs(offsetY);

                break;

            case ShadowGravity.EXCEPT_LEFT:
                shapeDrawablePadding.left = 0;
                shapeDrawablePadding.right = shadowRange;
                shapeDrawablePadding.top = shadowRange;
                shapeDrawablePadding.bottom = shadowRange;
                // DY =  offsetY;
                break;

            case ShadowGravity.EXCEPT_RIGHT:
                shapeDrawablePadding.left = shadowRange;
                shapeDrawablePadding.right = 0;
                shapeDrawablePadding.top = shadowRange;
                shapeDrawablePadding.bottom = shadowRange;
                break;

            default:
            case ShadowGravity.BOTTOM:
                shapeDrawablePadding.left = 0 ;
                shapeDrawablePadding.right = 0;
                shapeDrawablePadding.top = 0;
                shapeDrawablePadding.bottom = shadowRange;
                // DY = offsetY;

                mInsertTop = 0 ;
                mInsertBottom = shadowRange + Math.abs(offsetY);
                break;
        }

        ShapeDrawable shapeDrawable = new ShapeDrawable();
        // (1)当在最上层使用padding时，它指明的是最上层的drawable边缘与内容之间的padding;
        // (2)当在非最上层使用padding时，它指明当前层与上层之间的padding。
        shapeDrawable.setPadding(shapeDrawablePadding);

        shapeDrawable.getPaint().setColor(backgroundColor);

        //参数表示：柔边（阴影模糊范围）、X轴偏移量、Y轴偏移量、阴影颜色
        shapeDrawable.getPaint().setShadowLayer(shadowRange , 0, DY, shadowColor);

        //必须使用软件加速，不能用硬件加速
        view.setLayerType(LAYER_TYPE_SOFTWARE, shapeDrawable.getPaint());

        //设置shape 圆角
        if (shadowShape == ShadowShape.OVAL){
            Shape shape = new OvalShape();
            shapeDrawable.setShape(shape);
        } else{
            shapeDrawable.setShape(new RoundRectShape(outerRadius, null, null));
        }

        LayerDrawable drawable = new LayerDrawable(new Drawable[]{shapeDrawable});

        //setLayerInset()函数的作用就是将某层（层数从0开始计数）相对于上一层进行向里偏移。当然如果传入的数值为负数，就是向外偏移了，不过这时上层就遮挡住下层了，失去了使用layer的意义了
        drawable.setLayerInset(0, mInsertLeft, mInsertTop, mInsertRight, mInsertBottom);

        return drawable;

    }
}