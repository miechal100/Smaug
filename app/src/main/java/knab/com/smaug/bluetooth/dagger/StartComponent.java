package knab.com.smaug.bluetooth.dagger;



import dagger.Component;
import knab.com.smaug.bluetooth.ui.StartActivity;
import knab.com.smaug.dagger.ActivityScope;
import knab.com.smaug.dagger.SmaugComponent;

/**
 * Created by hp on 2017-07-27.
 */
@ActivityScope
@Component(modules = {StartModule.class}, dependencies = {SmaugComponent.class})
public interface StartComponent {

    void inject(StartActivity startActivity);

}
