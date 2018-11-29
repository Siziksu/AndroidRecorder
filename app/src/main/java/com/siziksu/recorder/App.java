package com.siziksu.recorder;

import android.app.Application;

import com.siziksu.recorder.dagger.component.ApplicationComponent;
import com.siziksu.recorder.dagger.component.DaggerApplicationComponent;
import com.siziksu.recorder.dagger.module.ApplicationModule;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    private static App app;

    public static App get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initDagger();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }
}
