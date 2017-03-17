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
    public String imgUrl;
    public String title;
    public String duration;
    public String subtitle;
    public String description;
    public String thumbnail;
    public String tag;
    public String scale;
    public int bindType;
    public String bindValue;

}
