package com.windywolf.jayray.vrdaily.widgets;

/**
 * Created by JayRay on 16/03/2017.
 * Info:
 */

public class VideoManager {

    private static class HolderClass {
        private static final VideoManager instance = new VideoManager();
    }

    private RayVrVideoView vrVideoView = null;

    public static VideoManager getInstance() {
        return HolderClass.instance;
    }

    public void release() {
        setVrVideoView(null);
    }

    public void onResume() {
        if (vrVideoView != null) {
            vrVideoView.onResume();
        }
    }

    public void onPause() {
        if (vrVideoView != null) {
            vrVideoView.onPause();
        }
    }

    public void onDestroy() {
        if (vrVideoView != null) {
            vrVideoView.onDestroy();
        }
    }

    public RayVrVideoView getVrVideoView() {
        return vrVideoView;
    }

    public void setVrVideoView(RayVrVideoView vrVideoView) {
        this.vrVideoView = vrVideoView;
    }
}
