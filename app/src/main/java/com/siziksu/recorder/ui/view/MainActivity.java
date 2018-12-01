package com.siziksu.recorder.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.siziksu.recorder.App;
import com.siziksu.recorder.R;
import com.siziksu.recorder.ui.common.ActivityUtils;
import com.siziksu.recorder.ui.common.manager.PermissionsManager;
import com.siziksu.recorder.ui.common.manager.Recorder;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Inject
    Recorder recorder;
    @Inject
    ActivityUtils activityUtils;
    @Inject
    PermissionsManager permissionsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.get().getApplicationComponent().inject(this);
        activityUtils.setStatusBarColor(this);
        permissionsManager.checkPermissions(this);
        initialize();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!permissionsManager.areAllPermissionsGranted(requestCode, grantResults)) { finish(); }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityUtils.setInImmersiveMode(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.onDestroy();
    }

    @OnClick({R.id.start, R.id.stop})
    public void onClick(View view) {
        recorder.onClick(view.getId());
    }

    private void initialize() {
        recorder.setListener(() -> {
            recorder.onClick(R.id.stop);
            recorder.onClick(R.id.start);
        });
    }
}
