package com.windywolf.jayray.vrdaily.utils;

import android.util.TypedValue;

import com.windywolf.jayray.vrdaily.RayVrVideoApplication;

/**
 * Created by JayRay on 16/03/2017.
 * Info:
 */

public class DensityUtils {
    /**
     * 获取屏幕宽度
     * @return width
     */
    public static int getScreenWidth() {
        return RayVrVideoApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return height
     */
    public static int getScreenHeight() {
        return RayVrVideoApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                RayVrVideoApplication.getContext().getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = RayVrVideoApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                RayVrVideoApplication.getContext().getResources().getDisplayMetrics());
    }
}
