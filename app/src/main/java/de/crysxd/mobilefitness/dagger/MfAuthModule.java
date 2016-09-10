package de.crysxd.mobilefitness.dagger;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.crysxd.mobilefitness.R;

/**
 * A module for Firebase auth
 */
@Module
class MfAuthModule {

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    GoogleSignInOptions provideGoogleSignInOptions(Context con) {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(con.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }
}
