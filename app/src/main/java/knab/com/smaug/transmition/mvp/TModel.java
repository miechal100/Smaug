package knab.com.smaug.transmition.mvp;

import javax.inject.Inject;

import knab.com.smaug.bluetooth.bluetooth_facade.BluetoothFacade;
import knab.com.smaug.dagger.ActivityScope;

/**
 * Created by hp on 2017-09-08.
 */
@ActivityScope
public class TModel implements TransmitionMVP.Model {

    private BluetoothFacade bluetoothFacade;

    @Inject
    public TModel(BluetoothFacade bluetoothFacade){
        this.bluetoothFacade = bluetoothFacade;
    }


}
