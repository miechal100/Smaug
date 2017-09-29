package knab.com.smaug.transmition.ui;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import knab.com.smaug.R;
import knab.com.smaug.SmaugApplication;
import knab.com.smaug.transmition.mvp.TPresenter;
import knab.com.smaug.transmition.mvp.TransmitionMVP;


public class TransmitionActivity extends AppCompatActivity implements TransmitionMVP.View {
    private static final String TAG = "Transmition activity";

    @Inject
    TPresenter presenter;

    @BindView(R.id.messageEditText)
    EditText messageEditText;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.messageTextView)
    TextView textView;

    private int i = 0;

    private ProgressDialog connectionProgressDialog;
    StringBuilder message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmition2);

        ButterKnife.bind(this);
        ((SmaugApplication)getApplication()).getInjector().inject(this);

        BluetoothDevice pairedDevice = getIntent().getParcelableExtra("device");
        this.message = new StringBuilder();

        Log.d(TAG, "onCreate called");
        sendButton.setOnClickListener(v -> send());
        init(pairedDevice);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        presenter.closeConnection();
        unregisterReceiver(presenter.messageBroadcastReceiver);
    }

    private void send() {
        String message = messageEditText.getText().toString();
        presenter.sendMessage(message);
        messageEditText.setText("");
    }

    private void init(BluetoothDevice pairedDevice) {
        //connectionProgressDialog = ProgressDialog.show(this, "HC-05 Connection", "Waiting for connection - please wait...");
        presenter.initConnectionListener();
        presenter.connect(pairedDevice, this);
    }


    @Override
    public void handleError(String message) {

    }

    @Override
    public void onSuccessfulConnection(){
        IntentFilter messageIntentFilter = new IntentFilter("message");
        LocalBroadcastManager.getInstance(this).registerReceiver(presenter.messageBroadcastReceiver, messageIntentFilter);
    }

    @Override
    public void showMessage(String message) {

        textView.setText(this.message.append("Wartość Smogu z potencjometru: " + message + "%\n"));
        i++;
        if(i == 15){
            textView.setText("");
            this.message.delete(0, this.message.length());
            i=0;
        }
        if(message.contains("100")){
            textView.setText(this.message.append("XD\n"));
            i++;
            if(i == 15){
                textView.setText("");
                this.message.delete(0, this.message.length());
                i=0;
            }
        }
    }
}
