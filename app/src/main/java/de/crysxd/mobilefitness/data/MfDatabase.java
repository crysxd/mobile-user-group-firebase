package de.crysxd.mobilefitness.data;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

/**
 * A class wrapping the {@link FirebaseDatabase}
 */
public class MfDatabase {

    /**
     * The {@link FirebaseDatabase} instance
     */
    private FirebaseDatabase mDatabase;

    /**
     * The {@link FirebaseAuth} instance
     */
    private FirebaseAuth mAuth;

    /**
     * The users root {@link DatabaseReference}
     */
    @Nullable
    private DatabaseReference mRoot;

    /**
     * The currently set {@link ValueEventListener}
     */
    @Nullable
    private ValueEventListener mListener;

    /**
     * Creates a new instance
     *
     * @param auth     the {@link FirebaseAuth} instance
     * @param database the {@link FirebaseDatabase} instance
     */
    @Inject
    public MfDatabase(FirebaseAuth auth, FirebaseDatabase database) {
        mAuth = auth;
        mDatabase = database;
    }

    /**
     * Initialises the database
     */
    public void init() {
        if (mAuth.getCurrentUser() == null) {
            throw new IllegalStateException("User is not logegd in");
        }

        mRoot = mDatabase.getReference(mAuth.getCurrentUser().getUid());
        mDatabase.goOnline();
    }

    /**
     * Destroys the database
     */
    public void destroy() {
        if (mRoot != null && mListener != null) {
            getRecords().removeEventListener(mListener);
            mDatabase.goOffline();
        }
    }

    /**
     * Returns the users root {@link DatabaseReference}
     *
     * @return the users root {@link DatabaseReference}
     */
    public DatabaseReference getUserRoot() {
        if (mRoot == null) {
            throw new IllegalStateException("Database not initialised");
        }

        return mRoot;
    }

    /**
     * Returns the users records list {@link DatabaseReference}
     *
     * @return the users records list {@link DatabaseReference}
     */
    public DatabaseReference getRecords() {
        if (mRoot == null) {
            throw new IllegalStateException("Database not initialised");
        }

        return mRoot.child("records");
    }

    /**
     * Sets the {@link ValueEventListener} notified about events
     *
     * @param listener the {@link ValueEventListener}
     */
    public void setRecordsValueEventListener(ValueEventListener listener) {
        if (mRoot == null) {
            throw new IllegalStateException("Database not initialised");
        }

        getRecords().addValueEventListener(mListener = listener);
    }
}
