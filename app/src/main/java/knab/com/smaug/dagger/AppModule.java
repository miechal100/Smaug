package knab.com.smaug.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import knab.com.smaug.SmaugApplication;
import knab.com.smaug.transmition.connection_service.BluetoothConnectionService;

/**
 * Created by hp on 2017-07-27.
 */
@Module
public class AppModule {

    private SmaugApplication application;

    public AppModule(SmaugApplication application){
        this.application = application;
    }

    @Provides
    @Singleton
    public SmaugApplication providesSmaugApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Application providesApplication(){
        return application;
    }

    @Provides
    @Singleton
    public DependencyInjector providesDependencyInjector(){
        return new DependencyInjector();
    }


}
