package de.crysxd.mobilefitness.data;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        mRoot = mDatabase.getReference("/users/" + mAuth.getCurrentUser().getUid());
    }

    /**
     * Connects the database to server. Data will be synced from now on
     */
    public void goOnline() {
        mDatabase.goOnline();
    }

    /**
     * Disconnects from server. Changes will be only local from now on
     */
    public void goOffline() {
        mDatabase.goOffline();
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
     * Sets the {@link ChildEventListener} notified about events
     *
     * @param listener the {@link ChildEventListener}
     */
    public void addRecordChildEventListener(ChildEventListener listener) {
        if (mRoot == null) {
            throw new IllegalStateException("Database not initialised");
        }

        getRecords().addChildEventListener(listener);
    }
}
