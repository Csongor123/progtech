package hu.nye.player;

public class Player {
    private final String name;
    private final char disc;  // Ezt korábban 'symbol'-nak hívtuk

    public Player(String name, char disc) {
        this.name = name;
        this.disc = disc;  // Itt is az új 'disc' változót használjuk
    }

    public String getName() {
        return name;
    }

    public char getDisc() {  // A 'getSymbol()' most 'getDisc()' lett
        return disc;  // Visszaadja a korong karakterét
    }
}
