package de.crysxd.mobilefitness.data;

import android.database.DataSetObservable;
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
    private DataSetObservable mObservable = new DataSetObservable();

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
     * Returns the observable for this repsoitory
     * @return the observable
     */
    public synchronized DataSetObservable getObservable() {
        return mObservable;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        updateRecord(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        updateRecord(dataSnapshot);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        updateRecord(dataSnapshot);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        updateRecord(dataSnapshot);
    }

    /**
     * Updates the given record
     * @param record the record
     */
    private void updateRecord(DataSnapshot record) {
        MfRecord recordInstance = record.getValue(MfRecord.class);
        mRecords.put(UUID.fromString(record.getKey()), recordInstance);
        mObservable.notifyChanged();
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
        mObservable.notifyChanged();
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
}
