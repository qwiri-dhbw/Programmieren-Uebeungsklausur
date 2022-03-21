package io.d2a.dhbw.probeklausur.obj;

import java.util.stream.Stream;

public class Board {

    private final Field[] fields;

    public Board() {
        this.fields = new Field[63];

        int i = 0;
        // dummy field
        this.fields[i++] = new Field("x", 0, false);

        // field values
        for (int j = 1; j <= 20; j++) {
            this.fields[i++] = new Field(String.valueOf(j), j, false);
            this.fields[i++] = new Field("D" + j, j * 2, true);
            this.fields[i++] = new Field("T" + j, j * 3, false);
        }

        // bullseye
        this.fields[i++] = new Field("25", 25, false);
        this.fields[i] = new Field("BULL", 25, true);
    }

    public Field parseField(final String inp) {
        return Stream.of(this.fields)
            .filter(f -> f.toString().equals(inp))
            .findFirst().orElse(null);
    }

    public Field[] getFields() {
        return fields;
    }
}
