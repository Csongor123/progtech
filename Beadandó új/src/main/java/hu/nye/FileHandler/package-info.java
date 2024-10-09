/*A FileHandler osztály két funkciót lát el a játék tábla adatainak fájlkezelésében:

loadBoardFromFile: Betölti a játéktáblát egy fájlból.
A megadott fájl nevét használva soronként beolvassa a tábla elemeit,
és egy karaktertömbbe (2D tömb) helyezi azokat.
Ha probléma lép fel a fájl olvasása során, hibaüzenetet jelenít meg.

saveBoardToFile: Elmenti a játéktáblát egy fájlba.
A megadott karaktertömböt soronként kiírja a fájlba.
Ha írás közben hiba történik, hibaüzenetet jelenít meg.

Ez az osztály segít a játékállapot fájlba mentésében és visszatöltésében.*/
package hu.nye.FileHandler;
