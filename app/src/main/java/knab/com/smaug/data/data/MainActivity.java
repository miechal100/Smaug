package knab.com.smaug.data.data;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import knab.com.smaug.R;


public class MainActivity extends AppCompatActivity {

    private BluetoothDevice pairedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pairedDevice = getIntent().getParcelableExtra("Paired device");

        Toast.makeText(this, "Jesteś połączony z" + pairedDevice.getName(), Toast.LENGTH_SHORT).show();
    }

}
