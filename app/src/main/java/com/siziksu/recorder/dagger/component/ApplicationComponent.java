package com.siziksu.recorder.dagger.component;

import com.siziksu.recorder.App;
import com.siziksu.recorder.dagger.module.ApplicationModule;
import com.siziksu.recorder.ui.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class
        }
)
public interface ApplicationComponent {

    void inject(App target);

    void inject(MainActivity target);
}
