package knab.com.smaug.bluetooth.bluetooth_facade;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by hp on 2017-07-27.
 */

public class BluetoothFacade {

    private BluetoothAdapter bluetoothAdapter;


    public BluetoothFacade(BluetoothAdapter bluetoothAdapter){
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public BluetoothAdapter getBluetoothAdapter(){
        return bluetoothAdapter;
    }
}
