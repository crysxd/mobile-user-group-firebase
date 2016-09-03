package de.crysxd.mobilefitness;

import android.app.Application;

import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by chrwue on 03/09/16.
 */

public class MfApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
