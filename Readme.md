# Opis
Gra BrickBreaker wykonana przy użyciu JavaFX. Żeby odpalić kod:

`mvn clean compile javafx:run`

# Jak grać
Użyj strzałek na klawiaturzę by poruszać paletką. Strzałka w góre zaczyna rozgrywkę. Gdy nie uda ci się odbić piłeczki paletką stracisz życie. Celem gry jest rozbić jak najwięcej cegiełek (zyskać jak najwięcej punktów) nim stracisz wszystkie życia. Po zniszczeniu najniższego rzędu cegiełek, na szczycie pojawi się rząd nowych cegiełek, a stare zostaną zepchnięte w dół, więc rozgrywka nigdy się nie kończy :wink:. Po straceniu wszystkich żyć możemy zacząć grę od nowa. 

Jeżeli rozgrywka okazałaby się za trudna, po wciśnięciu klawisza ESC wchodzimy w tryb debugowania - wszystkie obiekty (za wyjątkiem paletki) zastygają w miejscu, a wciśnięcie strzałki w górę "wznawia czas" na jedną klatkę. Po ponownym wciśnięciu ESC wracamy do normalnej rozgrywki.


# Ulepszenia

| Kolor        | Efekt                         |
| :-----------:|:-----------------------------:|
| Różowy       | Dodatkowa piłeczka            |
| Niebieski    | Bonus 2000 punktów            |
| Brązowy      | Wydłużenie paletki            |
| Pomarańczowy | Zwiększenie prędkości paletki |
| Zielony      | Dodatkowe życie               |
