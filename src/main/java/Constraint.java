/*
* description: Constraint is the super class that defines the params a Constraint must have
*              T - Depend type
*
*               depending on the type return a constant value or a stream of values
* */
public abstract class Constraint<T> {


    T requirement;
    T required_value;

    /*
    * This is used by a boolean constraint as the expected value will be to return true.
    * */
    public Constraint(T requirement) {
        this.requirement = requirement;
    }

    /*
    * This is used by other constraints that update their values to a service (eg. Time, String, Integer)
    * */
    public Constraint(T requirement, T required_value) {
        this.requirement = requirement;
        this.required_value = required_value;
    }

    public T dependsOn(T requirement) {
        return null;
    }

    public T dependsOn(T requirement, T required_value) {
        return null;
    }

    /*
    * Boolean does not stream any value
    * */
    public T streams() {
        if (requirement instanceof Boolean) {
            return null;
        } else {
            return dependsOn(requirement, required_value);
        }
    }

    public abstract boolean returns();
}
