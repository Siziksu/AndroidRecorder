package com.siziksu.recorder.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.siziksu.recorder.App;
import com.siziksu.recorder.R;
import com.siziksu.recorder.ui.common.manager.PermissionsManager;
import com.siziksu.recorder.ui.common.manager.Recorder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.info)
    TextView info;

    @Inject
    Recorder recorder;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.get().getApplicationComponent().inject(this);
        PermissionsManager.checkPermissions(this);
        initialize();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!PermissionsManager.allGood(requestCode, grantResults)) { finish(); }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.onDestroy();
    }

    @OnClick({R.id.start, R.id.stop})
    public void onClick(View view) {
        recorder.onClick(view);
    }

    private void initialize() {
        recorder.setListener(resource -> info.setText(getString(resource)));
    }
}
