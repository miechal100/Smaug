package knab.com.smaug.bluetooth.mvp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by hp on 2017-07-27.
 */

public interface BluetoothMVP {

    interface View{
        void enableBT();
        void disableBT();
        void error();
        void discoverDevices();
        void listDevices(Context context, BluetoothDevice device);
        void startNextActivity(BluetoothDevice device);
    }

    interface Model{
        boolean bluetoothEnableDisable();
        void checkDiscoveringState();
        void cancelDiscovering();
    }

    interface Presenter{
        void btEnableDisable();
        void btEnableDiscovering();
        void destroy();
    }
}
