package knab.com.smaug.bluetooth.mvp;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by hp on 2017-07-27.
 */

public interface BluetoothMVP {

    interface View{
        void enableBT();
        void disableBT();
        void error();
        void discoverDevices();
    }

    interface Model{
        boolean bluetoothEnableDisable();
        void checkDiscoveringState();
    }

    interface Presenter{
        void btEnableDisable();
        void btEnableDiscovering();
    }
}
