package stage;

import constraints.Constraint;
import enums.Colors;
import enums.ConstraintStatus;
import models.StageConstraintStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//FIXME: Implement adding CombinedConstraint functionality and startCombinedConstraint
public class Stage {

    private final String stage_name;
    private boolean stage_started = false;

    private List<Constraint<?>> constraints;
    private List<StageConstraintStatus> constraints_status;

    public Stage(String stage_name) {
        if (stage_name.isEmpty()) throw new IllegalArgumentException("A stage name has to be passed");

        this.stage_name = stage_name;

        constraints = new ArrayList<>();
        constraints_status = new ArrayList<>();
    }

    /*
    * description: adds a constraint to a stage
    * params: constraint - a constraint to be added to the stage
    * */
    public void addConstraint(Constraint<?> constraint) {
        if (constraint == null) throw new IllegalArgumentException("Null value cannot be passed to addConstraint");
        if (stage_started) throw new IllegalStateException("Constraint cannot be added after stage has started");

        constraints.add(constraint);

        //Create a constraint status object for each constraint
        StageConstraintStatus constraint_status = new StageConstraintStatus(constraint.getTag(), ConstraintStatus.IDLE);
        constraints_status.add(constraint_status);
    }

    /*
    * description: change the status of a constraint, given its position in `constraints` arraylist
    * params: index - position of constraint in arraylist
    *         constraintStatus - status to be changed to
    * */
    public void changeConstraintStatus(int index, ConstraintStatus constraintStatus) {
        if (index > constraints_status.size() || index < 0)
            throw new IndexOutOfBoundsException("Index cannot be less than 0 or greater than the number of constraints");

        constraints_status.get(index).setStatus(constraintStatus);
    }

    /*
    * description: method to start individual constraints
    * params: constraint - constraint to be started
    * */
    public void startConstraint(Constraint<?> constraint) {
        if (constraint.requirement instanceof Boolean) {
            constraint.start(constraint.requirement);
        } else {
            constraint.start(constraint.requirement, constraint.required_value);
        }
    }

    public void startCombinedConstraint(Constraint<?> constraint) {
        //FIXME: Implement this method to start a combined constraint
    }

    /*
    * description: returns the boolean value of a constraint
    * params: constraint - constraint value to be retrieved
    * */
    public Future<Boolean> getConstraintValue(Constraint<?> constraint) {
        return constraint.returns();
    }

    /*
    * description: starts a stage with all of its constraints
    * */
    public void start() throws ExecutionException, InterruptedException {
        if (constraints.size() < 1) throw new IllegalStateException("Stage cannot be started with no constraint");

        int i = 0;

        //stage beginning info//
        System.out.println(Colors.BLACK_UNDERLINED + "Stage → " + '"' + stage_name + '"' + Colors.RESET);
        logConstraints();
        System.out.println(Colors.BLACK_BOLD + "\nSTARTED" + Colors.RESET);

        stage_started = true;
        ////////////////////////

        for (Constraint<?> constraint : constraints) {

            if (constraint.has_combined_constraint) {
                startCombinedConstraint(constraint);
            } else {
                startConstraint(constraint);

                changeConstraintStatus(i, ConstraintStatus.STARTED);

                /*do {
                    System.out.println(constraint.getTag() + " working");
                }  while (!constraint.returns().isDone());*/

                logConstraints();
                System.out.println("\nᐅ " +Colors.RED_UNDERLINED + constraint.getTag() + Colors.RESET +
                        Colors.GREEN + " FINISHED" + Colors.RESET +
                        " with value: " + getConstraintValue(constraint).get() + " ᐊ \n");
                System.out.println("");

                changeConstraintStatus(i, ConstraintStatus.FINISHED);

            }

            i++;
        }

        stage_started = false;
        System.out.println(Colors.BLACK_BOLD + "FINISHED" + Colors.RESET);
        logConstraints();

    }

    /*
    * description: display details of the constraints
    * */
    public void logConstraints() throws ExecutionException, InterruptedException {
        int i = 0;
        for (StageConstraintStatus constraintStatus : constraints_status) {
            if (constraints.get(i).started) {
                switch (constraintStatus.getStatus()) {
                    case IDLE:
                        System.out.println((i+1) + ") → " + constraintStatus.getConstraintTag() + ":"
                                + Colors.BLUE + constraintStatus.getStatus() + Colors.RESET + ":"
                                + constraints.get(i).returns().get());
                        break;
                    case STARTED:
                        System.out.println((i+1) + ") → " + constraintStatus.getConstraintTag() + ":"
                                + Colors.RED + constraintStatus.getStatus() + Colors.RESET + ":"
                                + constraints.get(i).returns().get());
                        break;
                    case FINISHED:
                        System.out.println((i+1) + ") → " + constraintStatus.getConstraintTag() + ":"
                                + Colors.GREEN + constraintStatus.getStatus() + Colors.RESET + ":"
                                + constraints.get(i).returns().get());
                        break;
                }
            } else {
                System.out.println((i+1) + ") → " + constraintStatus.getConstraintTag() + ":"
                        + Colors.BLUE + constraintStatus.getStatus() + Colors.RESET + ":"
                        + "not started");
            }

            i++;
        }

    }

//    public Future<Boolean> returns() {
//
//    }
}
