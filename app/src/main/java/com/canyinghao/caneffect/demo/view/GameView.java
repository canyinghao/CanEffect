package com.canyinghao.caneffect.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.canyinghao.caneffect.demo.R;

public class GameView extends View implements Runnable
{
    /* 声明Bitmap对象 */
    Bitmap  mBitQQ  = null;
    int     BitQQwidth  = 0;
    int     BitQQheight = 0;

    Paint   mPaint = null;

    /* Bitmap渲染 */
    Shader mBitmapShader = null;

    /* 线性渐变渲染 */
    Shader mLinearGradient = null;

    /* 混合渲染 */
    Shader mComposeShader = null;

    /* 唤醒渐变渲染 */
    Shader mRadialGradient = null;

    /* 梯度渲染 */
    Shader mSweepGradient = null;


    ShapeDrawable mShapeDrawableQQ = null;


    public GameView(Context context)
    {
        this(context,null);


    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

         /* 装载资源 */
        mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();

        /* 得到图片的宽度和高度 */
        BitQQwidth = mBitQQ.getWidth();
        BitQQheight = mBitQQ.getHeight();

        /* 创建BitmapShader对象 */
        mBitmapShader = new BitmapShader(mBitQQ,Shader.TileMode.REPEAT,Shader.TileMode.MIRROR);

        /* 创建LinearGradient并设置渐变的颜色数组 说明一下这几天参数
         * 第一个 起始的x坐标
         * 第二个 起始的y坐标
                 * 第三个 结束的x坐标
                 * 第四个 结束的y坐标
         * 第五个 颜色数组
         * 第六个 这个也是一个数组用来指定颜色数组的相对位置 如果为null 就沿坡度线均匀分布
         * 第七个 渲染模式
         * */
        mLinearGradient = new LinearGradient(0,0,100,100,
                new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.WHITE},
                null,Shader.TileMode.REPEAT);
        /* 这里理解为混合渲染*/
        mComposeShader = new ComposeShader(mBitmapShader,mLinearGradient,PorterDuff.Mode.DARKEN);

        /* 构建RadialGradient对象，设置半径的属性 */
        //这里使用了BitmapShader和LinearGradient进行混合
        //当然也可以使用其他的组合
        //混合渲染的模式很多，可以根据自己需要来选择
        mRadialGradient = new RadialGradient(50,200,50,
                new int[]{Color.GREEN,Color.RED,Color.BLUE,Color.WHITE},
                null,Shader.TileMode.REPEAT);
        /* 构建SweepGradient对象 */
        mSweepGradient = new SweepGradient(30,30,new int[]{Color.GREEN,Color.RED,Color.BLUE,Color.WHITE},null);

        mPaint = new Paint();

        /* 开启线程 */
        new Thread(this).start();
    }

    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //将图片裁剪为椭圆形
        /* 构建ShapeDrawable对象并定义形状为椭圆 */
        mShapeDrawableQQ = new ShapeDrawable(new OvalShape());

        /* 设置要绘制的椭圆形的东西为ShapeDrawable图片 */
        mShapeDrawableQQ.getPaint().setShader(mBitmapShader);

        /* 设置显示区域 */
        mShapeDrawableQQ.setBounds(0,0, BitQQwidth, BitQQheight);

        /* 绘制ShapeDrawableQQ */
        mShapeDrawableQQ.draw(canvas);

        //绘制渐变的矩形
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(BitQQwidth, 0, 320, 156, mPaint);

        //显示混合渲染效果
        mPaint.setShader(mComposeShader);
        canvas.drawRect(0, 300, BitQQwidth, 300+BitQQheight, mPaint);

        //绘制环形渐变
        mPaint.setShader(mRadialGradient);
        canvas.drawCircle(50, 200, 50, mPaint);

        //绘制梯度渐变
        mPaint.setShader(mSweepGradient);
        canvas.drawRect(150, 160, 300, 300, mPaint);

    }

    // 触笔事件
    public boolean onTouchEvent(MotionEvent event)
    {
        return true;
    }


    // 按键按下事件
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return true;
    }


    // 按键弹起事件
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        return false;
    }


    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
    {
        return true;
    }


    /**
     * 线程处理
     */
    public void run()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            //使用postInvalidate可以直接在线程中更新界面
            postInvalidate();
        }
    }
}
