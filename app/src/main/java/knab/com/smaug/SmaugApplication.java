package knab.com.smaug;

import android.app.Application;

import javax.inject.Inject;

import knab.com.smaug.dagger.AppModule;
import knab.com.smaug.dagger.BluetoothModule;
import knab.com.smaug.dagger.DaggerSmaugComponent;
import knab.com.smaug.dagger.DependencyInjector;
import knab.com.smaug.dagger.SmaugComponent;

/**
 * Created by hp on 2017-07-27.
 */

public class SmaugApplication extends Application{

    @Inject
    DependencyInjector injector;

    private SmaugComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerSmaugComponent.builder()
                .appModule(new AppModule(this))
                .bluetoothModule(new BluetoothModule())
                .build();
        component.inject(this);
    }

    public SmaugComponent getComponent(){
        return component;
    }

    public DependencyInjector getInjector(){
        return injector;
    }
}
