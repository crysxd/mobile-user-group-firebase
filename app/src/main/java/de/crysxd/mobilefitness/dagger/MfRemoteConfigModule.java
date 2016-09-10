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
public class MfRemoteConfigModule {

    @Provides
    public FirebaseRemoteConfig provideFirebaseRemoteConfig() {
        final FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();

        Map<String, Object> defaults = new HashMap<>();
        defaults.put("blue_save_button", "false");
        config.setDefaults(defaults);
        config.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(getClass().getSimpleName(), "Remote configs fetched");
                config.activateFetched();
            }
        });

        return config;
    }
}
