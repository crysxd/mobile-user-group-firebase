package de.crysxd.mobilefitness.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.crysxd.mobilefitness.R;

/**
 * A {@link android.support.v7.widget.RecyclerView.ViewHolder} for group headers
 */
public class MfGroupHeaderViewHolder extends RecyclerView.ViewHolder {

    /**
     * The title text
     */
    @BindView(R.id.textViewTitle)
    TextView mTextViewTitle;

    /**
     * Creates a new instance
     * @param itemView the view
     */
    public MfGroupHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
