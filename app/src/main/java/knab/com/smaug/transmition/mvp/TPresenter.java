package knab.com.smaug.transmition.mvp;

/**
 * Created by hp on 2017-09-08.
 */

public class TPresenter implements TransmitionMVP.Presenter {

    private TransmitionMVP.View view;
    private TModel model;

    public TPresenter(TransmitionMVP.View view, TModel model){
        this.view = view;
        this.model = model;
    }
}
