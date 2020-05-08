package stage;

import constraints.Constraint;

import java.util.ArrayList;
import java.util.List;

public class Stage {

    List<Constraint<?>> constraints;
    String stage_name;

    public Stage(String stage_name) {
        this.stage_name = stage_name;

        constraints = new ArrayList<>();
    }

    public void addConstraint(Constraint<?> constraint) {
        constraints.add(constraint);
    }


    public void startConstraint(Constraint<?> constraint) {

    }

    public void startCombinedConstraint(Constraint<?> constraint) {}

    public void start() {
        for (Constraint<?> constraint : constraints) {
            if (constraint.has_combined_constraint) {
                startCombinedConstraint(constraint);
            } else {
                startConstraint(constraint);
            }
        }
    }

//    public Future<Boolean> returns() {
//
//    }
}
