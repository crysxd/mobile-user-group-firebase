package de.crysxd.mobilefitness.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import de.crysxd.mobilefitness.dagger.MfComponentHolder;

/**
 * A {@link MfActivity} which routes the user to the sign in or directly to the app
 */
public class MfLauncherActivity extends MfActivity {

    /**
     * The global {@link FirebaseAuth} instance
     */
    @Inject
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MfComponentHolder.i().inject(this);

        // If we are logged in, go to records, else show login
        if(mAuth.getCurrentUser() != null) {
            MfRecordsActivity.startActivity(this);

        } else {
            MfSignInActivity.startActivity(this);

        }

        // Finish so the user gets forwarded
        this.finish();

    }
}
