package de.crysxd.mobilefitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import de.crysxd.mobilefitness.R;
import de.crysxd.mobilefitness.dagger.MfComponentHolder;

public class MfRecordsActivity extends MfActivity {

    @Inject
    FirebaseAuth mAuth;

    /**
     * Starts the {@link MfRecordsActivity}
     * @param other the {@link Activity} which wants to start this {@link MfRecordsActivity}
     */
    public static void startActivity(Activity other) {
        Intent i = new Intent(other, MfRecordsActivity.class);
        other.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MfComponentHolder.i().inject(this);

        setContentView(R.layout.activity_mf_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mf_records, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Sign user out
        if (id == R.id.action_sign_out) {
            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sign the user out and returns to login screen
     */
    private void signOut() {
        mAuth.signOut();
        MfSignInActivity.startActivity(this);
        this.finish();
    }
}
