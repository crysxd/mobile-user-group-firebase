package de.crysxd.mobilefitness.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import de.crysxd.mobilefitness.fragments.MfFragment;
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
