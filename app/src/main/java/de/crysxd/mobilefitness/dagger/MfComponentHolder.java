package de.crysxd.mobilefitness.dagger;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

/**
 * A class holding a static reference to the used {@link MfComponent}. Use {@link #inject(MfComponent)}
 * to inject a mock component while testing
 */
public class MfComponentHolder {

    /**
     * The {@link MfComponent} used
     */
    @Nullable
    private static MfComponent sComponent;

    /**
     * Creates the {@link MfComponent} used
     * @param app the {@link Application} instance
     */
    public static void create(Application app) {
        if(sComponent != null) {
            sComponent = DaggerMfComponent.builder()
                    .mfAppModule(new MfAppModule(app))
                    .build();
        }
    }

    /**
     * Can be used to inject a special {@link MfComponent} instance
     * @param component the {@link MfComponent} used from now on for Dagger
     */
    @VisibleForTesting
    public static void inject(MfComponent component) {
        sComponent = component;
    }

    /**
     * Returns the {@link MfComponent} instance
     * @return the {@link MfComponent} instance
     */
    @NonNull
    public static MfComponent i() {
        if(sComponent == null) {
            throw new IllegalStateException("Dagger is not initialised yet. Call MfComponentHolder#create() first");
        }

        return sComponent;
    }
}
