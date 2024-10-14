package hu.nye;
/*a Main osztály a Connect 4 játék főprogramját tartalmazza.
Főbb funkciói:

Játékos nevének bekérése: A program bekéri az első játékos (az emberi játékos) nevét a felhasználótól.

Játékosok létrehozása:
Két játékos jön létre:

Player 1: emberi játékos, akinek a nevét a felhasználó adja meg, és a sárga korongot használja ('S').
Player 2: gépi játékos (AI), aki a piros korongot használja ('P').
Játéktábla létrehozása: A program egy 7x6 méretű táblát hoz létre,
amely a Connect 4 alapjátékának tábla mérete.

Játék elindítása: A Game osztály segítségével elindítja a játékot,
 amely a két játékos és a tábla alapján zajlik. A játékosok felváltva lépnek,
 és a játék addig tart, amíg valaki nyer vagy döntetlen lesz.

A kód a játék alapvető futását irányítja a parancssoron keresztül.*/