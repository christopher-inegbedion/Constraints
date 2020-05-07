import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Demo {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat myFormatObj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date future;
        Date current_date;

        String current_date_format = "7-05-2020 03:59:55";
        String future_date_format = "7-05-2020 04:00:00";

        current_date = myFormatObj.parse(current_date_format);
        future = myFormatObj.parse(future_date_format);

        TimeConstraint timer_constraint = new TimeConstraint(current_date, future);
        timer_constraint.start(current_date, future);

        try {
            timer_constraint.streams();
            System.out.println(timer_constraint.returns().get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
