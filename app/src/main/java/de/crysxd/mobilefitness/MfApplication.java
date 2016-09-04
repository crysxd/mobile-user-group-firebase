package de.crysxd.mobilefitness;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.log.MfFirebaseRemoteLogger;
import de.crysxd.mobilefitness.log.RemoteLog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * The custom {@link Application}
 */
public class MfApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup logging
        if(!BuildConfig.DEBUG) {
            RemoteLog.inject(new MfFirebaseRemoteLogger());
        }

        // Setup Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(this.getString(R.string.default_text_font))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        // Enable support for {@link VectorDrawableCompat} on pre-21 devices
        // Without this call the app will crash when inflating an vector drawable in an layout file!
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // Setup Dagger
        MfComponentHolder.create(this);
    }
}
