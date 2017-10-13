package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by hp on 2017-09-16.
 */

class ConnectionAcceptanceThread extends Thread {
    private static final String TAG = "Acceptance thread";

    private static final String APP_NAME = "Smaug";
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothServerSocket serverSocket;

    ConnectionAcceptanceThread(BluetoothAdapter bluetoothAdapter){
        BluetoothServerSocket tmpServerSocket = null;

        try {
            tmpServerSocket = bluetoothAdapter
                    .listenUsingInsecureRfcommWithServiceRecord(APP_NAME, MY_UUID_INSECURE);
            Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
        } catch (IOException e) {
            Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
        }

        serverSocket = tmpServerSocket;
    }

    @Override
    public void run(){
        Log.d(TAG, "run: AcceptThread Running.");
        BluetoothSocket bluetoothSocket = null;

        try {
            Log.d(TAG, "run: RFCOM server socket start.....");
            bluetoothSocket = serverSocket.accept();

            Log.d(TAG, "run: RFCOM server socket accepted connection.");
        } catch (IOException e) {
            Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
        }

        if(bluetoothSocket != null){
            devicesConnected(bluetoothSocket);
        }
        Log.i(TAG, "END mAcceptThread ");
    }

    void cancel(){
        Log.d(TAG, "cancel: Canceling AcceptThread.");
        try {
            serverSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
        }
    }

    void devicesConnected(BluetoothSocket bluetoothSocket){
        TransmitionThread transmitionThread = new TransmitionThread(bluetoothSocket);
        transmitionThread.start();
    }
}
