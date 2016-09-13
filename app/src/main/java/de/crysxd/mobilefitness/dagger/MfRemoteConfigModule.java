package de.crysxd.mobilefitness.dagger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for remote config
 */
@Module
class MfRemoteConfigModule {

    @Provides
    @Singleton
    FirebaseRemoteConfig provideFirebaseRemoteConfig() {
        final FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();

        // TODO

        return config;
    }
}
