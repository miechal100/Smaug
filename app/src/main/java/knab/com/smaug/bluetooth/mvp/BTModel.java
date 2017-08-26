package knab.com.smaug.bluetooth.mvp;

import android.Manifest;
import android.os.Build;

import javax.inject.Inject;

import knab.com.smaug.bluetooth.bluetooth_facade.BluetoothFacade;
import knab.com.smaug.dagger.ActivityScope;

/**
 * Created by hp on 2017-05-19.
 */
@ActivityScope
public class BTModel implements BluetoothMVP.Model {

    private BluetoothFacade bluetoothFacade;

    @Inject
    public BTModel(BluetoothFacade bluetoothFacade){
        this.bluetoothFacade = bluetoothFacade;
    }
    public boolean bluetoothEnableDisable() {
        if(bluetoothFacade.getBluetoothAdapter().isEnabled()){
            disableBluetoothAdapter();
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void checkDiscoveringState() {

        if(bluetoothFacade.getBluetoothAdapter().isDiscovering()){
            bluetoothFacade.getBluetoothAdapter().cancelDiscovery();
            bluetoothFacade.getBluetoothAdapter().startDiscovery();
        }
        else{
            bluetoothFacade.getBluetoothAdapter().startDiscovery();
        }


    }

    public void disableBluetoothAdapter(){
        bluetoothFacade.getBluetoothAdapter().disable();
    }


}
