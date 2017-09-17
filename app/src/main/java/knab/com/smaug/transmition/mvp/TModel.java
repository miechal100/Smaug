package knab.com.smaug.transmition.mvp;

import javax.inject.Inject;

import knab.com.smaug.bluetooth.bluetooth_facade.BluetoothFacade;
import knab.com.smaug.dagger.ActivityScope;
import knab.com.smaug.transmition.connection_service.BluetoothConnectionService;

/**
 * Created by hp on 2017-09-08.
 */
@ActivityScope
public class TModel implements TransmitionMVP.Model {

    private BluetoothFacade bluetoothFacade;
    private BluetoothConnectionService bluetoothConnectionService;

    @Inject
    public TModel(BluetoothFacade bluetoothFacade, BluetoothConnectionService bluetoothConnectionService){
        this.bluetoothConnectionService = bluetoothConnectionService;
        this.bluetoothFacade = bluetoothFacade;

    }


}
