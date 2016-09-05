package de.crysxd.mobilefitness.dagger;

import javax.inject.Singleton;

import dagger.Component;
import de.crysxd.mobilefitness.activities.MfEditRecordActivity;
import de.crysxd.mobilefitness.activities.MfLauncherActivity;
import de.crysxd.mobilefitness.activities.MfRecordsActivity;
import de.crysxd.mobilefitness.adapter.MfRecordsAdapter;
import de.crysxd.mobilefitness.data.MfDatabase;
import de.crysxd.mobilefitness.data.MfRecord;
import de.crysxd.mobilefitness.fragments.MfSignInFragment;

/**
 * The default unscoped {@link Component}
 */
@Singleton
@Component(modules = {MfAppModule.class, MfAuthModule.class, MfDataModule.class})
public interface MfComponent {

    void inject(MfSignInFragment mfSignInFragment);

    void inject(MfLauncherActivity mfLauncherActivity);

    void inject(MfRecordsActivity mfRecordsActivity);

    void inject(MfDatabase mfDatabase);

    void inject(MfRecord mfRecord);

    void inject(MfRecordsAdapter mfRecordsAdapter);

    void inject(MfEditRecordActivity mfEditRecordActivity);

}
