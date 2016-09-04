package de.crysxd.mobilefitness.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.crysxd.mobilefitness.R;

/**
 * A {@link android.support.v7.widget.RecyclerView.ViewHolder} for {@link de.crysxd.mobilefitness.data.MfRecord}
 */
public class MfRecordViewHolder extends RecyclerView.ViewHolder {

    /**
     * The title
     */
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    /**
     * The detail
     */
    @BindView(R.id.textViewDetail)
    TextView textViewDetail;

    /**
     * The icon
     */
    @BindView(R.id.imageViewIcon)
    ImageView imageViewIcon;

    /**
     * Creates a new instance
     *
     * @param itemView the view
     */
    public MfRecordViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
