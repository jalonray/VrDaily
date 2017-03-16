package com.windywolf.jayray.vrdaily.bean;

import static com.windywolf.jayray.vrdaily.bean.VideoInfo.VR_TYPE.MONO;

/**
 * Created by JayRay on 15/03/2017.
 * Info:
 */

public class VideoInfo {

    public @interface VR_TYPE {
        int MONO = 1;
        int STEREO = 2;
    }

    public String videoUrl;
    @VR_TYPE
    public int vrType = MONO;
}
