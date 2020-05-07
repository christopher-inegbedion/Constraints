/*
* description: TimerConstraint is used to check if a target time is reached provided the current time
* */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class TimeConstraint extends Constraint<Date> {

    boolean value = false;
    FutureTask<Boolean> futureTask;
    boolean started = false;

    public TimeConstraint(Date requirement, Date required_value) {
        super(requirement, required_value);
    }

    /*
    * description: terminates if the requested time has been met or continue to update value to stream method
    **/
    @Override
    public Date start(Date requirement, Date required_value) {
        if (requirement == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);
        if (required_value == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);

        started = true;

        long diff = Math.abs(required_value.getTime() - requirement.getTime());

        //create a future that passes current time to stream and final value to returns
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

        return null;
    }

    @Override
    public Date streams() {

        if (!started) throw new IllegalStateException(CONSTRAINT_NOT_STARTED);

        while (!futureTask.isDone()) {
            System.out.println("waiting");
        }

        System.out.println("done!");


        return super.streams();
    }

    @Override
    public Future<Boolean> returns() {
        return futureTask;
    }
}
