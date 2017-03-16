package com.windywolf.jayray.vrdaily.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.windywolf.jayray.vrdaily.R;
import com.windywolf.jayray.vrdaily.RayVrVideoApplication;

/**
 * Created by JayRay on 15/03/2017.
 * Info:
 */

public class AnimationUtil {
    /**
     * view的淡入出现
     *
     * @param view
     * @param duration 默认值为500ms,传入-1,使用默认值
     */
    public static void doFadeInAnimation(View view, int duration, Animation.AnimationListener animationListener) {
        doInAnimation(view, duration, R.anim.fade_in, animationListener);
    }

    public static void doFadeInvisibleAnimation(View view, int duration, Animation.AnimationListener animationListener) {
        doInvisibleAnimation(view, duration, R.anim.fade_out, animationListener);
    }

    public static void doInAnimation(View view, int duration, int anim, Animation.AnimationListener animationListener) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), anim);
        if (duration >= 0) {
            animation.setDuration(duration);
        }
        if (animationListener != null) {
            animation.setAnimationListener(animationListener);
        }
        view.startAnimation(animation);
    }

    public static void doInvisibleAnimation(View view, int duration, int anim, Animation.AnimationListener animationListener) {
        if (view.getVisibility() == View.INVISIBLE) {
            return;
        }
        view.setVisibility(View.INVISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), anim);
        if (duration >= 0) {
            animation.setDuration(duration);
        }
        if (animationListener != null) {
            animation.setAnimationListener(animationListener);
        }
        view.startAnimation(animation);
    }

    public static Context getContext() {
        return RayVrVideoApplication.getContext();
    }
}
