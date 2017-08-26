package knab.com.smaug.bluetooth.dagger;

import dagger.Module;
import dagger.Provides;
import knab.com.smaug.bluetooth.mvp.BTModel;
import knab.com.smaug.bluetooth.mvp.BTPresenter;
import knab.com.smaug.bluetooth.mvp.BluetoothMVP;
import knab.com.smaug.dagger.ActivityScope;

/**
 * Created by hp on 2017-07-27.
 */
@Module
public class StartModule {
    private BluetoothMVP.View view;

    public StartModule(BluetoothMVP.View view){
        this.view = view;
    }

    @Provides
    @ActivityScope
    public BTPresenter provideBTPresenter(BTModel model){
        return new BTPresenter(view, model);
    }
}
