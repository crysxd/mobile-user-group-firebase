package de.crysxd.mobilefitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.data.MfRecordsRepository;
import de.crysxd.mobilefitness.log.RemoteLog;

/**
 * A {@link MfActivity} which routes the user to the sign in or directly to the app
 */
public class MfLauncherActivity extends MfActivity {

    /**
     * The global {@link FirebaseAuth} instance
     */
    @Inject
    FirebaseAuth mAuth;

    /**
     * The global {@link MfRecordsRepository} instance
     */
    @Inject
    MfRecordsRepository mRepository;

    /**
     * Starts the {@link MfLauncherActivity}
     * @param other the activity which wants to start {@link MfLauncherActivity}
     */
    public static void startActivity(Activity other) {
        Intent i = new Intent(other, MfLauncherActivity.class);
        other.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MfComponentHolder.i().inject(this);

        // If we are logged in, go to records, else show login
        if(mAuth.getCurrentUser() != null) {
            mRepository.init();
            RemoteLog.log(getClass().getSimpleName(), "User already signed in, going to MfRecordsActivity");
            MfRecordsActivity.startActivity(this);

        } else {
            RemoteLog.log(getClass().getSimpleName(), "User not signed in, going to MfSignInActivity");
            MfSignInActivity.startActivity(this);

        }

        // Finish so the user gets forwarded
        this.finish();

    }
}
