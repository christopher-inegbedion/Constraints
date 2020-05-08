package constraints;/*
* description: TimerConstraint is used to check if a target time is reached provided the current time
* */

import java.util.Date;
import java.util.concurrent.*;

public class TimeConstraint extends Constraint<Date> {

    boolean value = false;

    public TimeConstraint(Date requirement, Date required_value) {
        super(requirement, required_value);
    }

    /*
    * description: terminates if the requested time has been met or continue to update value to stream method
    **/
    @Override
    public void start(Object requirement, Object required_value) {
        super.start(requirement, required_value);

        Date req = (Date) requirement;
        Date req_val = (Date) required_value;

        long diff = req_val.getTime() - req.getTime();
        if (diff < 0) throw new IllegalStateException("Required time cannot be before the current time");

        //create a future that waits for the current time to equals the target time
        futureTask = new FutureTask<>(
                () -> {
                    value = true;
                    //for demo use
                    Thread.sleep(diff);

                    return value;
                }
        );

        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(futureTask);

    }
}
