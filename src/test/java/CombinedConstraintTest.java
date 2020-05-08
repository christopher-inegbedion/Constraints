import constraints.CombinedConstraint;
import constraints.ReadyConstraint;
import constraints.TimeConstraint;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static enums.CombinationTypes.AND;
import static enums.CombinationTypes.OR;
import static org.junit.Assert.*;

public class CombinedConstraintTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThatNullCannotBePassedFor2SeparateConstraints() {
        new CombinedConstraint(new TimeConstraint(new Date(), new Date()), null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatNullCannotBePassedForCombinedConstraintAndSeparateConstraint() {
        new CombinedConstraint(null, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testIfhasCombinedConstraintMethodIsCalledForCombinedConstraint() {
        ReadyConstraint readyConstraint = new ReadyConstraint(false);
        CombinedConstraint combinedConstraint = new CombinedConstraint(readyConstraint, 0);
        combinedConstraint.start(combinedConstraint.combined_result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThat0or1IsPassedAsCombineType() {
        new CombinedConstraint(new TimeConstraint(new Date(), new Date()),
                new TimeConstraint(new Date(), new Date()), 2);
    }

    @Test
    public void test_That_Combined_Constraint_Value_Equals_Combined_Constraint_Return_Value_Is_Called() throws ExecutionException, InterruptedException {
        CombinedConstraint combinedConstraint = new CombinedConstraint(new ReadyConstraint(true), new ReadyConstraint(false), OR.getCombination());
        combinedConstraint.start(combinedConstraint.combined_result);

        CombinedConstraint combinedConstraint1 = new CombinedConstraint(new ReadyConstraint(false), AND.getCombination());
        try {
            combinedConstraint1.hasCombinedConstraint(combinedConstraint.returns().get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(combinedConstraint1.constraint_value, combinedConstraint.returns().get());
    }
}
