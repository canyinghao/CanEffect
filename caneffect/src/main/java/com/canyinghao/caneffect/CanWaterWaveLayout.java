package com.canyinghao.caneffect;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by canyinghao on 16/1/25.
 */
public class CanWaterWaveLayout extends FrameLayout {

    private Paint paint;

    private float centerDownX;
    private float centerDownY;


    private float centerUpX;
    private float centerUpY;

    private int alphaDown;
    private int alphaUp;


    private float radiusDown;

    private float radiusUp;

    private float maxDown;
    private float maxUp;

    private boolean isAnimeDowning;
    private boolean isAnimeUping;


    private long startTime;


    private float waterSpeed = 0.5f;
    private int waterRadius = 100;
    private int waterThickness = 50;

    private int colorStart = Color.TRANSPARENT;
    private int colorCenter = Color.WHITE;
    private int colorEnd = Color.TRANSPARENT;


    private int[] colors;

    private View childView;


    public CanWaterWaveLayout(Context context) {
        this(context, null);
    }

    public CanWaterWaveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanWaterWaveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CanWaterWaveLayout, defStyleAttr, 0);

        try {
            final int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.CanWaterWaveLayout_can_water_radius) {

                    setWaterRadius((int) a.getDimension(attr, 100));

                } else if (attr == R.styleable.CanWaterWaveLayout_can_water_thickness) {
                    setWaterThickness((int) a.getDimension(attr, 50));

                } else if (attr == R.styleable.CanWaterWaveLayout_can_water_speed) {
                    setWaterSpeed(a.getFloat(attr, 0.5f));

                } else if (attr == R.styleable.CanWaterWaveLayout_can_color_start) {
                    colorStart = a.getColor(attr, Color.TRANSPARENT);

                } else if (attr == R.styleable.CanWaterWaveLayout_can_color_center) {
                    colorCenter = a.getColor(attr, Color.WHITE);

                } else if (attr == R.styleable.CanWaterWaveLayout_can_color_end) {
                    colorEnd = a.getColor(attr, Color.TRANSPARENT);

                }
            }


        } finally {
            a.recycle();
        }


        colors = new int[]{colorStart, colorCenter, colorEnd};
        paint = new Paint();
        paint.setAntiAlias(true); //消除锯齿
        paint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形
        paint.setColor(Color.WHITE);

        setWillNotDraw(false);
    }

    public void setWaterThickness(int waterThickness) {
        this.waterThickness = waterThickness;
    }

    public void setWaterRadius(int waterRadius) {
        this.waterRadius = waterRadius;
    }

    public void setWaterSpeed( float waterSpeed) {
        this.waterSpeed = waterSpeed;
    }


    public void setColors(int... colors) {
        if (colors != null) {
            this.colors = colors;
        }

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() == 0) {
            throw new IllegalStateException("ChildCount = 0 ");
        }

        childView = getChildAt(0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        centerDownX = getWidth() / 2;
        centerDownY = getHeight() / 2;

    }


    public float startAnimator(float centerX, float centerY, String radiusStr) {


        float maxX = Math.max(centerX, getWidth() - centerX);
        float maxY = Math.max(centerY, getHeight() - centerY);

        float max = Math.max(maxX, maxY);

        ObjectAnimator ripple = ObjectAnimator.ofFloat(this, radiusStr, 0, max);
        ripple.setDuration((long) (max + max * 10 * (1 - waterSpeed)));


        ripple.setInterpolator(new DecelerateInterpolator());
        ripple.start();

        return max;

    }


    public void setRadiusDown(float radiusDown) {
        this.radiusDown = radiusDown;

        alphaDown = (int) ((1 - (radiusDown / maxDown)) * 255);

        invalidate();
    }

    public void setRadiusUp(float radiusUp) {
        this.radiusUp = radiusUp;

        alphaUp = (int) ((1 - (radiusUp / maxUp)) * 255);

        invalidate();
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

        if (childView != this) {
            childView.onTouchEvent(e);
        }


        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:

                startTime = System.currentTimeMillis();
                centerDownY = e.getY();
                centerDownX = e.getX();

                isAnimeDowning = true;
                maxDown = startAnimator(centerDownX, centerDownY, "radiusDown");
                return true;

            case MotionEvent.ACTION_MOVE:

                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:


                long endTime = System.currentTimeMillis();

                long time = endTime - startTime;


                if (time > 500) {

                    centerUpY = e.getY();
                    centerUpX = e.getX();

                    isAnimeUping = true;
                    maxUp = startAnimator(centerUpX, centerUpY, "radiusUp");


                }


                break;


        }


        return super.onTouchEvent(e);
    }


    private boolean isChildViewClick(View view, int x, int y) {

        if (view.isEnabled() && (view.isClickable() || view.isLongClickable())) {

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

        if (isAnimeDowning) {

            drawWater(canvas, centerDownX, centerDownY, radiusDown, alphaDown);
            if (alphaDown <= 5) {

                isAnimeDowning = false;
            }
        }

        if (isAnimeUping) {

            drawWater(canvas, centerUpX, centerUpY, radiusUp, alphaUp);

            if (alphaUp <= 5) {

                isAnimeUping = false;
            }
        }


    }


    private void drawWater(Canvas canvas, float centerX, float centerY, float radius, int alpha) {


        Shader mShader = new RadialGradient(centerX, centerY, waterRadius + radius + waterThickness / 2,
                colors,
                null, Shader.TileMode.REPEAT);

        paint.setAlpha(alpha);
        paint.setShader(mShader);

        paint.setStrokeWidth(waterThickness + radius / 5);
        canvas.drawCircle(centerX, centerY, waterRadius + radius, paint);
    }


    public static class Builder {


        private View view;
        private float waterSpeed = 0.5f;
        private int waterRadius = 100;
        private int waterThickness = 50;
        private int[] colors;

        public Builder(View view) {
            this.view = view;
        }


        public static Builder on(View view) {
            return new Builder(view);
        }


        public Builder waterSpeed(float waterSpeed) {
            this.waterSpeed = waterSpeed;
            return this;
        }

        public Builder waterRadius(int waterRadius) {
            this.waterRadius = waterRadius;
            return this;
        }

        public Builder waterThickness(int waterThickness) {
            this.waterThickness = waterThickness;
            return this;
        }

        public Builder colors(int... colors) {
            this.colors = colors;
            return this;
        }


        public CanWaterWaveLayout create() {

            if (view.getParent() != null && view.getParent() instanceof CanWaterWaveLayout) {
                return (CanWaterWaveLayout) view.getParent();
            }

            CanWaterWaveLayout layout = new CanWaterWaveLayout(view.getContext());

            layout.setColors(colors);
            layout.setWaterSpeed(waterSpeed);
            layout.setWaterThickness(waterThickness);
            layout.setWaterRadius(waterRadius);


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
