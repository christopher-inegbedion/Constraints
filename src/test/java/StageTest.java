import constraints.ReadyConstraint;
import constraints.TimeConstraint;
import enums.ConstraintStatus;
import org.junit.Test;
import stage.Stage;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class StageTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThatStringIsPassedAsConstructorParam() {
        new Stage("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatNullIsNotPassedToAddMethod() {
        Stage stage = new Stage("Test");
        stage.addConstraint(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThatIncorrectIndexCannotBePassedToChangeConstraintStatus() {
        Stage stage = new Stage("Test");
        stage.addConstraint(new TimeConstraint(new Date(), new Date()));
        stage.changeConstraintStatus(-1, ConstraintStatus.IDLE);
    }

    @Test(expected = IllegalStateException.class)
    public void testThatThereAreConstraintsBeforeStartingStage() throws ExecutionException, InterruptedException {
        Stage stage = new Stage("Test");
        stage.start();
    }

    /*@Deprecated
    @Test(expected = IllegalStateException.class)
    public void testThatConstraintCannotBeAddedAfterStageIsStarted() throws ExecutionException, InterruptedException {
        Stage stage = new Stage("Test");
        stage.addConstraint(new ReadyConstraint(false));
        stage.start();
        stage.addConstraint(new ReadyConstraint(true));
    }*/
}
