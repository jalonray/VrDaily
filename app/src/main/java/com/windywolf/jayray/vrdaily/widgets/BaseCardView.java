package com.windywolf.jayray.vrdaily.widgets;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.windywolf.jayray.vrdaily.DataBinding;

/**
 * Created by JayRay on 16/03/2017.
 * Info:
 */

public abstract class BaseCardView<T> extends FrameLayout implements View.OnClickListener,
        DataBinding<T> {


    public BaseCardView(@NonNull Context context) {
        super(context);
        init(null, 0);
    }

    public BaseCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BaseCardView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    abstract protected void init(@Nullable AttributeSet attrs, @AttrRes int defStyleAttr);
}
