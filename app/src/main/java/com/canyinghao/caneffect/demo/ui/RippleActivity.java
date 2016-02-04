package com.canyinghao.caneffect.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.canyinghao.caneffect.CanRippleLayout;
import com.canyinghao.caneffect.demo.R;

/**
 * Created by yangjian on 16/2/2.
 */
public class RippleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        View view1 =  findViewById(R.id.view1);
        View view2 = findViewById(R.id.view2);
        View view3 = findViewById(R.id.view3);
        View view4 = findViewById(R.id.view4);
        View fl1 = findViewById(R.id.fl1);


        CanRippleLayout.Builder.on(view1).create();
        CanRippleLayout.Builder.on(view2).rippleCorner(dp2Px(5)).create();
        CanRippleLayout.Builder.on(fl1).create();


        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
            }
        });
        view1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(getApplicationContext(), "OnLongClick", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
            }
        });

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
            }
        });

        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "have clickListener", Toast.LENGTH_SHORT).show();
            }
        });



    }


    public int dp2Px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
