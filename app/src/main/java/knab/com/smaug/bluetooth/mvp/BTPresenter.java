package knab.com.smaug.bluetooth.mvp;

/**
 * Created by hp on 2017-05-19.
 */

public class BTPresenter implements BluetoothMVP.Presenter{

    private BTModel model;
    private BluetoothMVP.View view;

    public BTPresenter(BluetoothMVP.View view, BTModel model){
        this.view = view;
        this.model = model;
    }
    public void btEnableDisable() {
        if(!model.bluetoothEnableDisable()){
            view.enableBT();
        }
        else{
            view.disableBT();
        }
    }

    @Override
    public void btEnableDiscovering() {

        model.checkDiscoveringState();

        view.discoverDevices();

    }
}
