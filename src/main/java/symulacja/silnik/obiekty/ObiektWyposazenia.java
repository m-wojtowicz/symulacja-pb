package symulacja.silnik.obiekty;

import symulacja.silnik.mapa.Pole;

public class ObiektWyposazenia extends Obiekt {

    //Zwiększenie jednorazowo "życia" pierwszego Oddziału który wejdzie w interakcję

    public ObiektWyposazenia(Pole.Wspolrzedne obiektPozycja) {
        super(obiektPozycja);
        Obiekt.Typ obiektTyp = Typ.WYPOSAZENIE;
        this.czyMoznaPrzejsc = true;
        int wartoscZasobow = 0;
    }

    @Override
    public String toString() {
        return Typ.WYPOSAZENIE.toString();
    }
}
