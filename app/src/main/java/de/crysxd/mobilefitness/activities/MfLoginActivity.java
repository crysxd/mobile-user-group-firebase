package de.crysxd.mobilefitness.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import de.crysxd.mobilefitness.R;

/**
 * The {@link MfActivity} letting the user logging himself in
 */
public class MfLoginActivity extends MfActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mf_login);

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
}
