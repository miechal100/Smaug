package knab.com.smaug.dagger;

import javax.inject.Singleton;

import dagger.Component;
import knab.com.smaug.SchedulerHolders;
import knab.com.smaug.SmaugApplication;
import knab.com.smaug.bluetooth.bluetooth_facade.BluetoothFacade;
import knab.com.smaug.transmition.connection_service.BluetoothConnectionService;

/**
 * Created by hp on 2017-07-27.
 */
@Singleton
@Component(modules = { AppModule.class, BluetoothModule.class })
public interface SmaugComponent {
    void inject(SmaugApplication smaugApplication);

    BluetoothFacade provideBluetoothFacade();

    BluetoothConnectionService provideBluetoothConnectionService();

    SchedulerHolders provideSchedulerHolders();

}
