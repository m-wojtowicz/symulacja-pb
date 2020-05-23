package symulacja.gui;

import symulacja.silnik.mapa.Mapa;
import symulacja.silnik.mapa.Pole;
import symulacja.silnik.obiekty.Obiekt;
import symulacja.silnik.oddzialy.Oddzial;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Plansza {

    private final JFrame plansza;
    private final MapaPanel mapaPanel;
    private final Mapa mapa;

    private final Color jasnyKolorPola = Color.decode("#228B22");
    private final Color ciemnyKolorPola = Color.decode("#006400");

    private final static Dimension ROZMIAR_POLA = new Dimension(10, 10);


    private static String sciezkaIkon = "ikony/";


    public Plansza(final int szerokosc, final int wysokosc, final List<Pole.Wspolrzedne> listaWspolrzednych,
                   final List<Obiekt> listaObiektow, final List<Oddzial> listaOddzialow) {

        //Graficzna reprezentacja Symulacji


        this.plansza = new JFrame("Symulacja");
        this.plansza.setLayout(new BorderLayout());
        final JMenuBar planszaPasekMenu = PasekMenu.utworzPasekMenu();
        this.plansza.setJMenuBar(planszaPasekMenu);
        this.plansza.setSize(new Dimension(szerokosc*50,  wysokosc*50));

        this.mapa = Mapa.utworzPodstawowaMape(listaWspolrzednych, listaObiektow, listaOddzialow);

        this.mapaPanel = new MapaPanel(szerokosc, wysokosc, listaWspolrzednych);
        this.plansza.add(this.mapaPanel,  BorderLayout.CENTER);
        this.plansza.setLocationRelativeTo(null);
        this.plansza.setVisible(true);
        this.plansza.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }



    private class MapaPanel extends JPanel {

        //Wyrysowanie siatki pól

        final List<PolePanel> polaNaMapie;

        MapaPanel(final int szerokosc, final int wysokosc, final List<Pole.Wspolrzedne> listaWspolrzednych) {
            super(new GridLayout(szerokosc, wysokosc));
            this.polaNaMapie = new ArrayList<>();
            for (int i = 0; i < szerokosc * wysokosc; i++) {
                final PolePanel polePanel = new PolePanel(this, listaWspolrzednych.get(i), wysokosc);
                this.polaNaMapie.add(polePanel);
                add(polePanel);
            }
            setPreferredSize(new Dimension(szerokosc, wysokosc));
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            validate();
        }
    }

    private class PolePanel extends JPanel {

        //Wyrysowanie jednego pola

        private Pole.Wspolrzedne wspolrzedne;

        PolePanel(final MapaPanel mapaPanel, final Pole.Wspolrzedne wspolrzedne, final int szerokosc) {
            super(new GridBagLayout());
            this.wspolrzedne = wspolrzedne;
            setPreferredSize(ROZMIAR_POLA);
            przypiszKolorPolu();
            przypiszObiektowiIkone(mapa);
            przypiszOddzialowiIkone(mapa);
            validate();
        }

        private void przypiszObiektowiIkone(final Mapa mapa) {
            this.removeAll();
            if(mapa.odczytajPole(this.wspolrzedne).odczytajObiekt() != null) {
                try {
                    final BufferedImage obrazek =
                            ImageIO.read(new File(sciezkaIkon +
                                    mapa.odczytajPole(this.wspolrzedne).odczytajObiekt().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(obrazek)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void przypiszOddzialowiIkone(final Mapa mapa) {
            if(mapa.odczytajPole(this.wspolrzedne).odczytajOddzial() != null) {
                try {
                    final BufferedImage obrazek =
                            ImageIO.read(new File(sciezkaIkon +
                                    mapa.odczytajPole(this.wspolrzedne).odczytajOddzial().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(obrazek)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void przypiszKolorPolu() {
            if ((wspolrzedne.x % 2 == 0 && wspolrzedne.y % 2 == 0) || (wspolrzedne.x % 2 != 0 && wspolrzedne.y % 2 != 0)) setBackground(jasnyKolorPola);
            else setBackground(ciemnyKolorPola);
        }
    }
}
