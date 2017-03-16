package com.windywolf.jayray.vrdaily.widgets;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.windywolf.jayray.vrdaily.Navigator;
import com.windywolf.jayray.vrdaily.R;
import com.windywolf.jayray.vrdaily.bean.VideoInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JayRay on 16/03/2017.
 * Info:
 */

public class VrCardView extends BaseCardView<VideoInfo> {

    @BindView(R.id.videoImage)
    ImageView mVideoImage;
    @BindView(R.id.videoTitle)
    TextView mVideoTitle;
    @BindView(R.id.videoDuration)
    TextView mVideoDuration;

    public VrCardView(@NonNull Context context) {
        super(context);
    }

    public VrCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VrCardView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        inflate(getContext(), R.layout.vr_card_layout, this);
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Navigator.toDetail(getContext());
    }

    @Override
    public void setData(VideoInfo data) {
        mVideoTitle.setText(data.title);
        mVideoDuration.setText(data.duration);
        Glide.with(getContext()).load(data.imgUrl).into(mVideoImage);
    }
}
