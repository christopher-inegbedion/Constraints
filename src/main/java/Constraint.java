import java.util.concurrent.Future;

/*
* description: Constraint is the super class that defines the params a Constraint must have
*              T - Depend type
*
*               depending on the type return a constant value or a stream of values
* */
public abstract class Constraint<T> {

    public final static String REQUIREMENT_NULL_PARAM_ERROR = "A null value cannot be passed as an argument.";
    public final static String CONSTRAINT_NOT_STARTED = "Constraint has not been started.";

    T requirement;
    T required_value;

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

    /*
    * description: used for boolean Constraint types. there is no streaming and the value is passed directly to return
    *              method.
    * param: requirement -> value passed by user
    * */
    public T start(T requirement) {
        if (requirement == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);

        return null;
    }

    /*
    * description: processes the requirements of the constraint and passes the unfinished value to stream method and then the
    *              complete value to return method.
    * param: requirement -> value passed by user
    *        required_value -> value expected
    * */
    public T start(T requirement, T required_value) {
        return null;
    }

    /*
    * Boolean does not stream any value
    *
    * description: the stream method is used by services and passes the current value.
    * */
    public T streams() {
        if (requirement instanceof Boolean) {
            return null;
        } else {
            return start(requirement, required_value);
        }
    }

    /*
    * description: return a boolean future
    * */
    public abstract Future<Boolean> returns();
}
