package knab.com.smaug.bluetooth.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import knab.com.smaug.SmaugApplication;
import knab.com.smaug.R;
import knab.com.smaug.bluetooth.mvp.BluetoothMVP;
import knab.com.smaug.bluetooth.mvp.BTPresenter;
import knab.com.smaug.data.data.MainActivity;

public class StartActivity extends AppCompatActivity implements BluetoothMVP.View{

    private static final String TAG = "StartActivity";

    private ArrayList<BluetoothDevice> bluetoothDevicesList;
    private DeviceListAdapter deviceListAdapter;


    @BindView(R.id.btOnOff)
    Button btONOFF;
    @BindView(R.id.btDiscover)
    Button btnStartDiscovering;

    private ListView lvNewDevices;

    @Inject
    BTPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        bluetoothDevicesList = new ArrayList<>();

        ((SmaugApplication)getApplication()).getInjector().inject(this);

        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);

        btONOFF.setOnClickListener(v-> presenter.btEnableDisable());
        btnStartDiscovering.setOnClickListener(v -> presenter.btEnableDiscovering());
        lvNewDevices.setOnItemClickListener((adapter, view, position, id)->pairDevices(position));
    }

    private void pairDevices(int i) {
        String deviceName = bluetoothDevicesList.get(i).getName();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
          bluetoothDevicesList.get(i).createBond();
        }
        Toast.makeText(this, "Jesteś połączony z " + deviceName, Toast.LENGTH_SHORT).show();

        IntentFilter pairingDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(receivingBondingDevicesBroadcast, pairingDevicesIntent);
    }

    @Override
    public void enableBT() {
         //   Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
           // startActivity(enableBTIntent);
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 308);
        startActivity(discoverableIntent);
        Log.d(TAG, "bluetooth enabled");
    }

    @Override
    public void disableBT() {
        Log.d(TAG, "bluetooth disabled");
    }

    @Override
    public void error() {
        Log.d(TAG, "bluetooth not available");
        Toast.makeText(this, "bluetooth is not avaliable", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void discoverDevices() {

        IntentFilter btDiscoverIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, btDiscoverIntent);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            Log.d(TAG, "Action found");

            if(action.equals(BluetoothDevice.ACTION_FOUND)){


                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothDevicesList.add(device);

                deviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, bluetoothDevicesList);
                lvNewDevices.setAdapter(deviceListAdapter);

            }
        }
    };

    private BroadcastReceiver receivingBondingDevicesBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "JEsteś połączony");
                    startNextActivity(device);
                }
                if(device.getBondState() == BluetoothDevice.BOND_BONDING){
                    Log.d(TAG, "łączysz się");
                }
                if(device.getBondState() == BluetoothDevice.BOND_NONE){
                    Log.d(TAG, "Brak Połączenia");
                }
            }
        }
    };

    private void startNextActivity(BluetoothDevice device) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Paired device", device);
        startActivity(intent);

    }
}
