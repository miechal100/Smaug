package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hp on 2017-09-16.
 */

class TransmitionThread extends Thread {

    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public TransmitionThread(BluetoothSocket bluetoothSocket){
        this.bluetoothSocket = bluetoothSocket;
        InputStream tmpInputStream = null;
        OutputStream tmpOutputStream = null;

        try {
            tmpInputStream = this.bluetoothSocket.getInputStream();
            tmpOutputStream = this.bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.inputStream = tmpInputStream;
        this.outputStream = tmpOutputStream;


    }
    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {
                bytes = this.inputStream.read(buffer);
                String incomingMassage = new String(buffer, 0, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void cancel(){
        try {
            this.bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] out){
        try {
            outputStream.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
