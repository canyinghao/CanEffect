package com.canyinghao.caneffect.demo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.canyinghao.caneffect.CanShadowDrawable;
import com.canyinghao.caneffect.demo.R;

/**
 * Created by yangjian on 16/2/2.
 */
public class ShadowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        View view1 = findViewById(R.id.view1);
        View view2 = findViewById(R.id.view2);

        findViewById(R.id.fl1).setVisibility(View.GONE);




        CanShadowDrawable.Builder.on(view1)
                .bgColor(getResources().getColor(R.color.colorPrimaryDark))

                .shadowColor(Color.parseColor("#000000"))

                .shadowRange(dp2Px(5))

                .offsetBottom(dp2Px(5))
                .offsetLeft(dp2Px(5))
                .offsetRight(dp2Px(5))
                .create();


        CanShadowDrawable.Builder.on(view2)
                .bgColor(getResources().getColor(R.color.colorPrimaryDark))
                .radius(dp2Px(5))
                .shadowColor(Color.parseColor("#aa000000"))

                .corners(CanShadowDrawable.CORNER_TOP_RIGHT|CanShadowDrawable.CORNER_TOP_LEFT)
                .shadowRange(dp2Px(5))
                .offsetTop(dp2Px(5))
                .offsetBottom(dp2Px(5))
                .offsetLeft(dp2Px(5))
                .offsetRight(dp2Px(5))
                .create();


    }

    public int dp2Px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
