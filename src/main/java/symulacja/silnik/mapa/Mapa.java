package symulacja.silnik.mapa;


import symulacja.silnik.obiekty.*;
import symulacja.silnik.oddzialy.*;

import java.util.*;

public class Mapa {

    public final List<Pole> listaPol;

    //Trzymanie listę Pól - ich współrzędnych oraz Obiektów i Oddziałów
    //znajdujących się na nich. Będzie też zajmować się ruchami Oddziałów - sprawdzać, czy są
    //możliwe oraz co się na tych polach znajduje

    private Mapa(final List<Pole.Wspolrzedne> listaWspolrzednych, final List<Obiekt> listaObiektow, final List<Oddzial> listaOddzialow) {

        this.listaPol = Collections.unmodifiableList(utworzListePol(listaWspolrzednych, listaObiektow, listaOddzialow));

        //final Collection<Oddzial.Ruch> mozliweRuchy = obliczMozliweRuchy(this.listaOddzialow);


    }

    public Pole odczytajPole(Pole.Wspolrzedne wspolrzedne) {
        for(Pole pole : listaPol) {
            if (pole.wspolrzedne == wspolrzedne) return pole;
        }
        return null;
    }

    private static List<Pole> utworzListePol(List<Pole.Wspolrzedne> listaWspolrzednych,final List<Obiekt> listaObiektow,
                                             final List<Oddzial>listaOddzialow) {
        final List<Pole> listaPol = new ArrayList<>();
        boolean obiekty, oddzialy;
        int i, j, k;

        for(i = 0; i < listaWspolrzednych.size(); i++) {
            obiekty = false;
            oddzialy = false;

            for(j = 0; j < listaObiektow.size(); j++) {
                if(listaWspolrzednych.get(i) == listaObiektow.get(j).wspolrzedne()) {
                    obiekty = true;
                    break;
                }
            }
            for(k = 0; k < listaOddzialow.size(); k++) {
                if(listaWspolrzednych.get(i) == listaOddzialow.get(k).wspolrzedne()) {
                    oddzialy = true;
                    break;
                }
            }
            if(obiekty && !oddzialy) {
                listaPol.add(i, Pole.utworzPole(listaWspolrzednych.get(i), listaObiektow.get(j), null));
            }
            if(!obiekty && oddzialy) {
                listaPol.add(i, Pole.utworzPole(listaWspolrzednych.get(i), null, listaOddzialow.get(k)));
            }
            if(!obiekty && !oddzialy) {
                listaPol.add(i, Pole.utworzPole(listaWspolrzednych.get(i), null, null));
            }
        }
        return Collections.unmodifiableList(listaPol);
    }

    public static Mapa utworzPodstawowaMape(final List<Pole.Wspolrzedne> listaWspolrzednych, final List<Obiekt> listaObiektow, final List<Oddzial> listaOddzialow) {
        return new Mapa(listaWspolrzednych, listaObiektow, listaOddzialow);
    }

    private static List<Oddzial> policzZyjaceOddzialy(final List<Pole> listaPol) {
        final List<Oddzial> zyjaceOddzialy = new ArrayList<>();
        for(final Pole pole : listaPol) {
            if(pole.czyPoleZajete()) {
                final Oddzial oddzial = pole.odczytajOddzial();
                zyjaceOddzialy.add(oddzial);
            }
        }
        return Collections.unmodifiableList(zyjaceOddzialy);
    }

    private List<Oddzial.Ruch> obliczMozliweRuchy(final List<Oddzial> listaOddzialow) {
        final List<Oddzial.Ruch> mozliweRuchy = new ArrayList<>();
        for(final Oddzial oddzial : listaOddzialow) {
            mozliweRuchy.addAll(oddzial.obliczMozliweRuchy(this));
        }
        return Collections.unmodifiableList(mozliweRuchy);
    }

    public String toString(int szerokosc, int wysokosc) {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < szerokosc; i++) {
            for(int j = 0; j < wysokosc; j++) {
                final String poleTekst = this.listaPol.get(i * wysokosc + j).toString();
                builder.append(String.format("%3s", poleTekst));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
