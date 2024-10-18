/**
 * A FileHandler csomag főbb funkciói:
 *
 * <p>loadBoardFromFile: Egy adott fájlból beolvassa a játék állását,
 * és visszaad egy táblát (két dimenziós karakter tömböt).
 * Ha hiba történik a fájl olvasása közben, egy hibaüzenetet jelenít meg.</p>
 *
 * <p>saveBoardToFile: A játék aktuális állását menti el egy fájlba,
 * soronként írva ki a tábla tartalmát.
 * Hiba esetén itt is hibaüzenetet jelenít meg.</p>
 *
 * <p>Ez az osztály lehetővé teszi a játék
 * állapotának mentését és visszatöltését,
 * ami hasznos lehet, ha a játékot később szeretnénk folytatni.</p>
 */
package hu.nye.FileHandler;
