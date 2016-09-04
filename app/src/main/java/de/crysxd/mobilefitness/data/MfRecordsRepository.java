package de.crysxd.mobilefitness.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * A repository storing all {@link MfRecord} instances and persists them into the database
 */
public class MfRecordsRepository implements ValueEventListener {

    /**
     * The database used
     */
    private MfDatabase mDatabase;

    /**
     * All records
     */
    private HashMap<UUID, MfRecord> mRecords = new HashMap<>();

    /**
     * Creates a new instance
     *
     * @param database the database
     */
    @Inject
    public MfRecordsRepository(MfDatabase database) {
        mDatabase = database;
    }

    /**
     * Initialises the repository. This will fail if the user is not logged in
     */
    public void init() {
        mDatabase.init();
        mDatabase.setRecordsValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mRecords.clear();
        for (DataSnapshot record : dataSnapshot.getChildren()) {
            mRecords.put(UUID.fromString(record.getKey()), dataSnapshot.getValue(MfRecord.class));
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w(getClass().getSimpleName(), databaseError.getMessage());
    }

    /**
     * Returns all entities
     *
     * @return all entities
     */
    public List<MfRecord> getAll() {
        return new ArrayList<>(mRecords.values());
    }

    /**
     * Saves the given record
     *
     * @param record the record
     */
    public void save(MfRecord record) {
        getRecordRef(record.getId()).setValue(record);
        this.mRecords.put(record.getId(), record);
    }

    /**
     * Deletes the given record
     *
     * @param record the record
     */
    public void delete(MfRecord record) {
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
}
