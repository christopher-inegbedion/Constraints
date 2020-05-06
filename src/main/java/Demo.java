import java.util.Date;

public class Demo {

    public static void main(String[] args) {
        TimeConstraint timer_constraint = new TimeConstraint(new Date(), new Date());
        timer_constraint.streams();
    }
}
