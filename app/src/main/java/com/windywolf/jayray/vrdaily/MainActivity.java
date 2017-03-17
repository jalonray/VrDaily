package com.windywolf.jayray.vrdaily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.windywolf.jayray.vrdaily.bean.VideoInfo;
import com.windywolf.jayray.vrdaily.widgets.RayVrVideoView;
import com.windywolf.jayray.vrdaily.widgets.VideoManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.windywolf.jayray.vrdaily.utils.DensityUtils.dp2px;

public class MainActivity extends AppCompatActivity {

    static private final String TAG = MainActivity.class.getSimpleName();

    RayVrVideoView mVrVideoView;
    VideoManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLog();
        manager = VideoManager.getInstance();
        mVrVideoView = new RayVrVideoView(this);
        mVrVideoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(250)));
        VideoInfo info = new VideoInfo();
        info.videoUrl = "http://cache.utovr.com/s1qtjpvopvfw1wpffi/L1_1280_1_15.mp4";
        mVrVideoView.setData(info);
        manager.setVrVideoView(mVrVideoView);
        setContentView(mVrVideoView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.onDestroy();
    }

    /**
     * Log current function name
     */
    private void printLog() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, Thread.currentThread().getStackTrace()[3].toString());
        }
    }
}
