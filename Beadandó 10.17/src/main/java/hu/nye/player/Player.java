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
     * A játékos korongjának karaktere (pl. 'X' vagy 'O').
     */
    private final char disc;

    /**
     * Létrehozza a játékost a megadott névvel és koronggal.
     *
     * @param name  a játékos neve
     * @param disc  a játékos korongjának karaktere
     */
    public Player(final String name, final char disc) {
        this.name = name;
        this.disc = disc;
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
