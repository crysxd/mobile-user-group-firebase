package de.crysxd.mobilefitness.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * A module for {@link Application} and {@link Context}
 */
@Module
public class MfAppModule {

    private Application mApplication;

    public MfAppModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    public Context provideContext() {
        return mApplication;
    }
}
