package symulacja;

import java.io.*;
import java.util.Properties;

public class PlikKonfiguracyjny {

    //Służy do utworzenia pliku konfiguracyjnego "config.properties",
    //zmian wartości w nim oraz odczytywanie ich.

    static boolean czyIstnieje() {
        File config = new File("zasoby/config.properties");
        return config.exists();
    }

    static Properties stworzKonfiguracje() {
        try (OutputStream wyjscie = new FileOutputStream("zasoby/config.properties")) {

            Properties config = new Properties();

            config.setProperty("szerokosc", "15");
            config.setProperty("wysokosc", "15");
            config.setProperty("zageszczenie", "5");
            config.setProperty("liczba-oddzialow", "5");

            config.store(wyjscie, null);
            return config;


        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }

    public static int odczytajWartosc(String wartosc) {
        FileInputStream wejscie;
        Properties config;
        int wynik = 0;
        try {
            wejscie = new FileInputStream("zasoby/config.properties");
            config = new Properties();
            config.load(wejscie);
            wynik = Integer.parseInt(config.getProperty(wartosc));
            wejscie.close();
            return wynik;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return wynik;
    }

    public static void zmienWartosc(String wartosc, String wynik) {
        FileInputStream wejscie;
        FileOutputStream wyjscie;
        Properties config = new Properties();
        try {
            wejscie = new FileInputStream("zasoby/config.properties");
            config.load(wejscie);
            wejscie.close();

            wyjscie = new FileOutputStream("zasoby/config.properties");
            config.setProperty(wartosc, wynik);
            config.store(wyjscie, "Zmieniono konfiguracje");
            wyjscie.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
