package de.crysxd.mobilefitness;

import android.app.Application;

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

        // Setup Dagger
        MfComponentHolder.create(this);
    }
}
