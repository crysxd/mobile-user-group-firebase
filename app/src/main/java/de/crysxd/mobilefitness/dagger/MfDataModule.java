package de.crysxd.mobilefitness.dagger;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.crysxd.mobilefitness.data.MfDatabase;
import de.crysxd.mobilefitness.data.MfDateConverter;
import de.crysxd.mobilefitness.data.MfDrawableResourceConverter;
import de.crysxd.mobilefitness.data.MfRecordsRepository;

/**
 * A module for database
 */
@Module
class MfDataModule {

    @Provides
    @Singleton
    FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        return database;
    }

    @Provides
    @Singleton
    MfDatabase provideDatabase(FirebaseAuth auth, FirebaseDatabase database) {
        return new MfDatabase(auth, database);
    }

    @Provides
    @Singleton
    MfDateConverter provideDateConverter() {
        return new MfDateConverter();
    }

    @Provides
    @Singleton
    MfDrawableResourceConverter provideDrawableResourceConverter(Context con) {
        return new MfDrawableResourceConverter(con);
    }

    @Provides
    @Singleton
    MfRecordsRepository provideRecordsRepository(MfDatabase database, FirebaseAnalytics analytics) {
        return new MfRecordsRepository(database, analytics);
    }
}
