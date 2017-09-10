package knab.com.smaug.transmition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import knab.com.smaug.R;
import knab.com.smaug.SmaugApplication;
import knab.com.smaug.transmition.mvp.TPresenter;
import knab.com.smaug.transmition.mvp.TransmitionMVP;


public class TransmitionActivity extends AppCompatActivity implements TransmitionMVP.View {

   @Inject
   TPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmition2);

      ((SmaugApplication)getApplication()).getInjector().inject(this);
    }

}
