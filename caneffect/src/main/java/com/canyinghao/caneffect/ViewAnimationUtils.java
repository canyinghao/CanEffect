package com.canyinghao.caneffect;


import android.animation.Animator;
import android.view.View;


/**
 * Created by canyinghao on 16/2/3.
 */
public final class ViewAnimationUtils {


    public static Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {


        return CanCircularRevealLayout.Builder.on(view)
                .centerX(centerX)
                .centerY(centerY)
                .startRadius(startRadius)
                .endRadius(endRadius)
                .create();


    }


//    public static android.animation.Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            return android.view.ViewAnimationUtils
//                    .createCircularReveal(view, centerX, centerY, startRadius, endRadius);
//        }
//
//
//        return null;
//
//    }


}
