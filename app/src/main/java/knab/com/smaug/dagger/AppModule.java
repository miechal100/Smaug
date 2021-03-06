package knab.com.smaug.dagger;

import android.app.Application;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import knab.com.smaug.SchedulerHolders;
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

    @Provides
    @Singleton
    public SchedulerHolders providesSchedulerHolders() {
        return new SchedulerHolders(AndroidSchedulers.mainThread(), Schedulers.from(Executors.newFixedThreadPool(4)));
    }
}
