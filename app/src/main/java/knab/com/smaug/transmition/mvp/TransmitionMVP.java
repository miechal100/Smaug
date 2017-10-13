package knab.com.smaug.transmition.mvp;

import android.bluetooth.BluetoothDevice;

import io.reactivex.Observable;

/**
 * Created by hp on 2017-09-08.
 */

public interface TransmitionMVP {

    interface View{
        void handleError(String message);
        void onSuccessfulConnection();
        void showMessage(String message);
    }

    interface Presenter{
        void initConnectionListener();
        void connect(BluetoothDevice pairedDevice);
        void sendMessage(String message);
        void startReading();
        void closeConnection();
    }

    interface Model{
        boolean startConnectionService();
        boolean attemptConnection(BluetoothDevice pairedDevice);
        boolean sendByConnectionService(String message);
        Observable<String> readFromConnectionService();
        boolean cancelConnectionService();
    }
}
