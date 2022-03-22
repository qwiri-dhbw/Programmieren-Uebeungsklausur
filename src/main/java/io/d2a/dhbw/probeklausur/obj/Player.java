package io.d2a.dhbw.probeklausur.obj;

import java.util.Objects;
import java.util.stream.Stream;

public class Player {

    public static final int MAX_POINTS = 501;
    public static final boolean DOUBLE_OUT = true;

    private final String name;
    private final Visit[] visits = new Visit[10];

    private int visitIndex = 0;
    private int countDartsThrown;

    public Player(final String name) {
        this.name = name;
    }

    public int getRemainingPoints() {
        return MAX_POINTS - Stream.of(this.visits)
            .filter(Objects::nonNull)
            .mapToInt(Visit::getValue)
            .sum();
    }

    public boolean addVisit(final Visit visit) {
        this.countDartsThrown += visit.getFields().length;

        final int value = visit.getValue();
        final int remaining = this.getRemainingPoints();
        if (remaining < value) {
            return false;
        }

        if (Player.DOUBLE_OUT) {
            // allow only double fields at the end of the game
            if (remaining == value) {
                if (visit.getLastField() != null && !visit.getLastField().isDoubleField()) {
                    return false;
                }
            }
            if (remaining - value == 1) {
                return false;
            }
        }

        this.visits[this.visitIndex++] = visit;
        return true;
    }

    public String getName() {
        return name;
    }

    public Visit[] getVisits() {
        return visits;
    }

    public int getCountDartsThrown() {
        return countDartsThrown;
    }
}
