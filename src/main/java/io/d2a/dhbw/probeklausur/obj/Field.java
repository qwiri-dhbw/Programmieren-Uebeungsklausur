package io.d2a.dhbw.probeklausur.obj;

public class Field {

    private final String label;
    private final int value;
    private final boolean doubleField;

    public Field(final String label, final int value, final boolean doubleField) {
        this.label = label;
        this.value = value;
        this.doubleField = doubleField;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public int getValue() {
        return value;
    }

    public boolean isDoubleField() {
        return doubleField;
    }
}
