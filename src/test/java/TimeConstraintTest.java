import static org.junit.Assert.*;

import constraints.TimeConstraint;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TimeConstraintTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullValuesAreNotPassedAsParams() {
        new TimeConstraint(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValuesAreNotPassedToStartMethod() {
        TimeConstraint constraint = new TimeConstraint(new Date(), new Date());
        constraint.start(null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testThatConstraintHasBeenStartedBeforeUpdateMethodCalled() {
        TimeConstraint constraint = new TimeConstraint(new Date(), new Date());
        constraint.updates("lll");
    }

    @Test(expected = IllegalStateException.class)
    public void testConstraintHasBeenStartedBeforeReturnMethodCalled() {
        TimeConstraint constraint = new TimeConstraint(new Date(), new Date());
        constraint.returns();
    }

    @Test
    public void testTimeConstraintReturnsTrue() throws ParseException, ExecutionException, InterruptedException {
        SimpleDateFormat myFormatObj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date future;
        Date current_date;

        String current_date_format = "7-05-2020 03:59:55";
        String future_date_format = "7-05-2020 04:00:00";

        current_date = myFormatObj.parse(current_date_format);
        future = myFormatObj.parse(future_date_format);

        TimeConstraint timeConstraint = new TimeConstraint(current_date, future);
        timeConstraint.start(current_date, future);

        assertTrue(timeConstraint.returns().get());

    }

    @Test(expected = IllegalStateException.class)
    public void testFutureDateCannotBeBeforeCurrentDate() throws ParseException, ExecutionException, InterruptedException {
        SimpleDateFormat myFormatObj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date future;
        Date current_date;

        String current_date_format = "7-05-2020 03:59:55";
        String future_date_format = "7-05-2020 04:00:00";

        current_date = myFormatObj.parse(current_date_format);
        future = myFormatObj.parse(future_date_format);

        TimeConstraint timeConstraint = new TimeConstraint(future, current_date);
        timeConstraint.start(future, current_date);

        System.out.println(timeConstraint.returns().get());
    }
}
