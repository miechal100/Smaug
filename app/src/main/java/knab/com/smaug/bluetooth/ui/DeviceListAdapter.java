package knab.com.smaug.bluetooth.ui;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import knab.com.smaug.R;

/**
 * Created by hp on 2017-08-11.
 */

public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice>{

    private LayoutInflater layoutInflater;
    private ArrayList<BluetoothDevice> devicesList;
    private int viewResourceId;

    public DeviceListAdapter(Context context, int tvResourceId, ArrayList<BluetoothDevice> devicesList){
        super(context,tvResourceId, devicesList);
        this.devicesList = devicesList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = tvResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(viewResourceId, null);

        BluetoothDevice device = devicesList.get(position);

        if (device != null) {
            TextView deviceName = (TextView) convertView.findViewById(R.id.tvDeviceName);
            TextView deviceAdress = (TextView) convertView.findViewById(R.id.tvDeviceAddress);
//
          if (deviceName != null) {
                  deviceName.setText(device.getName());
          }
          if (deviceAdress != null) {
                  deviceAdress.setText(device.getAddress());
          }
        }

        return convertView;
    }

}
