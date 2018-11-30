package com.siziksu.recorder.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.siziksu.recorder.App;
import com.siziksu.recorder.R;
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
    PermissionsManager permissionsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getColor(R.color.darkerGrey));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.get().getApplicationComponent().inject(this);
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
        if (!permissionsManager.allGood(requestCode, grantResults)) { finish(); }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInImmersiveMode();
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

    private void setInImmersiveMode() {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    private void initialize() {
        recorder.setListener(() -> {
            recorder.onClick(R.id.stop);
            recorder.onClick(R.id.start);
        });
    }
}
