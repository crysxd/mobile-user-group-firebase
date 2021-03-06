package de.crysxd.mobilefitness.dagger;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for Firebase analytics
 */
@Module
class MfAnalyticsModule {

    @Provides
    @Singleton
    FirebaseAnalytics provideFirebaseAnalytics(Context con) {
        return FirebaseAnalytics.getInstance(con);
    }

}
