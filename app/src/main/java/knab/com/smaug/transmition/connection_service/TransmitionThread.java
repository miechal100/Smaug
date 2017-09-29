package knab.com.smaug.transmition.connection_service;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by hp on 2017-09-16.
 */

class TransmitionThread extends Thread {
    private static final String TAG = "Transmition thread";

    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Intent messageIntent;
    private LocalBroadcastManager localBroadcastManager;

    public TransmitionThread(BluetoothSocket bluetoothSocket, Context context){
        Log.d(TAG, "ConnectedThread: Starting.");
        this.bluetoothSocket = bluetoothSocket;
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
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
        byte[] buffer = new byte[24];
        int bytes;

        while (true) {
            try {
                bytes = this.inputStream.read(buffer);
                String incomingMessage = new String(buffer, 0, bytes);
                this.messageIntent = new Intent("message");
                this.messageIntent.putExtra("message", incomingMessage);
                localBroadcastManager.sendBroadcast(messageIntent);
                Log.d(TAG, "InputStream: " + incomingMessage);
            } catch (IOException e) {
                Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage() );
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
        String message = new String(out, Charset.defaultCharset());
        Log.d(TAG, "write: Writing to outputstream: " + message);
        try {
            outputStream.write(out);
        } catch (IOException e) {
            Log.e(TAG, "write: Error writing to output stream. " + e.getMessage() );
        }
    }
}
