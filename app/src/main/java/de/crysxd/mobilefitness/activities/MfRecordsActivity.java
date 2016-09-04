package de.crysxd.mobilefitness.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import dagger.multibindings.IntKey;
import de.crysxd.mobilefitness.R;
import de.crysxd.mobilefitness.adapter.MfRecordsAdapter;
import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.data.MfRecord;
import de.crysxd.mobilefitness.data.MfRecordsRepository;
import de.crysxd.mobilefitness.data.MfUnit;

public class MfRecordsActivity extends MfActivity {

    /**
     * The {@link FirebaseAuth}
     */
    @Inject
    FirebaseAuth mAuth;

    /**
     * The {@link MfRecordsRepository}
     */
    @Inject
    MfRecordsRepository mRecords;

    /**
     * The {@link Toolbar}
     */
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * The {@link RecyclerView}
     */
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    /**
     * The {@link RecyclerView}'s adapter
     */
    private MfRecordsAdapter mAdapter;

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
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle(R.string.ui_your_records);

        mRecyclerView.setAdapter(mAdapter = new MfRecordsAdapter());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }

    @OnLongClick(R.id.toolbar)
    public boolean onDemoCrash() {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ui_crash, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        throw new RuntimeException("Hello, Firebase!");

                    }
                })
                .setMessage(R.string.ui_should_app_crash)
                .show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mf_records, menu);
        return true;
    }

    @OnClick(R.id.fab)
    public void onAdd() {
        mRecords.save(new MfRecord.Builder().setExercise("Max Backsquad").setUnit(MfUnit.KG).setIcon(R.drawable.ic_exercise_default).setAmount(80).build());
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
