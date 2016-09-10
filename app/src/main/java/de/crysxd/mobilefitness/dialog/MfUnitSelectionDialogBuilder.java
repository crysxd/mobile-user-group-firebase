package de.crysxd.mobilefitness.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.crysxd.mobilefitness.data.MfUnit;

/**
 * A {@link AlertDialog.Builder} which builds a dialog letting the user pick a {@link MfUnit}. Use
 * {@link #setOnUnitSelectedListener(OnUnitSelectedListener)} to get notified about the selection
 */
public class MfUnitSelectionDialogBuilder extends AlertDialog.Builder implements DialogInterface.OnClickListener {

    /**
     * The listener
     */
    @Nullable
    private OnUnitSelectedListener mListener;

    /**
     * Creates a new instance
     *
     * @param context a {@link Context}
     */
    public MfUnitSelectionDialogBuilder(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * Crates a new instance
     *
     * @param context    a {@link Context}
     * @param themeResId the theme
     */
    public MfUnitSelectionDialogBuilder(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    /**
     * Initialises this instance by creatinf the selection
     */
    private void init() {
        MfUnit[] units = MfUnit.values();
        CharSequence[] unitTexts = new CharSequence[units.length - 1];

        int offset = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i] != MfUnit.UNDEFINED) {
                unitTexts[i + offset] = (units[i].getName());
            } else {
                offset = -1;
            }
        }

        setItems(unitTexts, this);
    }

    /**
     * Sets the {@link OnUnitSelectedListener} which gets notified about selections
     *
     * @param listener the {@link OnUnitSelectedListener}
     * @return this instance
     */
    public MfUnitSelectionDialogBuilder setOnUnitSelectedListener(OnUnitSelectedListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (mListener != null) {
            mListener.onUnitSelected(MfUnit.values()[i]);
        }
    }

    /**
     * A interface to get notified about selections
     */
    public interface OnUnitSelectedListener {

        /**
         * Called when the user has selected a unit
         *
         * @param unit the selected unit
         */
        void onUnitSelected(MfUnit unit);

    }
}
