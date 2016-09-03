package de.crysxd.mobilefitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import de.crysxd.mobilefitness.R;
import de.crysxd.mobilefitness.fragments.MfSignInFragment;

/**
 * The {@link MfActivity} letting the user logging himself in
 */
public class MfSignInActivity extends MfActivity implements MfSignInFragment.OnLoginListener {

    /**
     * Starts the {@link MfSignInActivity}
     * @param other the {@link Activity} which wants to start this {@link MfSignInActivity}
     */
    public static void startActivity(Activity other) {
        Intent i = new Intent(other, MfSignInActivity.class);
        other.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create view
        setContentView(R.layout.activity_mf_sign_in);
        ButterKnife.bind(this);

        // Let this activity be informed about login events
        MfSignInFragment signInFragment = (MfSignInFragment)
                this.getSupportFragmentManager().findFragmentById(R.id.sign_in_button);
        signInFragment.setOnLoginListener(this);

        // Make View go below status bar
        prepareStatusbar();

    }

    /**
     * Prepares the status bar so the view goes below it
     */
    private void prepareStatusbar() {
        // On M, make fully translucent and use dark icons
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }

        // On KitKat and above, use translucent black bar with light icons
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }

        // Default: Use solid status bar

    }

    @Override
    public void onLoginCompleted() {
        // Forward user
        MfRecordsActivity.startActivity(this);
        this.finish();
    }

    @Override
    public void onLoginFailed() {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ui_ok, null)
                .setMessage(R.string.ui_sign_in_failed_try_again)
                .show();
    }
}
