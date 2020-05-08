package constraints;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CombinedConstraint extends Constraint<Boolean> {

    private Constraint<?> constraint1;
    private Constraint<?> constraint2;

    private Boolean value1;
    private Boolean value2;

    public boolean combined_result;
    public boolean constraint_value;

    int combine_type;

    /*
    * description: this constructor is used if combining 2 seperate constraints
    * params: constraint1 -> first constraint
    *         constraint2 -> second constraint
    *         combine_type -> a value between 0 and 1 with 0 representing AND and 1 representing OR. Can be represented
    *                         by an enum (CombinationTypes)
    * */
    public CombinedConstraint(Constraint<?> constraint1, Constraint<?> constraint2, int combine_type) {
        if (constraint1 == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);
        if (constraint2 == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);
        if (combine_type != 0 & combine_type != 1) throw new IllegalArgumentException("The combine type has to be 0(AND) or 1(OR)");

        this.constraint1 = constraint1;
        this.constraint2 = constraint2;
        this.combine_type = combine_type;
    }

    /*
     * description: this constructor is used if combining a seperate constraints and a combined constraint
     * params: constraint1 -> first constraint
     *         combine_type -> a value between 0 and 1 with 0 representing AND and 1 representing OR. Can be represented
     *                         by an enum (CombinationTypes)
     * */
    public CombinedConstraint(Constraint<?> constraint1, int combine_type) {
        if (constraint1 == null) throw new IllegalArgumentException(REQUIREMENT_NULL_PARAM_ERROR);
        if (combine_type != 0 & combine_type != 1) throw new IllegalArgumentException("The combine type has to be 0(AND) or 1(OR)");

        this.constraint1 = constraint1;
        this.combine_type = combine_type;
    }

    /*
    * description: used when dealing with a separate and combined constraint. signifies a combined constraint is being used.
    * params: combined_constraint_value -> the boolean value of the combined constraint
    * */
    public void hasCombinedConstraint(boolean combined_constraint_value) {
        has_combined_constraint = true;

        constraint_value = combined_constraint_value;
    }

    /*
    * description: utility method used to start a constraint
    * param: constraint -> constraint of task being started
    * */
    private void startConstraint(Constraint<?> constraint) {
        if (constraint.requirement instanceof Boolean) {
            constraint.start(constraint.requirement);
        }  else {
            constraint.start(constraint.requirement, constraint.required_value);
        }
    }

    /*
    * description: combine two booleans given a combination type
    * param: value1 -> first boolean
    *        value2 -> second boolean
    *        combine_type -> a value between 0 and 1 with 0 representing AND and 1 representing OR. Can be represented
    *                        by an enum (CombinationTypes)
    * */
    private boolean combineResult(boolean value1, boolean value2, int combine_type) {
        if (combine_type == 0) {
            //AND
            return value1 & value2;
        } else if (combine_type == 1) {
            //OR
            return value1 || value2;
        } else {
            return false;
        }
    }

    /*
    * description: combine two constraints to provide a boolean value
    * */
    private Boolean combinedConstraints() {
        if (has_combined_constraint) {
            //start constraint
            startConstraint(constraint1);

            //obtain value from constraint1
            try {
                value1 = constraint1.returns().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            //combine constraint values
            return combineResult(value1, constraint_value, combine_type);

        } else {
            //start constraints
            startConstraint(constraint1);
            startConstraint(constraint2);

            //obtain value from constraint1 and constraint2
            try {
                value1 = constraint1.returns().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            try {
                value2 = constraint2.returns().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            //combine constraint types
            return combineResult(value1, value2, combine_type);
        }
    }

    @Override
    public void start(Object req) {
        super.start(req);

        if (!has_combined_constraint & constraint2 == null) throw new IllegalStateException("Combined constraint not found! Try running the hasCombinedConstraint method before the start method");

        futureTask = new FutureTask<>(
                this::combinedConstraints
        );

        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(futureTask);
    }
}
