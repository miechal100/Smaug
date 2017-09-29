package knab.com.smaug.transmition.mvp;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.reactivex.disposables.CompositeDisposable;
import knab.com.smaug.SchedulerHolders;

/**
 * Created by hp on 2017-09-08.
 */

public class TPresenter implements TransmitionMVP.Presenter {

    private TransmitionMVP.View view;
    private TModel model;

    private String string = "";
    private CompositeDisposable compositeDisposable;
    private SchedulerHolders schedulerHolders;

    public TPresenter(TransmitionMVP.View view, TModel model, SchedulerHolders schedulerHolders){
        this.view = view;
        this.model = model;
        this.schedulerHolders = schedulerHolders;
    }

    @Override
    public void initConnectionListener() {
        model.startConnectionService();
    }

    @Override
    public void connect(BluetoothDevice pairedDevice, Context context) {
        if(model.attemptConnection(pairedDevice, context))
            view.onSuccessfulConnection();
    }

    @Override
    public void sendMessage(String message) {
        model.sendByConnectionService(message);
    }

    @Override
    public void closeConnection() {
        model.cancelConnectionService();
    }

    public BroadcastReceiver messageBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            string = string + message;
            if(string.contains(".")){
                view.showMessage(string);
                string = "";
            }
        }
    };
}
