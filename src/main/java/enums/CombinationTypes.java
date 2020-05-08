package enums;

public enum CombinationTypes {
    AND(0),
    OR(1);

    int combination;

    CombinationTypes(int combination) {
        this.combination = combination;
    }

    public int getCombination() {
        return combination;
    }
}
