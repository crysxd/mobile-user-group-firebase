package de.crysxd.mobilefitness.dagger;

import javax.inject.Singleton;

import dagger.Component;
import de.crysxd.mobilefitness.activities.MfLauncherActivity;
import de.crysxd.mobilefitness.activities.MfRecordsActivity;
import de.crysxd.mobilefitness.fragments.MfSignInFragment;

/**
 * The default unscoped {@link Component}
 */
@Singleton
@Component(modules = {MfAppModule.class, MfAuthModule.class})
public interface MfComponent {

    void inject(MfSignInFragment mfSignInFragment);

    void inject(MfLauncherActivity mfLauncherActivity);

    void inject(MfRecordsActivity mfRecordsActivity);

}
