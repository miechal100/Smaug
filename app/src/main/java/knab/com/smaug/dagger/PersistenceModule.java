package knab.com.smaug.dagger;

import android.bluetooth.BluetoothAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import knab.com.smaug.bluetooth.bluetooth_facade.BluetoothFacade;
import knab.com.smaug.transmition.connection_service.BluetoothConnectionService;

/**
 * Created by hp on 2017-07-27.
 */
@Module
public class PersistenceModule {

    private BluetoothAdapter bluetoothAdapter;

    @Provides
    @Singleton
    public BluetoothFacade provideBluetoothFacade(){
        return new BluetoothFacade(bluetoothAdapter.getDefaultAdapter());
    }

    @Provides
    @Singleton
    public BluetoothConnectionService providesBluetoothConnectionService() { return new BluetoothConnectionService(); }

}
