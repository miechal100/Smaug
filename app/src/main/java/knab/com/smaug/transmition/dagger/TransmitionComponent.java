package knab.com.smaug.transmition.dagger;

import dagger.Component;
import knab.com.smaug.dagger.ActivityScope;
import knab.com.smaug.dagger.SmaugComponent;
import knab.com.smaug.transmition.TransmitionActivity;

/**
 * Created by hp on 2017-09-08.
 */
@ActivityScope
@Component(modules = {TransmitionModule.class}, dependencies = {SmaugComponent.class})
public interface TransmitionComponent {

    void inject(TransmitionActivity transmitionActivity);
}
