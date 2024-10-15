package hu.nye.game;
/* Főbb funkciói:

Játék betöltése fájlból: A játék elején a felhasználó eldöntheti,
hogy egy fájlból kívánja-e betölteni a játékot.
Ha igen, akkor a megadott fájlból beolvassa a játéktáblát.

Játék lejátszása: A játék két játékos között zajlik, ahol az egyik emberi,
a másik pedig egy AI. Az emberi játékos manuálisan adja meg az oszlop számát,
ahova dobni szeretné a korongját, míg az AI véletlenszerűen választ oszlopot.
A lépések érvényességét ellenőrzi, és ha egy lépés nem érvényes, újra kéri a játékostól.

Győzelem és döntetlen ellenőrzése: Minden lépés után ellenőrzi, hogy a lépést végrehajtó játékos nyert-e
(azaz négy egymást követő korongot helyezett el bármely irányban),
vagy hogy a tábla megtelt-e, ami döntetlent eredményez.

Játék mentése: Minden lépés után lehetőséget biztosít a
játék mentésére egy fájlba, hogy később onnan folytatható legyen.

AI lépés generálása: A gépi játékos egy véletlenszerűen kiválasztott
oszlopba dobja a korongját, amíg talál üres oszlopot.*/