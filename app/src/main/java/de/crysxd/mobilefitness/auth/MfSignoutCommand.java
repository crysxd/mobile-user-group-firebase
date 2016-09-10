package de.crysxd.mobilefitness.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import de.crysxd.mobilefitness.dagger.MfComponentHolder;

/**
 * A command class to sign the user out
 */
public class MfSignOutCommand implements Runnable, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    /**
     * The {@link GoogleApiClient} used for sign in
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * The {@link GoogleSignInOptions} to be used
     */
    @Inject
    GoogleSignInOptions mSignInOptions;

    /**
     * The {@link FirebaseAuth}
     */
    @Inject
    FirebaseAuth mAuth;

    /**
     * The sign out listener
     */
    private OnSingOutListener mListener;

    /**
     * Creates a new instance
     *
     * @param a        a {@link FragmentActivity}
     * @param listener the {@link OnSingOutListener}
     */
    public MfSignOutCommand(@NonNull FragmentActivity a, @NonNull OnSingOutListener listener) {
        MfComponentHolder.i().inject(this);
        this.mListener = listener;

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(a)
                .enableAutoManage(a, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, this.mSignInOptions)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    public void run() {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mListener.onSignOutFailed();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        mAuth.signOut();
                        mListener.onSignOutCompleted();

                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Nothing to do
    }

    /**
     * A interface to get feedback
     */
    public interface OnSingOutListener {

        /**
         * Called after the user was signed out
         */
        void onSignOutCompleted();

        /**
         * Called when sign out failed
         */
        void onSignOutFailed();

    }
}
