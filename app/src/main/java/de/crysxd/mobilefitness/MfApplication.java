package de.crysxd.mobilefitness;

import android.app.Application;

import de.crysxd.mobilefitness.dagger.MfComponentHolder;

/**
 * Created by chrwue on 03/09/16.
 */

public class MfApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup Dagger
        MfComponentHolder.create(this);

    }
}
