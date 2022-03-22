package io.d2a.dhbw.probeklausur.obj;


import io.d2a.eeee.PromptFactory;
import io.d2a.eeee.annotation.annotations.common.Range;
import io.d2a.eeee.annotation.annotations.prompt.Default;
import io.d2a.eeee.annotation.annotations.prompt.Prompt;
import io.d2a.eeee.annotation.annotations.prompt.Split;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Game {

    public interface Wiz {

        @Prompt("Eingabe")
        @Split(" ")
        @Default("T20 T20 T20")
        @Range({1, 3})
        String[] read();

    }

    private final Board board;

    private final Player[] players;

    private final List<String> checkouts;

    public Game(final Board board, final Player... players) throws IOException {
        this.board = board;
        this.players = players;

        // read checkouts
        this.checkouts = Files.readAllLines(new File("checkouts.txt").toPath());
    }

    public void onWin(final Player player) throws IOException {
        System.out.printf("🎉 %s hat das Spiel gewonnen!%n", player.getName());

        // write win to file
        final File highscoreFile = new File("highscores.txt");
        try (final FileWriter writer = new FileWriter(highscoreFile, true)) {
            writer.write(String.format("%s won with %d darts.%n",
                player.getName(), player.getCountDartsThrown())
            );
        }
    }

    public void start() {
        int currentPlayer = 0;

        final Wiz wiz = PromptFactory.build(Wiz.class);

        while (Stream.of(this.players)
            .mapToInt(Player::getRemainingPoints)
            .filter(i -> i <= 0)
            .findAny().isEmpty()) {

            final Player player = this.players[currentPlayer++ % this.players.length];
            if (Stream.of(player.getVisits()).noneMatch(Objects::isNull)) {
                System.out.println("🦑 Ihr seid zu schlecht :)");
                break;
            }

            // print name and remaining points
            System.out.printf("%n👋 %s ist dran. Dem Spieler fehlen %d Punkte zum Sieg.%n",
                player.getName(), player.getRemainingPoints());

            // parse fields from values
            final Field[] fields = Stream.of(wiz.read())
                .map(this.board::parseField)
                .filter(Objects::nonNull)
                .toArray(Field[]::new);

            // add throws to player
            if (!player.addVisit(new Visit(fields))) {
                System.out.println("🥊 Dieser Wurf wurde nicht angenommen.");
                continue;
            }

            final int remaining = player.getRemainingPoints();

            if (remaining <= 0) {
                try {
                    this.onWin(player);
                } catch (IOException e) {
                    System.out.println("Cannot write to high scores:");
                    e.printStackTrace();
                }
            } else if (remaining <= 170) {
                final String checkout = this.checkouts.size() >= remaining
                    ? this.checkouts.get(remaining - 1)
                    : "-";

                System.out.printf("📦 Checkout für %d Punkte: %s%n", remaining, checkout);
            }

        }

    }

}
