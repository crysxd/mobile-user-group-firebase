package de.crysxd.mobilefitness.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * A module for {@link Application} and {@link Context}
 */
@Module
class MfAppModule {

    private Application mApplication;

    MfAppModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    Context provideContext() {
        return mApplication;
    }
}
