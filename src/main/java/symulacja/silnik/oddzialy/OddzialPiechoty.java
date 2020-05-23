package symulacja.silnik.oddzialy;

import symulacja.silnik.mapa.Mapa;
import symulacja.silnik.mapa.Pole;
import symulacja.silnik.obiekty.Obiekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OddzialPiechoty extends Oddzial {

    private final static Pole.Wspolrzedne[] WSPOLRZEDNE_MOZLIWEGO_RUCHU = {
            new Pole.Wspolrzedne(-1, -1),
            new Pole.Wspolrzedne(-1, 0),
            new Pole.Wspolrzedne(-1, 1),
            new Pole.Wspolrzedne(0, -1),
            new Pole.Wspolrzedne(0, 0),
            new Pole.Wspolrzedne(0, 1),
            new Pole.Wspolrzedne(1, -1),
            new Pole.Wspolrzedne(1, 0),
            new Pole.Wspolrzedne(1, 1)
    };

    private static final int SILA_MAX = 10;

    public OddzialPiechoty(Pole.Wspolrzedne wspolrzedne, Typ oddzialTyp) {
        super(wspolrzedne, oddzialTyp);
    }

    int szerokosc;
    int wysokosc;

    void Odpoczynek(Oddzial oddzial){
        if(this.sila == 9) {
            this.sila++;
        }
        if(this.sila != 10) {
            this.sila = this.sila + 2;
        }
    }

    void Obrona(Oddzial oddzial){
        Odpoczynek(oddzial);
    }

    @Override
    public List<Ruch> obliczMozliweRuchy(final Mapa mapa) {
        Pole.Wspolrzedne mozliweDoceloweWspolrzedne = new Pole.Wspolrzedne(0,0);
        final List<Ruch> mozliweRuchy = new ArrayList<>();
        if (this.sila == 0) {
            Odpoczynek(this);
        } else {
            for(final Pole.Wspolrzedne aktualneMozliwosci : WSPOLRZEDNE_MOZLIWEGO_RUCHU) {
                mozliweDoceloweWspolrzedne.x = this.wspolrzedne.x + aktualneMozliwosci.x;
                mozliweDoceloweWspolrzedne.y = this.wspolrzedne.y + aktualneMozliwosci.y;
                if(Pole.Wspolrzedne.czyPoprawneWspolrzedne(mozliweDoceloweWspolrzedne, szerokosc, wysokosc)) {
                    final Pole mozliweDocelowePole = mapa.odczytajPole(mozliweDoceloweWspolrzedne);
                    if (!mozliweDocelowePole.czyPoleZajete()) {
                        mozliweRuchy.add(new Ruch.Przemieszczenie(mapa, this, mozliweDoceloweWspolrzedne));
                    } else {
                        final Oddzial oddzialNaPoluDocelowym = mozliweDocelowePole.odczytajOddzial();
                        final Obiekt obiektNaPoluDocelowym = mozliweDocelowePole.odczytajObiekt();
                        if(obiektNaPoluDocelowym != null && obiektNaPoluDocelowym.czyMoznaPrzejsc) {
                            if (oddzialNaPoluDocelowym == null) {
                                mozliweRuchy.add(new Ruch.Przejecie(mapa, this, mozliweDoceloweWspolrzedne, obiektNaPoluDocelowym));
                            } else {
                                if (oddzialNaPoluDocelowym.obrona < this.atak) {
                                    mozliweRuchy.add(new Ruch.Atak(mapa, this, mozliweDoceloweWspolrzedne, oddzialNaPoluDocelowym));
                                }
                            }

                        } else {
                            mozliweRuchy.add(new Ruch.Przemieszczenie(mapa, this, mozliweDoceloweWspolrzedne));
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(mozliweRuchy);
    }

    @Override
    public String toString() {
        return Typ.PIECHOTA.toString();
    }

}
