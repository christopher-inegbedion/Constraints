import java.util.concurrent.Future;

public class ReadyConstraint extends Constraint<Boolean> {
    public ReadyConstraint(Boolean requirement) {
        super(requirement);
    }

    @Override
    public Future<Boolean> returns() {
        return null;
    }
}
