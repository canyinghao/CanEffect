package com.canyinghao.caneffect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by canyinghao on 16/2/1.
 */
public class CanShadowDrawable extends Drawable {


    private Paint paint;


    private RectF drawRect;
//  圆角半径
    private float radius;


    private float offsetLeft;
    private float offsetTop;
    private float offsetRight;
    private float offsetBottom;


    public CanShadowDrawable(int bgColor, float shadowRange, float shadowDx, float shadowDy, int shadowColor) {


        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);
//      设置阴影
        paint.setShadowLayer(shadowRange, shadowDx, shadowDy, shadowColor);

        drawRect = new RectF();
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);


        if (bounds.right - bounds.left > 0 && bounds.bottom - bounds.top > 0) {

            int width = bounds.right - bounds.left;
            int height = bounds.bottom - bounds.top;

            drawRect = new RectF(offsetLeft, offsetTop, width - offsetRight, height - offsetBottom);


            invalidateSelf();

        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(
                drawRect,
                radius, radius,
                paint
        );
    }


    public float getOffsetLeft() {
        return offsetLeft;
    }

    public void setOffsetLeft(float offsetLeft) {
        this.offsetLeft = offsetLeft;
    }

    public float getOffsetTop() {
        return offsetTop;
    }

    public void setOffsetTop(float offsetTop) {
        this.offsetTop = offsetTop;
    }

    public float getOffsetRight() {
        return offsetRight;
    }

    public void setOffsetRight(float offsetRight) {
        this.offsetRight = offsetRight;
    }

    public float getOffsetBottom() {
        return offsetBottom;
    }

    public void setOffsetBottom(float offsetBottom) {
        this.offsetBottom = offsetBottom;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public CanShadowDrawable setColor(int color) {
        paint.setColor(color);
        return this;
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }


    public static class Builder {


        private View view;
//      paddingLeft
        private float offsetLeft;
//        paddingTop
        private float offsetTop;
//        paddingRight
        private float offsetRight;
//        paddingBottom
        private float offsetBottom;
//       圆角半径
        private float radius;
//       阴影范围
        private float shadowRange;
//       阴影x轴偏移
        private float shadowDx;
//        阴影y轴偏移
        private float shadowDy;
//        阴影颜色
        private int shadowColor = Color.BLACK;
//        背景颜色
        private int bgColor = Color.WHITE;

        public Builder(View view) {
            this.view = view;
        }


        public static Builder on(View view) {
            return new Builder(view);
        }


        /**
         * PaddingLeft
         * @param offsetLeft
         * @return
         */
        public Builder offsetLeft(float offsetLeft) {
            this.offsetLeft = offsetLeft;
            return this;
        }

        /**
         * PaddingTop
         * @param offsetTop
         * @return
         */
        public Builder offsetTop(float offsetTop) {
            this.offsetTop = offsetTop;
            return this;
        }

        /**
         * PaddingRight
         * @param offsetRight
         * @return
         */
        public Builder offsetRight(float offsetRight) {
            this.offsetRight = offsetRight;
            return this;
        }

        /**
         * PaddingBottom
         * @param offsetBottom
         * @return
         */
        public Builder offsetBottom(float offsetBottom) {
            this.offsetBottom = offsetBottom;
            return this;
        }

        /**
         * 圆角半径
         * @param radius
         * @return
         */
        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        /**
         * 阴影范围
         * @param shadowRange
         * @return
         */
        public Builder shadowRange(float shadowRange) {
            this.shadowRange = shadowRange;
            return this;
        }


        /**
         * 阴影x轴偏移
         * @param shadowDx
         * @return
         */
        public Builder shadowDx(float shadowDx) {
            this.shadowDx = shadowDx;
            return this;
        }

        /**
         * 阴影y轴偏移
         * @param shadowDy
         * @return
         */
        public Builder shadowDy(float shadowDy) {
            this.shadowDy = shadowDy;
            return this;
        }

        /**
         * 阴影颜色
         * @param shadowColor
         * @return
         */
        public Builder shadowColor(int shadowColor) {
            this.shadowColor = shadowColor;
            return this;
        }

        /**
         * 背景颜色
         * @param bgColor
         * @return
         */
        public Builder bgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }


        public CanShadowDrawable create() {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            final CanShadowDrawable shadowViewDrawable = new CanShadowDrawable(bgColor, shadowRange, shadowDx, shadowDy, shadowColor);


            view.setPadding(view.getPaddingLeft() + (int) offsetLeft, view.getPaddingTop() + (int) offsetTop, view.getPaddingRight() + (int) offsetRight, view.getPaddingBottom() + (int) offsetBottom);

            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {


                    shadowViewDrawable.setOffsetLeft(offsetLeft);
                    shadowViewDrawable.setOffsetTop(offsetTop);
                    shadowViewDrawable.setOffsetBottom(offsetBottom);
                    shadowViewDrawable.setOffsetRight(offsetRight);
                    shadowViewDrawable.setRadius(radius);

                    shadowViewDrawable.setBounds(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(shadowViewDrawable);
            } else {
                view.setBackground(shadowViewDrawable);
            }


            return shadowViewDrawable;


        }
    }
}
