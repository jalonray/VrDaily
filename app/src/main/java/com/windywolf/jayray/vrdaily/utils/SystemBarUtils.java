package com.windywolf.jayray.vrdaily.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * Created by JayRay on 16/03/2017.
 * Info:
 */

public class SystemBarUtils {

    static private final String TAG = SystemBarUtils.class.getSimpleName();

    private static int preUiOption;

    public static boolean hideStatusBarAndNavigationBar(@Nullable final Activity activity, final boolean hideNavigationBar) {
        if (activity == null) {
            return false;
        }

        boolean result = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            preUiOption = activity.getWindow().getDecorView().getSystemUiVisibility();
            Log.d(TAG, "setUiOptions: preUiOption" + preUiOption);
            setUiOptions(activity, hideNavigationBar);
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void setUiOptions(@Nullable Activity activity, boolean hideNavigationBar) {
        int uiOptions = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            uiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiOptions ^= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            uiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            if (hideNavigationBar) {
                uiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
        }
        Log.d(TAG, "setUiOptions: uiOptions" + uiOptions);
        activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    public static boolean showStatusBar(@Nullable Activity activity) {
        if (activity == null) {
            return false;
        }

        boolean result = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.d(TAG, "setUiOptions: preUiOption" + preUiOption);
            activity.getWindow().getDecorView().setSystemUiVisibility(preUiOption);
            activity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(null);
        }

        return result;
    }
}
