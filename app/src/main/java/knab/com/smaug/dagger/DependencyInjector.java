package knab.com.smaug.dagger;

import android.app.Activity;

import knab.com.smaug.SmaugApplication;
import knab.com.smaug.bluetooth.ui.StartActivity;
import knab.com.smaug.bluetooth.dagger.DaggerStartComponent;
import knab.com.smaug.bluetooth.dagger.StartModule;
import knab.com.smaug.transmition.TransmitionActivity;
import knab.com.smaug.transmition.dagger.DaggerTransmitionComponent;
import knab.com.smaug.transmition.dagger.TransmitionModule;

/**
 * Created by hp on 2017-07-27.
 */

public class DependencyInjector {

    public void inject(StartActivity startActivity){
        DaggerStartComponent.builder()
                .smaugComponent(getSmaugComponent(startActivity))
                .startModule(new StartModule(startActivity))
                .build()
                .inject(startActivity);
    }

    public void inject(TransmitionActivity transmitionActivity){
        DaggerTransmitionComponent.builder()
                .smaugComponent(getSmaugComponent(transmitionActivity))
                .transmitionModule(new TransmitionModule(transmitionActivity))
                .build()
                .inject(transmitionActivity);
    }
    public SmaugComponent getSmaugComponent(Activity activity){
        return ((SmaugApplication)activity.getApplication()).getComponent();
    }
}
