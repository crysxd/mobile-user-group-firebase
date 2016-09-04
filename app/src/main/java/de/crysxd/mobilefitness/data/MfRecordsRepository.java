package de.crysxd.mobilefitness.data;

import android.database.DataSetObservable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * A repository storing all {@link MfRecord} instances and persists them into the database
 */
public class MfRecordsRepository implements ChildEventListener {

    /**
     * The database used
     */
    private MfDatabase mDatabase;

    /**
     * All records
     */
    private HashMap<UUID, MfRecord> mRecords = new HashMap<>();

    /**
     * The listener
     */
    @Nullable
    private OnRepositoryChangedListener mListener;

    /**
     * Creates a new instance
     *
     * @param database the database
     */
    public MfRecordsRepository(MfDatabase database) {
        mDatabase = database;
    }

    /**
     * Initialises the repository. This will fail if the user is not logged in
     */
    public synchronized void init() {
        mDatabase.init();
        mDatabase.addRecordChildEventListener(this);
    }

    /**
     * Sets the {@link OnRepositoryChangedListener} notfied about changes
     * @param listener the {@link OnRepositoryChangedListener}
     */
    public synchronized void setOnRepositoryChangedListener(OnRepositoryChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        MfRecord r = updateRecord(dataSnapshot);
        if(mListener != null) {
            mListener.onItemAdded(r);
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        MfRecord r = updateRecord(dataSnapshot);
        if(mListener != null) {
            mListener.onItemUpdated(r);
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        MfRecord r = updateRecord(dataSnapshot);
        if(mListener != null) {
            mListener.onItemRemoved(r);
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        MfRecord r = updateRecord(dataSnapshot);
        if(mListener != null) {
            mListener.onItemMoved(r);
        }
    }

    /**
     * Updates the given record
     * @param record the record
     */
    private MfRecord updateRecord(DataSnapshot record) {
        MfRecord recordInstance = record.getValue(MfRecord.class);
        mRecords.put(UUID.fromString(record.getKey()), recordInstance);
        return recordInstance;
    }

    @Override
    public synchronized void onCancelled(DatabaseError databaseError) {
        Log.w(getClass().getSimpleName(), databaseError.getMessage());
    }

    /**
     * Returns all entities
     *
     * @return all entities
     */
    public synchronized List<MfRecord> getAll() {
        return new ArrayList<>(mRecords.values());
    }

    /**
     * Saves the given record
     *
     * @param record the record
     */
    public synchronized void save(MfRecord record) {
        getRecordRef(record.getId()).setValue(record);
        this.mRecords.put(record.getId(), record);
    }

    /**
     * Deletes the given record
     *
     * @param record the record
     */
    public synchronized void delete(MfRecord record) {
        getRecordRef(record.getId()).removeValue();
    }

    /**
     * Returns the {@link DatabaseReference} for the given record
     *
     * @param id the id of the record
     * @return the {@link DatabaseReference}
     */
    private DatabaseReference getRecordRef(UUID id) {
        return mDatabase.getRecords().child(id.toString());
    }

    /**
     * A interface to get informed about changes
     */
    public interface OnRepositoryChangedListener {

        /**
         * Calles when a new item was added
         * @param item the item
         */
        void onItemAdded(MfRecord item);

        /**
         * Called when a item was updated
         * @param item the item
         */
        void onItemUpdated(MfRecord item);

        /**
         * Called when a item was removed
         * @param item the item
         */
        void onItemRemoved(MfRecord item);

        /**
         * Called when a item was moved
         * @param item the item
         */
        void onItemMoved(MfRecord item);

    }
}
