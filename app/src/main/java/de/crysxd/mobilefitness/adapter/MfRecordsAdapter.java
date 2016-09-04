package de.crysxd.mobilefitness.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import de.crysxd.mobilefitness.R;
import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.data.MfDateRange;
import de.crysxd.mobilefitness.data.MfRecord;
import de.crysxd.mobilefitness.data.MfRecordTimeComparator;
import de.crysxd.mobilefitness.data.MfRecordsFilter;
import de.crysxd.mobilefitness.data.MfRecordsRepository;

/**
 * A adapter listing all {@link MfRecord} instances from {@link MfRecordsRepository}
 */
public class MfRecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MfRecordsRepository.OnRepositoryChangedListener{

    /**
     * The repository
     */
    @Inject
    MfRecordsRepository mRepository;

    /**
     * A list with all records and group headers
     */
    private List<Object> mItems = new ArrayList<>();

    /**
     * The type for headers
     */
    private final int TYPE_HEADER = 0;

    /**
     * The type for records
     */
    private final int TYPE_RECORD = 1;

    /**
     * The filter
     */
    @Nullable
    private MfRecordsFilter mFilter = null;

    /**
     * Creates a new instance
     */
    public MfRecordsAdapter() {
        MfComponentHolder.i().inject(this);
        mRepository.setOnRepositoryChangedListener(this);
        setHasStableIds(true);
        reload();
    }

    /**
     * Releases all resources
     */
    public void destroy() {
        mRepository.setOnRepositoryChangedListener(null);
    }

    /**
     * Sets the filter
     * @param filter the filter
     */
    public void setFilter(MfRecordsFilter filter) {
        mFilter = filter;
        reload();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position) instanceof MfRecord ? TYPE_RECORD : TYPE_HEADER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_RECORD) {
            return new MfRecordViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_record, parent, false));
        } else {
            return new MfGroupHeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_group_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mItems.get(position) instanceof MfRecord) {
            MfRecord item = (MfRecord) mItems.get(position);
            MfRecordViewHolder recordHolder = (MfRecordViewHolder) holder;

            // Create detail text with amount, unit and date
            String date = DateFormat.getDateInstance(DateFormat.SHORT).format(item.getTime());
            String detail = recordHolder.textViewDetail.getContext()
                    .getString(R.string.ui_amount_on_date, item.getAmount(), item.getUnit().getSuffix(), date);

            recordHolder.imageViewIcon.setImageResource(item.getIconResource());
            recordHolder.textViewDetail.setText(detail);
            recordHolder.textViewTitle.setText(item.getExercise());
        } else {
            MfDateRange item = (MfDateRange) mItems.get(position);
            MfGroupHeaderViewHolder groupHeaderHolder = (MfGroupHeaderViewHolder) holder;
            groupHeaderHolder.mTextViewTitle.setText(item.getDescription());
        }
    }

    @Override
    public long getItemId(int i) {
        Object item = mItems.get(i);
        if(item instanceof MfRecord) {
            return ((MfRecord) item).getId().toString().hashCode();
        } else {
            return ((MfDateRange) item).getDescription().hashCode();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Sorts {@link #mItems}
     */
    private void reload() {
        int currentRange = 0;
        MfDateRange[] ranges = new MfDateRange[]{
                MfDateRange.getInstance(MfDateRange.TODAY),
                MfDateRange.getInstance(MfDateRange.THIS_WEEK),
                MfDateRange.getInstance(MfDateRange.THIS_MONTH),
                MfDateRange.getInstance(MfDateRange.BEFORE)};

        List<MfRecord> records = mRepository.getAll();
        if(mFilter != null) {
            mFilter.filter(records);
        }
        Collections.sort(records, new MfRecordTimeComparator());
        List<Object> items = new ArrayList<>();

        for(MfRecord record : records) {
            while(!ranges[currentRange].isInRange(record.getTime()) && currentRange < ranges.length - 1) {
                currentRange++;
            }

            if(!items.contains(ranges[currentRange])) {
                items.add(ranges[currentRange]);
            }

            items.add(record);
        }

        mItems = items;

    }

    @Override
    public void onItemAdded(MfRecord item) {
        reload();
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemUpdated(MfRecord item) {
        reload();
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(MfRecord item) {
        reload();
        this.notifyDataSetChanged();
    }

    @Override
    public void onItemMoved(MfRecord item) {
        onItemUpdated(item);
    }
}
