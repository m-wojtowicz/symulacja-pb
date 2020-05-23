package symulacja.silnik.obiekty;

import symulacja.silnik.mapa.Pole;

public class ObiektAtaku extends Obiekt {

    //Permamentne zwiększenie o x% ataku Oddziału który pierwszy wejdzie w interakcję

    public ObiektAtaku(Pole.Wspolrzedne obiektPozycja) {
        super(obiektPozycja);
        Obiekt.Typ obiektTyp = Typ.ATAK;
        boolean czyMoznaPrzejsc = true;
        int wartoscAtaku = 0;
    }

    @Override
    public String toString() {
        return Typ.ATAK.toString();
    }

}
