import constraints.CombinedConstraint;
import constraints.ReadyConstraint;
import constraints.TimeConstraint;
import stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import static enums.CombinationTypes.*;

public class Demo {

    public static void main(String[] args) throws ParseException, ExecutionException, InterruptedException {
        SimpleDateFormat myFormatObj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date future;
        Date current_date;

        String current_date_format = "7-05-2020 03:59:50";
        String future_date_format = "7-05-2020 04:00:00";

        current_date = myFormatObj.parse(current_date_format);
        future = myFormatObj.parse(future_date_format);

        TimeConstraint timer_constraint = new TimeConstraint(current_date, future);
        ReadyConstraint readyConstraint = new ReadyConstraint(false);
        ReadyConstraint readyConstraint2 = new ReadyConstraint(true);
/*
        CombinedConstraint combinedConstraint = new CombinedConstraint(timer_constraint, readyConstraint, OR.getCombination());
        combinedConstraint.start(combinedConstraint.combined_result);

        CombinedConstraint combinedConstraint1 = new CombinedConstraint(readyConstraint2, AND.getCombination());
        try {
            combinedConstraint1.hasCombinedConstraint(combinedConstraint.returns().get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        combinedConstraint1.start(combinedConstraint1.combined_result);
        try {
            System.out.println(combinedConstraint.returns().get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/

        Stage stage = new Stage("Test");
        stage.addConstraint(timer_constraint);
        stage.addConstraint(readyConstraint);
        stage.addConstraint(readyConstraint2);

        stage.start();

        /*SimpleDateFormat myFormatObj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date future;
        Date current_date;

        String current_date_format = "7-05-2020 03:59:50";
        String future_date_format = "7-05-2020 04:00:00";

        current_date = myFormatObj.parse(current_date_format);
        future = myFormatObj.parse(future_date_format);

        constraints.TimeConstraint timeConstraint = new constraints.TimeConstraint(current_date, future);
        timeConstraint.start(current_date, future);
        timeConstraint.updates("waiting");*/
    }
}
