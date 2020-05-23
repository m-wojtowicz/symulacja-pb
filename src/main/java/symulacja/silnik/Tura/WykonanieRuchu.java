package symulacja.silnik.Tura;

import symulacja.silnik.mapa.Mapa;
import symulacja.silnik.oddzialy.Oddzial;

//Generowanie nowej mapy za każdym razem kiedy zostanie wykonany ruch oraz pozbycie się starej

public class WykonanieRuchu {

    private final Mapa mapaPoRuchu;
    private final Oddzial.Ruch ruch;
    private final StatusRuchu statusRuchu;

    public WykonanieRuchu(final Mapa mapaPoRuchu, final Oddzial.Ruch ruch, final StatusRuchu statusRuchu) {
        this.mapaPoRuchu = mapaPoRuchu;
        this.ruch = ruch;
        this.statusRuchu = statusRuchu;
    }


}
