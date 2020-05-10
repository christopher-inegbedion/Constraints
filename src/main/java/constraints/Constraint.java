package constraints;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/*
* description: constraints.Constraint is the super class that defines the params a constraints.Constraint must have
*              T - Depend type
*
*               depending on the type, return a constant value or wait for values
* */
public abstract class Constraint<T> {

    public final static String REQUIREMENT_NULL_PARAM_ERROR = "A null value cannot be passed as an argument.";
    public final static String CONSTRAINT_NOT_STARTED = "constraints.Constraint has not been started.";

    public String TAG;

    public boolean started = false;
    public boolean has_combined_constraint = false;

    public T requirement;
    public T required_value;

    protected FutureTask<Boolean> futureTask;

    public Constraint() {}

    /*
    * This is used by a boolean constraint as the expected value will be to return true.
    * */
    public Constraint(T requirement) {
        if (requirement == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);

        this.requirement = requirement;
    }

    /*
    * This is used by other constraints that update their values to a service (eg. Time, String, Integer)
    * */
    public Constraint(T requirement, T required_value) {
        if (requirement == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);
        if (required_value == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);

        this.requirement = requirement;
        this.required_value = required_value;
    }

    private void setTag(String tag) {
        TAG = tag;
    }

    public String getTag() {
        return TAG;
    }

    /*
    * description: used for boolean constraints. there is no updating and the value is passed directly to return
    *              method.
    * param: requirement -> value passed by user
    * */
    //FIXME: change the start method definition to -> startWithoutRequiredValue
    public void start(Object requirement) {
        if (requirement == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);

        started = true;
    }

    /*
    * description: processes the requirements of the constraint and passes the unfinished value to update method and then the
    *              complete value to return method.
    * param: requirement -> value passed by user
    *        required_value -> value expected
    * */

    //FIXME: change the start method definition to -> startWithRequiredValue
    public void start(Object requirement, Object required_value) {
        if (requirement == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);
        if (required_value == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);

        started = true;

    }

    /*
    * Boolean type does not update any value
    *
    * description: the update method is used by services, and is used to indicate that a constraint is not ready.
    * */
    public void updates(String waiting_message) {
        if (!started) throw new IllegalStateException(CONSTRAINT_NOT_STARTED);
        if (requirement == null) throw new IllegalStateException(CONSTRAINT_NOT_STARTED);

        if (!(requirement instanceof Boolean)) {
            while (!futureTask.isDone()) {
                System.out.println(waiting_message);
            }
            futureTask.cancel(true);
            System.out.println("done!");

        }
        returns();
    }

    /*
    * description: return a boolean future
    * */
    public Future<Boolean> returns() {
        if (!started) throw new IllegalStateException(CONSTRAINT_NOT_STARTED);

        return futureTask;
    }
}
