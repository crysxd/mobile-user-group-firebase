package de.crysxd.mobilefitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.crysxd.mobilefitness.R;
import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.data.MfRecord;
import de.crysxd.mobilefitness.data.MfRecordsRepository;
import de.crysxd.mobilefitness.data.MfUnit;
import de.crysxd.mobilefitness.view.MfUnitEditText;

/**
 * A {@link MfActivity} enabling the user to create a record
 */
public class MfEditRecordActivity extends MfActivity {

    /**
     * The {@link Toolbar}
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * The save button
     */
    @BindView(R.id.buttonSave)
    Button mButtonSave;

    /**
     * The input layout for the exercise
     */
    @BindView(R.id.editTextExerciseLayout)
    TextInputLayout mTextInputLayoutExercise;

    /**
     * The input layout for the amount
     */
    @BindView(R.id.editTextAmountLayout)
    TextInputLayout mTextInputLayoutAmount;

    /**
     * The input layout for the unit
     */
    @BindView(R.id.editTextUnitLayout)
    TextInputLayout mTextInputLayoutUnit;

    /**
     * The input layout for the unit
     */
    @BindView(R.id.editTextUnit)
    MfUnitEditText mEditTextUnit;

    /**
     * The input layout for the amount
     */
    @BindView(R.id.editTextAmount)
    TextInputEditText mEditTextAmount;

    /**
     * The input layout for the exercise
     */
    @BindView(R.id.editTextExercise)
    TextInputEditText mEditTextExcercise;

    /**
     * The record repository
     */
    @Inject
    MfRecordsRepository mRepository;

    /**
     * Starts the {@link MfEditRecordActivity}
     *
     * @param other the {@link Activity} which wants to start this {@link MfEditRecordActivity}
     */
    public static void startActivity(Activity other) {
        Intent i = new Intent(other, MfEditRecordActivity.class);
        other.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MfComponentHolder.i().inject(this);

        setContentView(R.layout.activity_mf_edit_record);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.ui_create_record));
        mButtonSave.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);

    }

    @OnClick(R.id.buttonSave)
    public void onSave() {
        if (validateInput()) {
            MfRecord record = composeRecord();
            if (record != null) {
                mRepository.save(composeRecord());
                this.finish();
            }
        }
    }

    /**
     * Composes the record from the user input
     *
     * @return the user input
     */
    private MfRecord composeRecord() {
        try {
            return new MfRecord.Builder()
                    .setExercise(mEditTextExcercise.getText().toString())
                    .setAmount(getAmount())
                    .setUnit(mEditTextUnit.getUnit())
                    .build();
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.ui_error_unable_to_save_record)
                    .setPositiveButton(R.string.ui_ok, null)
                    .show();
            return null;
        }
    }

    /**
     * Validates the user input and shows error messages to the user
     *
     * @return true if input is valie, false if not
     */
    private boolean validateInput() {
        boolean valid = true;
        String exercise = mEditTextExcercise.getText().toString();
        String amountString = mEditTextAmount.getText().toString();
        MfUnit unit = mEditTextUnit.getUnit();

        // Reset errors, user may have fixed the inputs
        mTextInputLayoutExercise.setErrorEnabled(false);
        mTextInputLayoutAmount.setErrorEnabled(false);
        mTextInputLayoutUnit.setErrorEnabled(false);

        if (exercise.isEmpty()) {
            mTextInputLayoutExercise.setError(getString(R.string.ui_error_enter_value));
            valid = false;
        }

        if (amountString.isEmpty()) {
            mTextInputLayoutAmount.setError(getString(R.string.ui_error_enter_value));
            valid = false;
        }

        if (unit == null) {
            mTextInputLayoutUnit.setError(" ");
            valid = false;
        }

        try {
            getAmount();
        } catch (Exception e) {
            mTextInputLayoutAmount.setError(getString(R.string.ui_error_enter_value));
            valid = false;
        }

        return valid;
    }

    /**
     * Returns the amount entered by the user
     *
     * @return the amount
     * @throws ParseException
     */
    private double getAmount() throws ParseException {
        String amountString = mEditTextAmount.getText().toString();
        return DecimalFormat.getInstance().parse(amountString).doubleValue();

    }
}
