import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TimeConstraintTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullValuesAreNotPassedAsParams() {
        TimeConstraint constraint = new TimeConstraint(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValuesAreNotPassedToStartMethod() {
        TimeConstraint constraint = new TimeConstraint(new Date(), new Date());
        constraint.start(null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testConstraintHasBeenStarted() {
        TimeConstraint constraint = new TimeConstraint(new Date(), new Date());
        constraint.streams();
    }
}
