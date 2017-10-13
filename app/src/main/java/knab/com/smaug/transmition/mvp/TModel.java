package knab.com.smaug.transmition.mvp;

import android.bluetooth.BluetoothDevice;

import java.nio.charset.Charset;

import javax.inject.Inject;

import io.reactivex.Observable;
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
        this.bluetoothFacade = bluetoothFacade;
        this.bluetoothConnectionService = bluetoothConnectionService;
    }


    @Override
    public boolean startConnectionService() {

            bluetoothConnectionService.init(bluetoothFacade.getBluetoothAdapter());

        return false;
    }

    @Override
    public boolean attemptConnection(BluetoothDevice pairedDevice) {
        if(bluetoothConnectionService.attemptConnection(pairedDevice))
            return true;
        return false;
    }

    @Override
    public boolean sendByConnectionService(String message) {
        byte[] out = message.getBytes(Charset.defaultCharset());
        bluetoothConnectionService.write(out);
        return true;
    }

    @Override
    public Observable<String> readFromConnectionService() {
        return bluetoothConnectionService.read();
    }

    @Override
    public boolean cancelConnectionService() {
        bluetoothConnectionService.close();
        bluetoothConnectionService = null;
        bluetoothFacade.getBluetoothAdapter().disable();
        return false;
    }
}
