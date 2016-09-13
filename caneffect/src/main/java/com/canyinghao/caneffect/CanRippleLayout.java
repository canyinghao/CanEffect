package com.canyinghao.caneffect;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.ObjectAnimator;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by canyinghao on 16/2/3.
 */
public class CanRippleLayout extends FrameLayout {


    private float radius;
    private Paint paint;

    private float centerX;
    private float centerY;

    private ObjectAnimator ripple;

    private boolean isAnime;

    private View childView;

    private float radiusMax;


    private float rippleCorner;
    private int rippleColor = Color.parseColor("#77000000");
    private int rippleAlpha = 20;
    private float rippleSpeed = 0.5f;


    public CanRippleLayout(Context context) {
        this(context, null);
    }

    public CanRippleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanRippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CanRippleLayout, defStyleAttr, 0);

        try {
            final int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.CanRippleLayout_can_ripple_corner) {

                    setRippleCorner(a.getDimension(attr, 0));

                } else if (attr == R.styleable.CanRippleLayout_can_ripple_alpha) {
                    setRippleAlpha(a.getInt(attr, 20));

                } else if (attr == R.styleable.CanRippleLayout_can_ripple_speed) {
                    setRippleSpeed(a.getFloat(attr, 0.5f));

                } else if (attr == R.styleable.CanRippleLayout_can_ripple_color) {
                    setRippleColor(a.getColor(attr, Color.parseColor("#77000000")));

                }
            }


        } finally {
            a.recycle();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(rippleColor);
        paint.setAlpha(rippleAlpha);


        setWillNotDraw(false);

    }

    public void setRadius(float radius) {
        this.radius = radius;


        invalidate();
    }


    public void setRippleSpeed(@FloatRange(from = 0.0, to = 1.0) float rippleSpeed) {
        this.rippleSpeed = rippleSpeed;
    }

    public void setRippleAlpha(int rippleAlpha) {
        this.rippleAlpha = rippleAlpha;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }

    public void setRippleCorner(float rippleCorner) {
        this.rippleCorner = rippleCorner;
    }

    public void startAnimator(float x, float y) {


        float maxX = Math.max(x, getWidth() - x);
        float maxY = Math.max(y, getHeight() - y);

        radiusMax = (float) Math.sqrt(Math.pow(maxX, 2) + Math.pow(maxY, 2));


        ripple = ObjectAnimator.ofFloat(this, "radius", 0, radiusMax);
        ripple.setDuration((long) (radiusMax + radiusMax * 10 * (1 - rippleSpeed)));


        ripple.setInterpolator(new OvershootInterpolator());
        ripple.start();


    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (isAnime){
            stopAnime();
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {


        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!isChildViewClick(this, (int) e.getX(), (int) e.getY())) {
            return super.onTouchEvent(e);
        }

        if (childView!=this){
            childView.onTouchEvent(e);
        }


        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:

                centerY = e.getY();
                centerX = e.getX();
                isAnime = true;
                startAnimator(e.getX(), e.getY());
                return true;

            case MotionEvent.ACTION_MOVE:

                return super.onTouchEvent(e);
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                stopAnime();

                break;


        }


        return super.onTouchEvent(e);
    }

    private void stopAnime() {
        if (ripple != null && ripple.isRunning()) {
            postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (ripple.isRunning()) {
                        ripple.cancel();

                    }
                    isAnime = false;

                    setRadius(0);
                }
            }, 500);
        } else {
            isAnime = false;
            setRadius(0);
        }
    }


    private boolean isChildViewClick(View view, int x, int y) {


        if (view.isEnabled() && (view.isClickable() || view.isLongClickable())){

            childView = view;
            return true;
        }

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;

            if (view instanceof FrameLayout || view instanceof RelativeLayout) {


                for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
                    View child = viewGroup.getChildAt(i);
                    final Rect rect = new Rect();
                    child.getHitRect(rect);

                    final boolean contains = rect.contains(x, y);
                    if (contains) {
                        return isChildViewClick(child, x - rect.left, y - rect.top);
                    }
                }
            } else {

                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    final Rect rect = new Rect();
                    child.getHitRect(rect);

                    final boolean contains = rect.contains(x, y);
                    if (contains) {
                        return isChildViewClick(child, x - rect.left, y - rect.top);
                    }
                }
            }


        }

        childView = view;


        return view.isEnabled() && (view.isClickable() || view.isLongClickable());
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        if (isAnime) {

            Path clipPath = new Path();
            RectF rect = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            clipPath.addRoundRect(rect, rippleCorner, rippleCorner, Path.Direction.CW);
            canvas.clipPath(clipPath);
            canvas.drawCircle(centerX, centerY, radiusMax, paint);
            canvas.drawCircle(centerX, centerY, radius, paint);
        }


    }




    public static class Builder {


        private View view;
        private float rippleCorner;
        private int rippleColor = Color.parseColor("#77000000");
        private int rippleAlpha = 20;
        private float rippleSpeed = 0.5f;

        public Builder(View view) {
            this.view = view;
        }


        public static Builder on(View view) {
            return new Builder(view);
        }


        public Builder rippleCorner(float rippleCorner) {
            this.rippleCorner = rippleCorner;
            return this;
        }

        public Builder rippleColor(int rippleColor) {
            this.rippleColor = rippleColor;
            return this;
        }
        public Builder rippleAlpha(int rippleAlpha) {
            this.rippleAlpha = rippleAlpha;
            return this;
        }
        public Builder rippleSpeed(float rippleSpeed) {
            this.rippleSpeed = rippleSpeed;
            return this;
        }




        public CanRippleLayout create() {

            if (view.getParent() != null && view.getParent() instanceof CanRippleLayout) {
                return (CanRippleLayout) view.getParent();
            }

            CanRippleLayout layout = new CanRippleLayout(view.getContext());

            layout.setRippleColor(rippleColor);
            layout.setRippleSpeed(rippleSpeed);
            layout.setRippleAlpha(rippleAlpha);
            layout.setRippleCorner(rippleCorner);



            ViewGroup.LayoutParams params = view.getLayoutParams();
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = 0;


            if (parent != null) {
                index = parent.indexOfChild(view);
                parent.removeView(view);
            }

            layout.addView(view, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

            if (parent != null) {
                parent.addView(layout, index, params);
            }


            return layout;

        }


    }
}
