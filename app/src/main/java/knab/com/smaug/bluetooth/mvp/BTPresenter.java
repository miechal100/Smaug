package knab.com.smaug.bluetooth.mvp;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by hp on 2017-05-19.
 */

public class BTPresenter implements BluetoothMVP.Presenter{

    private BTModel model;
    private BluetoothMVP.View view;
    private static final String TAG = "BTPresenter";

    public BTPresenter(BluetoothMVP.View view, BTModel model){
        this.view = view;
        this.model = model;
    }
    public void btEnableDisable() {
        if(!model.bluetoothEnableDisable()){
            view.enableBT();
        }
        else{
            view.disableBT();
        }
    }

    @Override
    public void btEnableDiscovering() {

        model.checkDiscoveringState();

        view.discoverDevices();
    }

    @Override
    public void onDestroy() {
        model.cancelDiscovering();
    }

    public BroadcastReceiver discoveredDevicesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            Log.d(TAG, "Action found");

            if(action.equals(BluetoothDevice.ACTION_FOUND)){

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                view.listDevices(context, device);
            }
        }
    };

    public BroadcastReceiver bondingDevicesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            Log.d(TAG, "Action found");

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDED:
                        Log.d(TAG, "You are connected with " + deviceName);
                        view.startNextActivity(device);
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        Log.d(TAG, "connecting...");
                        break;
                    case BluetoothDevice.BOND_NONE:
                        Log.d(TAG, "error, no connection");
                        break;
                }
            }
        }
    };
}
