package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by hp on 2017-09-16.
 */

class ConnectionAttemptThread extends Thread {
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private BluetoothDevice pairedDevice;
    private BluetoothSocket bluetoothSocket;
    public ConnectionAttemptThread(BluetoothDevice pairedDevice){
        this.pairedDevice = pairedDevice;
    }

    public void run(){
        BluetoothSocket tmpBluetoothSocket = null;

        try {
            tmpBluetoothSocket = pairedDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bluetoothSocket = tmpBluetoothSocket;

        try {
            bluetoothSocket.connect();
        } catch (IOException e) {
            try {
                bluetoothSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void cancel(){
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
