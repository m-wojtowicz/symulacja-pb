package symulacja.silnik.obiekty;

import symulacja.silnik.mapa.Pole;

public class ObiektObrony extends Obiekt {

    //Zwiększenie obrony Oddziału który znajduje się na tym samym polu

    public ObiektObrony(Pole.Wspolrzedne obiektPozycja) {
        super(obiektPozycja);
        Obiekt.Typ obiektTyp = Typ.OBRONA;
        int wartoscObrony = 0;
        boolean czyMoznaPrzejsc = true;
    }

    @Override
    public String toString() {
        return Typ.OBRONA.toString();
    }
}
