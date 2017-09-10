package knab.com.smaug.transmition.dagger;

import dagger.Module;
import dagger.Provides;
import knab.com.smaug.dagger.ActivityScope;
import knab.com.smaug.transmition.mvp.TModel;
import knab.com.smaug.transmition.mvp.TPresenter;
import knab.com.smaug.transmition.mvp.TransmitionMVP;

/**
 * Created by hp on 2017-09-08.
 */
@Module
public class TransmitionModule {
    private TransmitionMVP.View view;

    public TransmitionModule(TransmitionMVP.View view){
        this.view = view;
    }

    @Provides
    @ActivityScope
    public TPresenter provideTPresenter(TModel model){
        return new TPresenter(view, model);
    }
}
