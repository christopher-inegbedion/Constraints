/*
* description: TimerConstraint is used to check if a target time is reached provided the current time
* */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeConstraint extends Constraint<Date> {

    public TimeConstraint(Date requirement, Date required_value) {
        super(requirement, required_value);
    }


    /*
    * description: terminates if the requested time has been met or continue to update value to streams method
    **/
    @Override
    public Date dependsOn(Date requirement, Date required_value) {
        Timer timer = new Timer();
        long repeat = 1000;
        boolean finished = false;

        if (finished) {
            return super.dependsOn(requirement, required_value);
        } else {
            timer.schedule(new TimerTask() {
                int counter = 0;

                @Override
                public void run() {
                    if (counter == 5) {
                        System.out.println("finished");
                    } else {
                        System.out.println(counter);
                        counter++;
                    }
                }
            }, 0, repeat);

            return dependsOn(new Date(), new Date());

        }
    }

    @Override
    public boolean returns() {
        return false;
    }
}
