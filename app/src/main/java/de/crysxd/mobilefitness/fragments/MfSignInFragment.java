package de.crysxd.mobilefitness.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.log.RemoteLog;

/**
 * A {@link Fragment} showing a login button and handlign the login process
 */
public class MfSignInFragment extends MfFragment implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, OnCompleteListener<AuthResult> {

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
     * The {@link FirebaseAuth} to store the login in
     */
    @Inject
    FirebaseAuth mFirebaseAuth;

    /**
     * The {@link FirebaseAnalytics} instance
     */
    @Inject
    FirebaseAnalytics mAnalytics;

    /**
     * The request code for the login
     */
    private final int SIGN_IN_REQUEST_CODE = 3724;

    /**
     * The listener to be informed aboutn events
     */
    @Nullable
    private OnLoginListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SignInButton button = new SignInButton(this.getContext());
        button.setOnClickListener(this);
        button.setScopes(this.mSignInOptions.getScopeArray());
        return button;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Make sure the Context is a Activity
        if(context instanceof FragmentActivity) {
            // Inject dependencies
            MfComponentHolder.i().inject(this);

            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                    .enableAutoManage((FragmentActivity) context, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, this.mSignInOptions)
                    .build();

        } else {
            RemoteLog.log(getClass().getSimpleName(), "Attached to Context which is no FragmentActivity");

        }
    }

    /**
     * Handles the given {@link GoogleSignInResult}
     * @param result the result
     */
    private void handleSignInResult(GoogleSignInResult result) {
        RemoteLog.log(getClass().getSimpleName(), "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, forward result to firebase
            GoogleSignInAccount account = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this.getActivity(), this);
            if(mListener != null) {
                mListener.onLoginStarted();
            }
        }
    }

    /**
     * Sets the {@link OnLoginListener}
     * @param listener the new listener
     */
    public void setOnLoginListener(OnLoginListener listener) {
        this.mListener = listener;

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        RemoteLog.log(getClass().getSimpleName(), "Login failed, connectionResult:" + connectionResult.getErrorMessage());
        if(mListener != null) {
            mListener.onLoginFailed();
        }
    }

    @Override
    public void onClick(View view) {
        // Start sign in process
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        RemoteLog.log(getClass().getSimpleName(), "signInWithCredential:onComplete:" + task.isSuccessful());

        // If sign in fails, display a message to the user. If sign in succeeds
        // the auth state listener will be notified and logic to handle the
        // signed in user can be handled in the listener.
        if(mListener != null){
            if (!task.isSuccessful()) {
                RemoteLog.log(getClass().getSimpleName(), "signInWithCredential", task.getException());
                mListener.onLoginFailed();
            } else {
                mListener.onLoginCompleted();
                if(mFirebaseAuth.getCurrentUser() != null) {
                    mAnalytics.setUserId(mFirebaseAuth.getCurrentUser().getUid());
                }
            }
        }
    }

    /**
     * A interface to get informed about login events
     */
    public interface OnLoginListener {

        /**
         * Called when the login process was started
         */
        void onLoginStarted();

        /**
         * Called when the login was completed
         */
        void onLoginCompleted();

        /**
         * Called when the login failed
         */
        void onLoginFailed();

    }
}
