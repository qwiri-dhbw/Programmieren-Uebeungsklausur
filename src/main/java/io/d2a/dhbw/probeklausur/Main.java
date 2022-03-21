package io.d2a.dhbw.probeklausur;

import io.d2a.dhbw.probeklausur.obj.Board;
import io.d2a.dhbw.probeklausur.obj.Game;
import io.d2a.dhbw.probeklausur.obj.Player;

public class Main {

    public static void main(final String[] args) throws Exception {
        final Board board = new Board();
        final Game game = new Game(board,
            new Player("Daniel"),
            new Player("Tobias")
        );
        game.start();
    }

}
