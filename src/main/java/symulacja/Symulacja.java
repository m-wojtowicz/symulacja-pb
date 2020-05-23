package symulacja;

import symulacja.gui.Plansza;
import symulacja.silnik.mapa.Pole;
import symulacja.silnik.obiekty.Obiekt;
import symulacja.silnik.oddzialy.Oddzial;

import java.util.List;

public class Symulacja {

    static int szerokosc = 15;
    static int wysokosc = 15;
    static int zageszczenie = 5;
    static int liczbaOddzialow = 5;

    //Pełni rolę bazy danych, trzymając listy Współrzędnych, Obiektów i Oddziałów.
    //Odpowiada za samo uruchomienie symulacji.

    public static void main(String[] args){

        if (PlikKonfiguracyjny.czyIstnieje() == true) {
            szerokosc = PlikKonfiguracyjny.odczytajWartosc("szerokosc");
            wysokosc = PlikKonfiguracyjny.odczytajWartosc("wysokosc");
            zageszczenie = PlikKonfiguracyjny.odczytajWartosc("zageszczenie");
            liczbaOddzialow = PlikKonfiguracyjny.odczytajWartosc("liczba-oddzialow");
        } else {
            PlikKonfiguracyjny.stworzKonfiguracje();
        }

        //Tworzenie podstawowych list dla dzialania symulacji
        final List<Pole.Wspolrzedne> listaWspolrzednych = Pole.Wspolrzedne.utworzListeWspolrzednych(szerokosc, wysokosc);
        final List<Obiekt> listaObiektow = Obiekt.utworzListeObiektow(listaWspolrzednych, zageszczenie);
        final List<Oddzial> listaOddzialow = Oddzial.utworzListeOddzialow(listaWspolrzednych, listaObiektow, liczbaOddzialow);
        //final List<Dowodca> listaDowodcow = Dowodca.utworzListeDowodzcow();

        //Wygenerowanie mapy i wyświetlenie jej
        Plansza plansza = new Plansza(szerokosc, wysokosc, listaWspolrzednych, listaObiektow, listaOddzialow);



    }

    public static int odczytajSzerokosc() {
        return szerokosc;
    }
    public static int odczytajWysokosc() {
        return wysokosc;
    }

}
