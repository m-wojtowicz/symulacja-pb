package symulacja.silnik.obiekty;

import symulacja.silnik.mapa.Pole;

public class ObiektTerenu extends Obiekt{

    //Ograniczenie terenu dostępnego dla Oddziałów

    public ObiektTerenu(Pole.Wspolrzedne obiektPozycja) {
        super(obiektPozycja);
        Obiekt.Typ obiektTyp = Typ.TEREN;
        boolean czyMoznaPrzejsc = false;
    }

    @Override
    public String toString() {
        return Typ.TEREN.toString();
    }
}
