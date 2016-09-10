package de.crysxd.mobilefitness.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import de.crysxd.mobilefitness.data.MfUnit;
import de.crysxd.mobilefitness.dialog.MfUnitSelectionDialogBuilder;

/**
 * A {@link android.widget.EditText} which shows a dialog when tapped and lets the user
 * select a {@link MfUnit}
 */
public class MfUnitEditText extends TextInputEditText implements View.OnClickListener,
        View.OnFocusChangeListener, MfUnitSelectionDialogBuilder.OnUnitSelectedListener {

    /**
     * The selected unit
     */
    @Nullable
    private MfUnit mUnit;

    /**
     * Creates a new instance
     *
     * @param context a {@link Context}
     */
    public MfUnitEditText(Context context) {
        super(context);
        init();
    }

    /**
     * Creates a new instance
     *
     * @param context a {@link Context}
     * @param attrs   the {@link AttributeSet}
     */
    public MfUnitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Creates a new instance
     *
     * @param context      a {@link Context}
     * @param attrs        the {@link AttributeSet}
     * @param defStyleAttr the default style
     */
    public MfUnitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Initialises this view
     */
    private void init() {
        setOnClickListener(this);
        setOnFocusChangeListener(this);
        setKeyListener(null);
    }

    /**
     * Shows the selction dialog
     */
    private void showSelectionDialog() {
        new MfUnitSelectionDialogBuilder(this.getContext()).setOnUnitSelectedListener(this).show();

    }

    /**
     * Returns the unit selected by the user or null if no unit was selected
     *
     * @return the selected {@link MfUnit}
     */
    @Nullable
    public MfUnit getUnit() {
        return mUnit;
    }

    @Override
    public void onClick(View view) {
        if (view == this) {
            showSelectionDialog();
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            onClick(view);
        }
    }

    @Override
    public void onUnitSelected(MfUnit unit) {
        mUnit = unit;
        setText(unit.getSuffix());
    }
}
