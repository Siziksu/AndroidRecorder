package com.siziksu.recorder.ui.common;

import android.app.Activity;
import android.view.View;

import com.siziksu.recorder.R;

import javax.inject.Inject;

public class ActivityUtils {

    @Inject
    public ActivityUtils() {}

    public void setInImmersiveMode(Activity activity) {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    public void setStatusBarColor(Activity activity) {
        activity.getWindow().setStatusBarColor(activity.getColor(R.color.darkerGrey));
    }
}
