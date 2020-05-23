package symulacja.silnik.oddzialy;

import symulacja.Symulacja;
import symulacja.silnik.mapa.Mapa;
import symulacja.silnik.mapa.Pole;
import symulacja.silnik.obiekty.Obiekt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//Odpowiada za utworzenie listy Oddziałów, zachowań Oddziałów (Zwiad i Ruch) oraz
//warunków które pomagają Dowódcy podjąć decyzję


public abstract class Oddzial {

    private static final int STALA_ZYCIE = 10;
    private static final int STALA_ATAK = 10;
    private static final int STALA_OBRONA = 10;
    private static final int STALA_SILA = 5;

    int zycie;
    int atak;
    int obrona;
    int sila;

    protected final Typ typOddzialu;
    protected final Pole.Wspolrzedne wspolrzedne;

    Oddzial(final Pole.Wspolrzedne wspolrzedne, final Typ typOddzialu) {
        this.zycie = STALA_ZYCIE;
        this.atak = STALA_ATAK * zycie;
        this.obrona = STALA_OBRONA * zycie;
        this.sila = STALA_SILA;

        this.wspolrzedne = wspolrzedne;
        this.typOddzialu = typOddzialu;
    }

    public Pole.Wspolrzedne wspolrzedne() {
        return this.wspolrzedne;
    }

    enum Typ {

        PIECHOTA("P"),
        ZMOTORYZOWANY("Z");

        private String oddzialTyp;

        Typ(String oddzialTyp) {
            this.oddzialTyp = oddzialTyp;
        }
        @Override
        public String toString() {
            return this.oddzialTyp;
        }

    }


    public List<Ruch> obliczMozliweRuchy(final Mapa map) {
        return null;
    }

    public static List<Oddzial> utworzListeOddzialow(final List<Pole.Wspolrzedne> listaWspolrzednych, final List<Obiekt> listaObiektow, final int liczbaOddzialow) {
        List<Oddzial> listaOddzialow = new ArrayList<>();
        int[] indeksy = new int[liczbaOddzialow];
        for(int i = 0; i < liczbaOddzialow; i++) {
            indeksy[i] = ThreadLocalRandom.current().nextInt(0, listaWspolrzednych.size());
            for (Obiekt obiekt : listaObiektow) {
                if (obiekt.wspolrzedne() == listaWspolrzednych.get(indeksy[i])) {
                    i--;
                    break;
                }
            }
        }

        for(int i = 0; i < liczbaOddzialow; i++) {
            listaOddzialow.add(new OddzialPiechoty(listaWspolrzednych.get(indeksy[i]), Typ.PIECHOTA));
        }
        return listaOddzialow;
    }

    public static abstract class Zwiad {

        final Mapa mapa;
        final Oddzial oddzial;
        final List<Pole> zbadanePola;

        private Zwiad(final Mapa mapa, final Oddzial oddzial) {
            this.mapa = mapa;
            this.oddzial = oddzial;
            zbadanePola = zbadajPola(mapa, oddzial);
        }

        private List<Pole> zbadajPola(final Mapa mapa, final Oddzial oddzial) {
            final List<Pole> listaPol = mapa.listaPol;
            final List<Pole> listaZbadanychPol = new ArrayList<>();
            Pole poleOddzialu = null;
            boolean warunekX, warunekY;
            for(Pole pole: listaPol) {
                if(pole.odczytajOddzial() == oddzial) {
                    poleOddzialu = pole;
                    break;
                }
            }
            for(Pole pole : listaPol) {
                warunekX = false;
                warunekY = false;
                if(pole.wspolrzedne.czyPoprawneWspolrzedne(pole.wspolrzedne, Symulacja.odczytajSzerokosc(), Symulacja.odczytajWysokosc())) {
                    if(pole.wspolrzedne.x - 1 == poleOddzialu.wspolrzedne.x) warunekX = true;
                    if(pole.wspolrzedne.x == poleOddzialu.wspolrzedne.x) warunekX = true;
                    if(pole.wspolrzedne.x + 1 == poleOddzialu.wspolrzedne.x) warunekX = true;
                    if(pole.wspolrzedne.y - 1 == poleOddzialu.wspolrzedne.y) warunekY = true;
                    if(pole.wspolrzedne.y == poleOddzialu.wspolrzedne.y) warunekY = true;
                    if(pole.wspolrzedne.y + 1 == poleOddzialu.wspolrzedne.y) warunekY = true;
                    if(warunekX && warunekY) listaZbadanychPol.add(pole);
                }
            }
            return listaZbadanychPol;
        }

        public static boolean czySilniejszyPrzeciwnik(List<Pole> listaZbadanychPol, final Oddzial oddzial) {
            for(Pole pole : listaZbadanychPol) {
                if(pole.odczytajOddzial().atak > oddzial.obrona) return true;
            }
            return false;
        }

        public boolean czyOddzialBezpieczny(final Pole pole, final Oddzial oddzial) {
            if (pole.odczytajObiekt().odczytajTyp() == Obiekt.Typ.OBRONA) {
                return !czySilniejszyPrzeciwnik(zbadanePola, oddzial);
            } else return false;
        }
    }

    public static class Ruch {

        final Mapa mapa;
        final Oddzial poruszonyOddzial;
        final Pole.Wspolrzedne doceloweWspolrzedne;

        private Ruch(final Mapa mapa,
             final Oddzial oddzial,
             final Pole.Wspolrzedne doceloweWspolrzedne) {
            this.mapa = mapa;
            this.poruszonyOddzial = oddzial;
            this.doceloweWspolrzedne = doceloweWspolrzedne;
        }

        static final class Przemieszczenie extends Ruch {
            Przemieszczenie(Mapa mapa, Oddzial oddzial, Pole.Wspolrzedne doceloweWspolrzedne) {
                super(mapa, oddzial, doceloweWspolrzedne);
                poruszonyOddzial.sila--;
            }
        }

        static final class Przejecie extends Ruch {

            Obiekt docelowyObiekt;

            Przejecie(Mapa mapa, Oddzial poruszonyOddzial, Pole.Wspolrzedne doceloweWspolrzedne, Obiekt docelowyObiekt) {
                super(mapa, poruszonyOddzial, doceloweWspolrzedne);
                this.docelowyObiekt = docelowyObiekt;
                poruszonyOddzial.sila = poruszonyOddzial.sila - 2;
            }
        }

        static final class Atak extends Ruch {

            Oddzial zaatakowanyOddzial;

            Atak(Mapa mapa, Oddzial poruszonyOddzial, Pole.Wspolrzedne doceloweWspolrzedne, Oddzial zaatakowanyOddzial) {
                super(mapa, poruszonyOddzial, doceloweWspolrzedne);
                this.zaatakowanyOddzial = zaatakowanyOddzial;
                poruszonyOddzial.sila = poruszonyOddzial.sila - 2;
            }
        }


    }
}
