package de.crysxd.mobilefitness.dagger;

import android.content.Context;

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
public class MfDataModule {

    @Provides
    @Singleton
    public FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        return database;
    }

    @Provides
    @Singleton
    public MfDatabase provideDatabase(FirebaseAuth auth, FirebaseDatabase database) {
        return new MfDatabase(auth, database);
    }

    @Provides
    @Singleton
    public MfDateConverter provideDateConverter() {
        return new MfDateConverter();
    }

    @Provides
    @Singleton
    public MfDrawableResourceConverter provideDrawableResourceConverter(Context con) {
        return new MfDrawableResourceConverter(con);
    }

    @Provides
    @Singleton
    public MfRecordsRepository provideRecordsRepository(MfDatabase database) {
        return new MfRecordsRepository(database);
    }
}
