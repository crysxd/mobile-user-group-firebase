package de.crysxd.mobilefitness.adapter;

import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.List;

import javax.inject.Inject;

import de.crysxd.mobilefitness.R;
import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.data.MfRecord;
import de.crysxd.mobilefitness.data.MfRecordsRepository;

/**
 * A adapter listing all {@link MfRecord} instances from {@link MfRecordsRepository}
 */
public class MfRecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * The repository
     */
    @Inject
    MfRecordsRepository mRepository;

    /**
     * A list with all records
     */
    private List<MfRecord> mRecords;

    /**
     * A observer calling reload when the data set was changed
     */
    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            reload();

        }
    };

    /**
     * Creates a new instance
     */
    public MfRecordsAdapter() {
        MfComponentHolder.i().inject(this);
        mRepository.getObservable().registerObserver(this.mObserver);
        reload();
    }

    /**
     * Releases all resources
     */
    public void destroy() {
        mRepository.getObservable().unregisterObserver(this.mObserver);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MfRecordViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MfRecord item = mRecords.get(position);
        MfRecordViewHolder recordHolder = (MfRecordViewHolder) holder;

        // Create detail text with amount, unit and date
        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(item.getTime());
        String detail = recordHolder.textViewDetail.getContext()
                .getString(R.string.ui_amount_on_date, item.getAmount(), item.getUnit().getSuffix(), date);

        recordHolder.imageViewIcon.setImageResource(item.getIconResource());
        recordHolder.textViewDetail.setText(detail);
        recordHolder.textViewTitle.setText(item.getExercise());
    }

    @Override
    public long getItemId(int i) {
        return mRecords.get(i).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    /**
     * Reloads the entire data set
     */
    private void reload() {
        mRecords = mRepository.getAll();
        this.notifyDataSetChanged();
    }
}
