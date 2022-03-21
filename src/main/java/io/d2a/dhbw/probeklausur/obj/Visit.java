package io.d2a.dhbw.probeklausur.obj;

import java.util.stream.Stream;

public class Visit {

    private final Field[] fields;

    public Visit(final Field... fields) {
        if (fields.length > 3) {
            throw new IllegalArgumentException("too many throws");
        }
        this.fields = fields;
    }

    public Field[] getFields() {
        return fields;
    }

    public int getValue() {
        return Stream.of(this.fields)
            .mapToInt(Field::getValue)
            .sum();
    }

    public Field getLastField() {
        Field res = null;
        for (final Field field : this.fields) {
            if (field != null) {
                res = field;
            }
        }
        return res;
    }

}
