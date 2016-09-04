package de.crysxd.mobilefitness.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import de.crysxd.mobilefitness.fragments.MfFragment;
import de.crysxd.mobilefitness.log.RemoteLog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * A god class for all activities in this project
 */
public class MfActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RemoteLog.log(getClass().getSimpleName(), "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        RemoteLog.log(getClass().getSimpleName(), "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RemoteLog.log(getClass().getSimpleName(), "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        RemoteLog.log(getClass().getSimpleName(), "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        RemoteLog.log(getClass().getSimpleName(), "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        RemoteLog.log(getClass().getSimpleName(), "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RemoteLog.log(getClass().getSimpleName(), "onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Forward result to all of our own fragments, not to thrid party ones
        // (this may break some things in third party code)
        for(Fragment frag : this.getSupportFragmentManager().getFragments()) {
            if(frag instanceof MfFragment) {
                frag.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
