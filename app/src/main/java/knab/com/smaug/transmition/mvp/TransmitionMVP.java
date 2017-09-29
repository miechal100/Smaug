package knab.com.smaug.transmition.mvp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

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
        void connect(BluetoothDevice pairedDevice, Context context);
        void sendMessage(String message);
        void closeConnection();
    }

    interface Model{
        boolean startConnectionService();
        boolean attemptConnection(BluetoothDevice pairedDevice, Context context);
        boolean sendByConnectionService(String message);
        boolean cancelConnectionService();
    }
}
