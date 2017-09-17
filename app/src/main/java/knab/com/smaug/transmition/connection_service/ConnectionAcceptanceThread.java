package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by hp on 2017-09-16.
 */

class ConnectionAcceptanceThread extends Thread {

    private static final String APP_NAME = "Smaug";
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothServerSocket serverSocket;

    public ConnectionAcceptanceThread(BluetoothAdapter bluetoothAdapter){
        BluetoothServerSocket tmpServerSocket = null;

        try {
            tmpServerSocket = bluetoothAdapter
                    .listenUsingInsecureRfcommWithServiceRecord(APP_NAME, MY_UUID_INSECURE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverSocket = tmpServerSocket;
    }

    @Override
    public void run(){
        BluetoothSocket bluetoothSocket = null;

        try {
            bluetoothSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(bluetoothSocket != null){

        }
    }

    public void cancel(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
