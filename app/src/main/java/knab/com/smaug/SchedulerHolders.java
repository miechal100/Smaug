package knab.com.smaug;

import io.reactivex.Scheduler;

/**
 * Created by hp on 2017-09-22.
 */

public class SchedulerHolders {

    private Scheduler observScheduler;
    private Scheduler subscribeScheduler;

    public SchedulerHolders(Scheduler observScheduler, Scheduler subscribeScheduler){
        this.observScheduler = observScheduler;
        this.subscribeScheduler = subscribeScheduler;
    }

    public Scheduler observ(){
        return observScheduler;
    }

    public Scheduler subscribe(){
        return subscribeScheduler;
    }
}
