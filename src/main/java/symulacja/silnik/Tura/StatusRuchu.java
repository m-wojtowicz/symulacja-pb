package symulacja.silnik.Tura;

public enum StatusRuchu {

    //Przekazanie informacji o możliwości/niemożliwości przeprowadzenia ruchu

    RUCH_WYKONANY {
        @Override
        boolean ruchWykonany() {
            return true;
        }
    },
    RUCH_NIEMOZLIWY {
        @Override
        boolean ruchWykonany() {
            return false;
        }
    };
    abstract boolean ruchWykonany();
}
