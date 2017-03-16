package com.windywolf.jayray.vrdaily.widgets;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import com.windywolf.jayray.vrdaily.DataBinding;
import com.windywolf.jayray.vrdaily.R;
import com.windywolf.jayray.vrdaily.bean.VideoInfo;
import com.windywolf.jayray.vrdaily.utils.AnimationUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.vr.sdk.widgets.common.VrWidgetView.DisplayMode.EMBEDDED;
import static com.google.vr.sdk.widgets.common.VrWidgetView.DisplayMode.FULLSCREEN_MONO;
import static com.windywolf.jayray.vrdaily.widgets.RayVrVideoView.VideoStatus.COMPLETE;
import static com.windywolf.jayray.vrdaily.widgets.RayVrVideoView.VideoStatus.PAUSE;
import static com.windywolf.jayray.vrdaily.widgets.RayVrVideoView.VideoStatus.PLAY;
import static com.windywolf.jayray.vrdaily.widgets.RayVrVideoView.VrHandler.FADE_OUT;

/**
 * Created by JayRay on 15/03/2017.
 * Info: Vr player wraps Google Vr Video View
 */

public class RayVrVideoView extends FrameLayout implements DataBinding<VideoInfo>,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    static private final String TAG = RayVrVideoView.class.getSimpleName();

    /**
     * Video Status
     */
    public @interface VideoStatus {
        int IDLE = 0;
        int LOAD = 1;
        int PLAY = 2;
        int PAUSE = 3;
        int COMPLETE = 4;
        int ERROR = 5;
    }

    /**
     * Fade animation time
     */
    protected static final int animateTime = 300;
    /**
     * Controller show time
     */
    protected static final long defaultTimeout = 3000;

    @BindView(R.id.vrVideoView)
    VrVideoView mVideoView;
    @BindView(R.id.vrController)
    LinearLayout mController;
    @BindView(R.id.playPauseBtn)
    ImageButton mPlayPause;
    @BindView(R.id.btnReplay)
    ImageButton mReplay;
    @BindView(R.id.time_current)
    TextView mCurrentTime;
//    @BindView(R.id.seek_bar)
//    SeekBar mPlayProgress;
    @BindView(R.id.time_total)
    TextView mTotalTime;
    @BindView(R.id.fullscreen_button)
    ImageButton mFullscreen;
    @BindView(R.id.volumeControl)
    ImageButton mVolumeControl;
    protected boolean isControllerShowed = true;
    protected boolean isMute = false;
    protected YdVrVideoEventListener ydVrVideoEventListener = null;
    @VideoStatus
    protected int mVideoStatus;
    protected VrHandler mHandler;
    protected boolean isFullscreen = false;

    public RayVrVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public RayVrVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RayVrVideoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        printLog();
        inflate(getContext(), R.layout.vr_player_layout, this);
        ButterKnife.bind(this);
        bindView();
        initPlayer();
        mHandler = new VrHandler(this);
    }

    private void bindView() {
        printLog();
        mPlayPause.setOnClickListener(this);
        mReplay.setOnClickListener(this);
        mFullscreen.setOnClickListener(this);
        mVolumeControl.setOnClickListener(this);
//        mPlayProgress.setOnSeekBarChangeListener(this);
    }

    private void initPlayer() {
        printLog();
        ydVrVideoEventListener = new YdVrVideoEventListener();
        mVideoView.setEventListener(ydVrVideoEventListener);
        mVideoStatus = VideoStatus.IDLE;
        mVideoView.setInfoButtonEnabled(false);
        mVideoView.setTransitionViewEnabled(false);
        mVideoView.setFullscreenButtonEnabled(false);
        mVideoView.setTouchTrackingEnabled(true);
        mVideoView.setStereoModeButtonEnabled(false);
    }

    @Override
    public void setData(VideoInfo info) {
        printLog();
        try {
            VrVideoView.Options options = new VrVideoView.Options();
            options.inputType = info.vrType;
            mVideoView.loadVideo(Uri.parse(info.videoUrl), options);
            mVideoStatus = VideoStatus.LOAD;
        } catch (IOException e) {
            mVideoStatus = VideoStatus.ERROR;
            Log.e(TAG, "Could not load video: " + e);
        }

    }

    public void onPause() {
        printLog();
        mVideoView.pauseRendering();
        if (mVideoStatus == PLAY) {
            mVideoStatus = PAUSE;
        }
    }

    public void onResume() {
        printLog();
        mVideoView.resumeRendering();
        if (mVideoStatus == PAUSE) {
            mVideoStatus = PLAY;
        }
    }

    public void onDestroy() {
        printLog();
        mVideoView.shutdown();
        mVideoStatus = VideoStatus.IDLE;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playPauseBtn:
                playPause();
                break;
            case R.id.btnReplay:
                replay();
                break;
            case R.id.maxBtn:
                toggleFullscreen();
                break;
            case R.id.volumeControl:
                toggleVolume();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mVideoView.seekTo(progress);
            updateStatusText();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void replay() {
        if (isVideoLoaded()) {
            mVideoView.seekTo(0);
            mVideoView.playVideo();
            updateStatusText();
            mVideoStatus = PLAY;
        }
    }

    public void playPause() {
        if (mVideoStatus == PLAY) {
            mVideoStatus = PAUSE;
            mVideoView.pauseVideo();
        } else if (mVideoStatus == PAUSE) {
            mVideoStatus = PAUSE;
            mVideoView.playVideo();
        }
    }

    protected boolean isVideoLoaded() {
        return mVideoStatus == PLAY || mVideoStatus == PAUSE
                || mVideoStatus == COMPLETE;
    }

    protected void toggleFullscreen() {
        printLog();
        isFullscreen = !isFullscreen;
        mVideoView.setDisplayMode(isFullscreen ? FULLSCREEN_MONO : EMBEDDED);
    }

    protected void toggleController() {
        printLog();
        if (isControllerShowed) {
            doFadeOut(mController);
        } else {
            doFadeIn(mController);
        }
    }

    protected void toggleVolume() {
        printLog();
        isMute = !isMute;
        mVolumeControl.setImageResource(isMute ? R.mipmap.volume_off : R.mipmap.volume_on);
        mVideoView.setVolume(isMute ? 0f : 1f);
    }

    protected void updateStatusText() {
        printLog();
        mCurrentTime.setText(getReadTime(mVideoView.getCurrentPosition()));
    }

    protected void doFadeOut(View view) {
        printLog();
        if (view == mController && !isControllerShowed) {
            return;
        }
        AnimationUtil.doFadeInvisibleAnimation(view, animateTime, null);
    }

    protected void doFadeIn(View view) {
        printLog();
        if (view == mController && isControllerShowed) {
            return;
        }
        AnimationUtil.doFadeInAnimation(view, animateTime, null);
    }

    protected void showController(long timeout) {
        printLog();
        mHandler.removeMessages(FADE_OUT);
        mController.setVisibility(VISIBLE);
        if (timeout != 0) {
            mHandler.sendEmptyMessage(FADE_OUT);
        }
    }

    private void printLog() {
        Log.d(TAG, Thread.currentThread().getStackTrace()[3].toString());
    }

    private String getReadTime(long time) {
        return null;
    }

    static class VrHandler extends Handler {

        public static final int FADE_OUT = 0;

        private WeakReference<RayVrVideoView> videoViewWeakReference;

        public VrHandler(RayVrVideoView vrVideoView) {
            videoViewWeakReference = new WeakReference<>(vrVideoView);
        }

        @Override
        public void handleMessage(Message msg) {
            RayVrVideoView videoView = videoViewWeakReference.get();
            if (videoView == null) {
                return;
            }
            switch (msg.what) {
                case FADE_OUT:
                    videoView.doFadeOut(videoView.mController);
                    break;
                default:
                    break;
            }
        }
    }

    private class YdVrVideoEventListener extends VrVideoEventListener {
        @Override
        public void onLoadSuccess() {
            Log.i(TAG, "Successfully loaded video " + mVideoView.getDuration());
            mVideoStatus = PLAY;
//            mPlayProgress.setMax((int) mVideoView.getDuration());
            mTotalTime.setText(getReadTime(mVideoView.getDuration()));
            showController(defaultTimeout);
        }

        /**
         * Called by video widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            // An error here is normally due to being unable to decode the video format.
            mVideoStatus = VideoStatus.ERROR;
            Log.e(TAG, "Error loading video: " + errorMessage);
        }

        @Override
        public void onClick() {
            toggleController();
        }

        /**
         * Update the UI every frame.
         */
        @Override
        public void onNewFrame() {
            updateStatusText();
//            mPlayProgress.setProgress((int) mVideoView.getCurrentPosition());
        }

        /**
         * Pause and set status to complete.
         */
        @Override
        public void onCompletion() {
            mVideoView.pauseVideo();
            mVideoStatus = COMPLETE;
        }
    }
}
