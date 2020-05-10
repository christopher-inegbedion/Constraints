package constraints;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ReadyConstraint extends Constraint<Boolean> {

    public ReadyConstraint(Boolean requirement) {
        super(requirement);
        TAG = "ReadyConstraint";
    }

    @Override
    public void start(Object requirement) {
        super.start(requirement);

        futureTask = new FutureTask<>(
                () -> (Boolean) requirement
        );

        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(futureTask);
    }
}
