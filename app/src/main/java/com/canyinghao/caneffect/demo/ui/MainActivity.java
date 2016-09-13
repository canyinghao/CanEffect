package com.canyinghao.caneffect.demo.ui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.canyinghao.caneffect.CanRippleLayout;
import com.canyinghao.caneffect.CanShadowDrawable;
import com.canyinghao.caneffect.CanWaterWaveLayout;
import com.canyinghao.caneffect.ViewAnimationUtils;
import com.canyinghao.caneffect.demo.R;
import com.nineoldandroids.animation.Animator;


/**
 * Created by yangjian on 16/1/25.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       View view1 =  findViewById(R.id.view1);
       View fl1 =  findViewById(R.id.fl1);
        View view2 =  findViewById(R.id.view2);
       View fl2 =  findViewById(R.id.fl2);


        final View view3 =  findViewById(R.id.view3);
        final View view4 =  findViewById(R.id.view4);







        CanShadowDrawable.Builder.on(fl1)

                .radius(dp2Px(5))
                .shadowColor(Color.parseColor("#333333"))

                .shadowRange(dp2Px(5))
                .offsetTop(dp2Px(5))
                .offsetBottom(dp2Px(5))
                .offsetLeft(dp2Px(5))
                .offsetRight(dp2Px(5))
                .create();

        CanRippleLayout.Builder.on(view1).rippleCorner(dp2Px(5)).create();

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
            }
        });


        CanShadowDrawable.Builder.on(fl2)

                .radius(dp2Px(10))
                .shadowColor(Color.parseColor("#333333"))

                .shadowRange(dp2Px(5))
//                .offsetTop(dp2Px(5))
                .offsetBottom(dp2Px(5))
                .offsetLeft(dp2Px(5))
                .offsetRight(dp2Px(5))
                .create();

        CanWaterWaveLayout.Builder.on(view2).create();

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
            }
        });


        view3.setVisibility(View.GONE);
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view4.setVisibility(View.GONE);
                view3.setVisibility(View.VISIBLE);



                Animator c1a = ViewAnimationUtils
                        .createCircularReveal(view3, 0, 0, (int) dp2Px(100), 1000);



                c1a.setDuration(1000);
                c1a.setInterpolator(new AccelerateDecelerateInterpolator());
                c1a.start();

            }
        });

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Animator c1a = ViewAnimationUtils
                        .createCircularReveal(view3, 0, 0, 1000, (int) dp2Px(100));



                c1a.setDuration(1000);
                c1a.setInterpolator(new AccelerateDecelerateInterpolator());
                c1a.start();

                c1a.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        view3.setVisibility(View.GONE);
                        view4.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });

    }


     float dp2Px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {

            case R.id.action_1:


                startActivity(new Intent(MainActivity.this,ShadowActivity.class));

                break;
            case R.id.action_2:
                startActivity(new Intent(MainActivity.this,WaterActivity.class));


                break;
            case R.id.action_3:
                startActivity(new Intent(MainActivity.this,RippleActivity.class));
                break;
            case R.id.action_4:
                startActivity(new Intent(MainActivity.this,ShapeActivity.class));
                break;

            case R.id.action_5:
                startActivity(new Intent(MainActivity.this,XmlActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
