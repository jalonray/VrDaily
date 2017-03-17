package com.windywolf.jayray.vrdaily.api;

import com.windywolf.jayray.vrdaily.bean.VideoInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by JayRay on 15/03/2017.
 * Info: Retrofit http definition
 * use of retrofit: http://square.github.io/retrofit
 */

public interface VideoService {

    static final String BASE_VR_URL = "http://api.utovr.com/";
    @GET("v4/home")
    Flowable<VideoInfo> getInfo();

}
