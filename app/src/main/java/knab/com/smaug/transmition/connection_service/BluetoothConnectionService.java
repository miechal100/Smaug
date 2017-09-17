package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * Created by hp on 2017-09-14.
 */

public class BluetoothConnectionService {

    private BluetoothDevice pairedDevice;
    private BluetoothAdapter bluetoothAdapter;
    private ConnectionAcceptanceThread acceptanceThread;
    private ConnectionAttemptThread attemptThread;
    private TransmitionThread transmitionThread;

    public BluetoothConnectionService(BluetoothAdapter bluetoothAdapter, BluetoothDevice pairedDevice){
        this.bluetoothAdapter = bluetoothAdapter;
        this.pairedDevice = pairedDevice;
        start();
    }
    private synchronized void start(){

        if(attemptThread != null){
            attemptThread.cancel();
            attemptThread = null;
        }

        if(acceptanceThread == null){
            acceptanceThread = new ConnectionAcceptanceThread(bluetoothAdapter);
            acceptanceThread.start();
        }
    }
    public void attemptConnection(){
        attemptThread = new ConnectionAttemptThread(pairedDevice);
        attemptThread.start();
    }

    public void devicesConnected(BluetoothSocket bluetoothSocket){
        transmitionThread = new TransmitionThread(bluetoothSocket);
    }

    public void write(byte[] out){
        transmitionThread.write(out);
    }

}