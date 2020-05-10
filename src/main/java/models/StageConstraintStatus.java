package models;

import enums.ConstraintStatus;

public class StageConstraintStatus {
    private String constraint_tag;
    private ConstraintStatus status;

    public StageConstraintStatus(String constraint_tag, ConstraintStatus status) {
        this.constraint_tag = constraint_tag;
        this.status = status;
    }

    public String getConstraintTag() {
        return constraint_tag;
    }

    public void setConstraintTag(String constraint_tag) {
        this.constraint_tag = constraint_tag;
    }

    public ConstraintStatus getStatus() {
        return status;
    }

    public void setStatus(ConstraintStatus status) {
        this.status = status;
    }
}
