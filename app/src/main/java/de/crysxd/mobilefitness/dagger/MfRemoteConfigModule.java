package de.crysxd.mobilefitness.dagger;

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
public class MfRemoteConfigModule {

    @Provides
    @Singleton
    public FirebaseRemoteConfig provideFirebaseRemoteConfig() {
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();

        Map<String, Object> defaults = new HashMap<>();
        defaults.put("blue_save_button", "false");
        config.setDefaults(defaults);
        config.fetch();
        config.activateFetched();

        return config;
    }
}
