package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by hp on 2017-09-16.
 */

class ConnectionAttemptThread extends Thread {
    private static final String TAG = "Attempt thread";

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice pairedDevice;
    private BluetoothSocket bluetoothSocket;
    private TransmitionThread transmitionThread;
    private Context context;

    public ConnectionAttemptThread(BluetoothDevice pairedDevice, Context context){
        Log.d(TAG, "ConnectThread: started.");
        this.pairedDevice = pairedDevice;
        this.context = context;
    }

    public void run () {
        Log.i(TAG, "RUN connectThread ");
        BluetoothSocket tmpBluetoothSocket = null;

        try {
            Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: "
                    +MY_UUID_INSECURE );
            tmpBluetoothSocket = pairedDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
        } catch (IOException e) {
            Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
        }

        bluetoothSocket = tmpBluetoothSocket;

        try {
            bluetoothSocket.connect();
            Log.d(TAG, "run: ConnectThread connected.");
        } catch (IOException e) {
            try {
                bluetoothSocket.close();
                Log.d(TAG, "run: Closed Socket.");
            } catch (IOException e1) {
                Log.e(TAG, "connectThread: run: Unable to close connection in socket " + e1.getMessage());
            }
            Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE );
        }
        devicesConnected(bluetoothSocket);
    }

    public void cancel(){
        try {
            bluetoothSocket.close();
            Log.d(TAG, "cancel: Closing Client Socket.");
        } catch (IOException e) {
            Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
        }
    }
    public void devicesConnected(BluetoothSocket bluetoothSocket){
        this.transmitionThread = new TransmitionThread(bluetoothSocket, context);
        transmitionThread.start();
    }

    public TransmitionThread getTransmitionThread(){
        return transmitionThread;
    }
}
