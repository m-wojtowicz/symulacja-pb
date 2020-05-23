package symulacja.silnik.obiekty;

import symulacja.silnik.mapa.Pole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.StrictMath.round;

public abstract class Obiekt {

    private static final int STALA_ZAGESZCZENIA = 20;
    protected final Pole.Wspolrzedne obiektPozycja;
    protected Typ obiektTyp;
    public boolean czyMoznaPrzejsc;

    public Pole.Wspolrzedne wspolrzedne() {
        return this.obiektPozycja;
    }

    public Typ odczytajTyp() {
        return this.obiektTyp;
    }

    public enum Typ {
        ATAK("A"),
        OBRONA("O"),
        WYPOSAZENIE("W"),
        TEREN("T");

        public String obiektTyp;

        Typ(String obiektTyp) {
            this.obiektTyp = obiektTyp;
        }

        @Override
        public String toString() {
            return this.obiektTyp;
        }
    }

    public static List<Obiekt> utworzListeObiektow(final List<Pole.Wspolrzedne> listaWspolrzednych, final int zageszczenie) {

        List<Obiekt> listaObiektow = new ArrayList<>();
        int buf = round(2 * zageszczenie *listaWspolrzednych.size() / STALA_ZAGESZCZENIA);
        int[] indeksy = new int[buf];
        for(int i = 0; i < buf; i++) {
            indeksy[i] = ThreadLocalRandom.current().nextInt(0, listaWspolrzednych.size());
        }
        for(int i = 0; i < buf; i++) {
            if ( i < 15 * buf / 100) {
                listaObiektow.add(i, new ObiektAtaku(listaWspolrzednych.get(indeksy[i])));
            } else {
                if ( i < 30 * buf / 100) {
                    listaObiektow.add(i, new ObiektObrony(listaWspolrzednych.get(indeksy[i])));
                } else {
                    if ( i < 50 * buf / 100) {
                        listaObiektow.add(i, new ObiektWyposazenia(listaWspolrzednych.get(indeksy[i])));
                    } else {
                        listaObiektow.add(i, new ObiektTerenu(listaWspolrzednych.get(indeksy[i])));
                    }
                }
            }
        }
        return listaObiektow;
    }

    Obiekt(final Pole.Wspolrzedne obiektPozycja) {
        this.obiektPozycja = obiektPozycja;
    }


}
