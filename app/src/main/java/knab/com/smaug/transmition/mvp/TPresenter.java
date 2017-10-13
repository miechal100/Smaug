package knab.com.smaug.transmition.mvp;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import knab.com.smaug.ClearingDisposables;
import knab.com.smaug.SchedulerHolders;

/**
 * Created by hp on 2017-09-08.
 */

public class TPresenter implements TransmitionMVP.Presenter, ClearingDisposables {
    private static final String TAG = "TPresenter";

    private TransmitionMVP.View view;
    private TModel model;

    private String string = "";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
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
    public void connect(BluetoothDevice pairedDevice) {
        if(model.attemptConnection(pairedDevice))
            view.onSuccessfulConnection();
    }

    @Override
    public void sendMessage(String message) {
        model.sendByConnectionService(message);
    }

    @Override
    public void startReading() {

            compositeDisposable.add(model.readFromConnectionService()
                    .observeOn(schedulerHolders.observ())
                    .subscribeOn(schedulerHolders.subscribe())
                    .subscribeWith(new DisposableObserver<String>() {
                        @Override
                        public void onNext(String message) {
                            if(message != null){
                                Log.d(TAG, "Observer: onNext called.");
                                string = string + message;
                                if(string.contains(".")){
                                    view.showMessage(string);
                                    string = "";
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "Observer: onError called.");
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "Observer: onComplete called.");
                        }
                    }));


    }


    @Override
    public void closeConnection() {
        model.cancelConnectionService();
    }

    @Override
    public void clearDisposables() {
        Log.d(TAG, "clearDisposables called.");
        compositeDisposable.dispose();
    }
}
