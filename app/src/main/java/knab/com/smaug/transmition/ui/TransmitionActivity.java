package knab.com.smaug.transmition.ui;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import knab.com.smaug.R;
import knab.com.smaug.SmaugApplication;
import knab.com.smaug.transmition.mvp.TPresenter;
import knab.com.smaug.transmition.mvp.TransmitionMVP;
import knab.com.smaug.utils.DateUtils;


public class TransmitionActivity extends AppCompatActivity implements TransmitionMVP.View {
    private static final String TAG = "Transmition activity";

    @Inject
    TPresenter presenter;

    @BindView(R.id.smaugTextView)
    TextView smaugTextView;
    @BindView(R.id.messageEditText)
    EditText messageEditText;
    @BindView(R.id.startConnectionButton)
    Button startConnectionButton;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.readButton)
    Button readButton;
    @BindView(R.id.potentialTextView)
    TextView potentialTextView;
    @BindView(R.id.messageTextView)
    TextView messageTextView;

    StringBuilder message;
    int i = 0;

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
        readButton.setOnClickListener(v -> read());
        startConnectionButton.setOnClickListener(v -> init(pairedDevice));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        presenter.closeConnection();
        presenter.clearDisposables();
    }

    private void send() {
        String message = messageEditText.getText().toString();
        presenter.sendMessage(message);
        messageEditText.setText("");
    }


    private void read() {
        presenter.startReading();
    }

    @Override
    public void secondReading() { presenter.startReading(); }

    private void init(BluetoothDevice pairedDevice) {
        presenter.initConnectionListener();
        presenter.connect(pairedDevice);
        enableView();
    }

    private void enableView() {
        startConnectionButton.setVisibility(View.GONE);
        smaugTextView.setVisibility(View.VISIBLE);
        readButton.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.VISIBLE);
        messageEditText.setVisibility(View.VISIBLE);
        potentialTextView.setVisibility(View.VISIBLE);
        messageTextView.setVisibility(View.VISIBLE);
    }


    @Override
    public void handleError(String message) {

    }

    @Override
    public void onSuccessfulConnection(){

    }

    @Override
    public void showMessage(String message) {

        i++;
        if(i == 5){
            messageTextView.setText("");
            this.message.delete(0, this.message.length());
            i=0;
        }

        messageTextView.setText("Lokalizacja: Warszawa\n" + DateUtils.now()
                        + "\nWartość: " + message + "μg/m3\n");

    }
}
