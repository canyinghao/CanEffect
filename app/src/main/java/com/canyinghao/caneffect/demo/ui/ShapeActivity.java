package com.canyinghao.caneffect.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.canyinghao.caneffect.demo.view.GameView;

/**
 * Created by yangjian on 16/2/2.
 */
public class ShapeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gv =  new GameView(this);

        setContentView(gv);
    }
}
