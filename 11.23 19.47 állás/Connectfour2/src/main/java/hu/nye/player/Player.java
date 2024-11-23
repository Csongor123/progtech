package hu.nye.player;

/**
 * A Player osztály egy játékost képvisel, akinek van neve és egy korongja.
 * A végleges osztály megakadályozza a kiterjesztést.
 */
public final class Player {
    /**
     * A játékos neve.
     */
    private final String name;

    /**
     * A játékos korongjának karaktere (pl. 'S' vagy 'P').
     */
    private final char disc;

    /**
     * Létrehozza a játékost a megadott névvel és koronggal.
     *
     * @param playerName a játékos neve
     * @param playerDisc a játékos korongjának karaktere
     */
    public Player(final String playerName, final char playerDisc) {
        this.name = playerName;
        this.disc = playerDisc;
    }

    /**
     * Visszaadja a játékos nevét.
     *
     * @return a játékos neve
     */
    public String getName() {
        return this.name;
    }

    /**
     * Visszaadja a játékos korongjának karakterét.
     *
     * @return a játékos korongjának karaktere
     */
    public char getDisc() {
        return this.disc;
    }
}
