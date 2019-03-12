package ConnectFour.Logic;

import javafx.geometry.Pos;

public class Disc {
    private Point placement;
    private boolean isOwnedByPlayer;

    public Disc(final boolean isOwnedByPlayer) {
        this.isOwnedByPlayer = isOwnedByPlayer;
    }

    public Disc(Disc disc) {
        this.isOwnedByPlayer = disc.isOwnedByPlayer();
    }

    public boolean isOwnedByPlayer() {
        return isOwnedByPlayer;
    }
}
