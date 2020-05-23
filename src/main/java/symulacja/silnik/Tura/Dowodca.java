package symulacja.silnik.Tura;

import symulacja.silnik.mapa.Mapa;
import symulacja.silnik.oddzialy.Oddzial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Dowodca {

    protected final Mapa mapa;
    protected Oddzial oddzial;
    protected final Collection<Oddzial.Ruch> mozliweRuchy;

    //Dowódca ma odpowiadać za "inteligencję" oddziałów -
    // podejmuje decyzje co do następnych ruchów
    public Dowodca(final Mapa mapa, final Oddzial oddzial, final Collection<Oddzial.Ruch> mozliweRuchy) {
        this.mapa = mapa;
        this.oddzial = null;
        this.mozliweRuchy = mozliweRuchy;
    }

    public static List<Dowodca> utworzListeDowodzcow(final Mapa mapa, final List<Oddzial> listaOddzialow) {
        List<Dowodca> listaDowodzcow = new ArrayList<>();
        for(int i = 0; i < listaOddzialow.size(); i++) {
            listaDowodzcow.add(i, new Dowodca(mapa, listaOddzialow.get(i),null));
        }
        return listaDowodzcow;
    }

    public boolean czyRuchMozliwy(final Oddzial.Ruch ruch) {
        return this.mozliweRuchy.contains(ruch);
    }

    public boolean czyOstatniOddzial() {
        return false;
    }

    public Oddzial odczytajOddzial() {
        return this.oddzial;
    }


    public WykonanieRuchu wykonajRuch(final Oddzial.Ruch ruch) {

        if(!czyRuchMozliwy(ruch)) {
            return new WykonanieRuchu(this.mapa, ruch, StatusRuchu.RUCH_NIEMOZLIWY);
        }

//        final Mapa mapaPoRuchu = ruch.wykonaj();

//        return new WykonanieRuchu(mapaPoRuchu, ruch, StatusRuchu.RUCH_WYKONANY);
        return null;
    }


}
