package knab.com.smaug.dagger;

import android.app.Activity;

import knab.com.smaug.SmaugApplication;
import knab.com.smaug.bluetooth.ui.StartActivity;
import knab.com.smaug.bluetooth.dagger.DaggerStartComponent;
import knab.com.smaug.bluetooth.dagger.StartModule;

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

    public SmaugComponent getSmaugComponent(Activity activity){
        return ((SmaugApplication)activity.getApplication()).getComponent();
    }
}
