package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hp on 2017-09-14.
 */

public class BluetoothConnectionService {

    private BluetoothAdapter bluetoothAdapter;
    private ConnectionAcceptanceThread acceptanceThread;
    private ConnectionAttemptThread attemptThread;
    private TransmitionThread transmitionThread;

    public void init(BluetoothAdapter bluetoothAdapter){
        this.bluetoothAdapter = bluetoothAdapter;
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

    public boolean attemptConnection(BluetoothDevice pairedDevice, Context context){
        attemptThread = new ConnectionAttemptThread(pairedDevice, context);
        attemptThread.start();
        waitForConnection();
        return true;
    }

    private void waitForConnection() {
        while (( this.transmitionThread = attemptThread.getTransmitionThread()) == null) {}
    }

    public void write(byte[] out){
        this.transmitionThread.write(out);
    }

    public boolean close(){
        acceptanceThread.cancel();
        attemptThread.cancel();
        transmitionThread.cancel();
        return true;
    }
}