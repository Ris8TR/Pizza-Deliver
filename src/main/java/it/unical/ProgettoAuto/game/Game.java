package it.unical.ProgettoAuto.game;
import it.unical.ProgettoAuto.Main;
import it.unical.ProgettoAuto.config.Settings;
import it.unical.ProgettoAuto.controller.GameController;
import it.unical.ProgettoAuto.view.SceneHandler;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Game extends JPanel implements ActionListener {
    ArrayList<Rectangle> Arcars;
    ArrayList<Rectangle> Arcars2;
    ArrayList<Rectangle> Linee;
    ArrayList<Rectangle> tree;
    ArrayList<Rectangle> BeachEl;
    Clip clip = AudioSystem.getClip();
    Clip clip1 = AudioSystem.getClip();
    private final Random rand;
    private final Rectangle Player;
    private final int spawn;
    private final int record = SceneHandler.getInstance().getHighScore();
    private final int move = 50;
    private int count = 0;
    private int speed;
    private int speedUp = 0;
    private int animationC = 0;
    private boolean GameOver = false;
    private boolean Pause = false;
    private boolean flame = true;
    private final BufferedImage carversus;
    private final BufferedImage caropposit;
    private final BufferedImage grass;
    private final BufferedImage sand;
    private final BufferedImage beachEl;
    private final BufferedImage Tree;
    private final Image[] player = new Image[2];
    private final Image[] sea = new Image[2];
    static Timer time;


    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter pw = null;



    public Game() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException, FontFormatException {
        Random rand2 = new Random();
        rand = new Random();
        spawn = rand2.nextInt(4) + 1;
        int spawn2 = rand.nextInt(4) + 1;

        //scelgo casualmente il colore delle auto avversarie e mi assicuro che non siano gli stessi per entrambe le corsie
        if (spawn == spawn2 && spawn != 1) {
            spawn2 = 1;
        }
        if (spawn == spawn2) {
            spawn2 = 2;
        }

        //carico le immagini
        URL path = Main.class.getResource("Images/player1.png");
        assert path != null;
        player[0] = ImageIO.read(path);
        path = Main.class.getResource("Images/player2.png");
        assert path != null;
        player[1] = ImageIO.read(path);

        path = Main.class.getResource("Images/car" + spawn + ".png");
        assert path != null;
        carversus = ImageIO.read(path);
        path = Main.class.getResource("Images/opposite/car" + spawn2 + ".png");
        assert path != null;
        caropposit = ImageIO.read(path);

        path = Main.class.getResource("Images/sea.png");
        assert path != null;
        sea[0] = ImageIO.read(path);
        path = Main.class.getResource("Images/sea2.png");
        assert path != null;
        sea[1] = ImageIO.read(path);

        path = Main.class.getResource("Images/grass.png");
        assert path != null;
        grass = ImageIO.read(path);

        path = Main.class.getResource("Images/sand.png");
        assert path != null;
        sand = ImageIO.read(path);

        path = Main.class.getResource("Images/BeachEl.png");
        assert path != null;
        beachEl = ImageIO.read(path);

        path = Main.class.getResource("Images/tree.png");
        assert path != null;
        Tree = ImageIO.read(path);


        Arcars = new ArrayList<>();
        Arcars2 = new ArrayList<>();
        Linee = new ArrayList<>();
        tree = new ArrayList<>();
        BeachEl = new ArrayList<>();
        Player = new Rectangle(100, 165, 50, 20);

        //thread
        time = new Timer(10, this);

        //velocità iniziale
        speed = 3;

        //Inizializzazione del mondo
        for (int i = 0; i < 8; i++) {
            addtraffico(true);
            addalberi();
            addcosesullaspiaggia();
            animacorsia();
        }

        //Inizializzazione delle linee sulla strada
        for (int i = 0; i < 30; i++) {
            addlinee();
        }

        //Avvio del mondo
        addKeyListener(new GameController(this));
        AprieRegistraFont();
        music();
        time.start();
    }

    //shutdown serve per bloccare il processo alla chiusura della finestra
    public static void shutdown() {
        System.exit(0);
    }


    //alcuni get e set per il controller
    public boolean getstatus() {
        return GameOver;
    }

    public boolean getpause() {
        return !Pause;
    }

    public void setpause() {
        this.Pause = !Pause;
    }


    //start&stop servono per la pausa
    public void stop() {
        time.stop();
        clip.stop();
    }
    public void start() {
        setpause();
        time.start();
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    //funzioni che popolano il mondo di gioco
    public void animacorsia() {
        Arcars2.add(new Rectangle((Arcars2.size() * 100 + 10), 390, 60, 30));
    }

    public void addalberi() {
        tree.add(new Rectangle((tree.size() * 300), 280, 1, 1));
    }

    public void addcosesullaspiaggia() {
        BeachEl.add(new Rectangle((BeachEl.size() * 700), 24, 1, 1));
    }

    public void addlinee() {
        Linee.add(new Rectangle((Linee.size() * 80 + 50), 200, 30, 2));
    }


    public void addtraffico(boolean first) {
        //x è la distanza iniziale dal quale partono le auto
        int x = 1200;
        int y = 0;
        int spawn = rand.nextInt(3) + 1;

        //Scelta casuale tra le tre corsie
        if (spawn == 1) {
            y = 160;
        }
        if (spawn == 2) {
            y = 210;
        }
        if (spawn == 3) {
            y = 260;
        }
        if (first) {
            Arcars.add(new Rectangle(x + (Arcars.size() * 200), y, 55, 30));
        } else {
            Arcars.add(new Rectangle((Arcars.size() * 210), y, 55, 30));
        }
    }

    public static void AprieRegistraFont() {
        //apre e registra il font pixeboy nel GraphicsEnvironment locale
        try (InputStream is = Main.class.getResourceAsStream("Resources/Pixeboy.ttf")) {
            assert is != null;
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            font.deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception ignored) {
        }
    }


    //musica e suoni
    public void music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (Settings.audio) {
            URL track;
            //scelta casuale tra due tracce
            if (spawn % 2 == 0) {
                track = Main.class.getResource("Music/qdss.wav");
            } else {
                track = Main.class.getResource("Music/qdss1.wav");
            }
            assert track != null;
            clip.open(AudioSystem.getAudioInputStream(track));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    public void explosion() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (Settings.audio) {
            URL explosion;
            explosion = Main.class.getResource("Music/damage.wav");
            assert explosion != null;
            clip1.open(AudioSystem.getAudioInputStream(explosion));
            clip1.start();
        }
    }


    //movimenti giocatore
    public void moveup() {
        if (Player.y - move > 155) {
            Player.y -= move;
        }
    }
    public void movedown() {
        if (Player.y + move < 270) {
            Player.y += move;
        }
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //DISEGNO:
        //erba
        g.drawImage(grass, -10, 290, Settings.larghezza, 100, null);

        //asfalto prima corsia
        g.setColor(Color.black);
        g.fillRect(0, 150, Settings.larghezza, 150);

        //linee
        g.setColor(Color.white);
        for (Rectangle rect : Linee) {
            g.setColor(Color.white);
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
            g.fillRect(rect.x, rect.y + 50, rect.width, rect.height);
        }

        //asfalto seconda corsia
        g.setColor(Color.black);
        g.fillRect(0, 380, Settings.larghezza, 300);

        //mare (due immagini diverse per creare l'animazione)
        g.drawImage(sand, 0, 70, Settings.larghezza, 80, null);
        if (flame) {
            g.drawImage(sea[0], 0, 0, Settings.larghezza, 81, null);
        } else {
            g.drawImage(sea[1], 0, 0, Settings.larghezza, 81, null);
        }

        //barra nera in alto
        g.setColor(Color.black);
        g.fillRect(0, 0, Settings.larghezza, 24);

        //auto
        for (Rectangle rect : Arcars) {
            g.drawImage(carversus, rect.x, rect.y - 3, null);

        }

        //auto seconda corsia
        for (Rectangle rect : Arcars2) {
            g.drawImage(caropposit, rect.x, rect.y, null);
            g.drawImage(caropposit, rect.x + 10, rect.y + 50, null);
        }


        //linee seconda corsia
        for (Rectangle rect : Linee) {
            g.setColor(Color.white);
            g.fillRect(rect.x + 6, rect.y + 232, rect.width, rect.height);
        }

        //player (due immagini diverse per creare l'animazione)
        if (flame) {
            g.drawImage(player[0], Player.x - 22, Player.y - 12, null);
        } else {
            g.drawImage(player[1], Player.x - 22, Player.y - 12, null);
        }

        //alberi
        for (Rectangle rect : tree) {
            g.drawImage(Tree, rect.x, rect.y, null);
            g.drawImage(Tree, rect.x + 150, rect.y + 40, null);
        }

        //cose sulla spiaggia
        for (Rectangle rect : BeachEl) {
            g.drawImage(beachEl, rect.x, rect.y, null);
        }


        //testi e punti
        g.setColor(Color.red);
        g.setFont(new Font("Pixeboy", Font.PLAIN, 20));
        g.drawString("Punteggio: " + count, 30, 16);
        if (!GameOver) {
            g.drawString("Record: " + record + " punti", 500, 16);
        }

        //al game over avvio l'end game
        if (GameOver){
            drawEnd(g);
        }
    }




    private void drawEnd(Graphics g){
        //avvio l'endgame
        if (GameOver) {
            if (Settings.vibrazione) {
                System.out.println("ZZZ ZZZ ZZZ");
            }

            //cancello il vecchio record
            g.setColor(Color.black);
            g.drawString("Record: " + record + " punti", 500, 16);

            //scrivo il nuovo record
            g.setColor(Color.red);
            g.drawString("Record: " + SceneHandler.getInstance().getHighScore() + " punti", 500, 16);


            //disegno dei rettangoli che rendono tutto più bello
            g.setColor(Color.black);
            g.fillRect(10, 22, 665, 100);
            g.fillRect(50, 320, 225, 30);
            g.fillRect(345, 320, 285, 30);


            //scrivo il menù dell'end game
            g.setColor(Color.RED);
            g.setFont(new Font("Pixeboy", Font.PLAIN, 160));
            g.drawString("GAME OVER", 30, 110);
            g.setFont(new Font("Pixeboy", Font.PLAIN, 30));
            g.drawString(" R PER RIPROVARE                   F PER TORNARE AL MENU", 60, 343);

            //se ho battuto il record lo salvo in scores.txt
            if (count > SceneHandler.getInstance().getHighScore()) {
                SceneHandler.getInstance().setHighScore(count);
                g.setColor(Color.GREEN);
                g.setFont(new Font("Pixeboy", Font.PLAIN, 30));
                g.drawString("Nuovo Record!",250, 20);
                try {
                    fw = new FileWriter("scores.txt", true);
                    bw = new BufferedWriter(fw);
                    pw = new PrintWriter(bw);
                    pw.println("" + count);
                } catch (IOException e) {
                    System.out.println("Ops, c'è un problema con il file scores.txt.");
                    System.out.println("Prova ad eliminarlo.");
                    e.printStackTrace();
                } finally {
                    try {
                        pw.close();
                        bw.close();
                        fw.close();
                    } catch (IOException ignored) {
                    }
                }
            }

        }
    }



    /*---------------------------------------*/



    public void actionPerformed(ActionEvent e) {
        //focus per il listener
        requestFocusInWindow();

        //punteggio, movimento e collisioni
        if (!GameOver) {
            //punti
            count++;
            //aumento difficoltà
            speedUp++;
            //animationC è un semplice counter che fa cambiare le animazioni delle onde e delle fiamme
            animationC++;

            //movimento linee corsie
            Rectangle rect;
            for (Rectangle rectangle1 : Linee) {
                rect = rectangle1;
                rect.x -= 2;
            }


            //movimento auto 1° corsia
            Rectangle rect3;
            for (Rectangle arcar : Arcars) {
                rect3 = arcar;
                if (animationC == 50) {
                    this.flame = !flame;
                    animationC = 1;
                }
                //qui la difficoltà aumenta
                if (speedUp % 3500 == 0 && speed < 6) {
                    speed++;
                    speedUp = 1;
                }
                rect3.x -= speed;
            }

            //movimento 2° corsia
            Rectangle rect4;
            for (Rectangle item : Arcars2) {
                rect4 = item;
                rect4.x -= 2;
            }

            //movimento alberi
            for (Rectangle value : tree) {
                rect4 = value;
                rect4.x -= 2;
            }

            //movimento cose sulla spiaggia
            for (Rectangle rectangle : BeachEl) {
                rect4 = rectangle;
                rect4.x -= 2;
            }



            //ottimizzazione linee
            for (int i = 0; i < Linee.size(); i++) {
                rect = Linee.get(i);
                if (rect.x < -30) {
                    Linee.remove(rect);
                    addlinee();
                }
            }

            //ottimizzazione auto 1° corsia
            for (int i = 0; i < Arcars.size(); i++) {
                rect3 = Arcars.get(i);
                if (rect3.x < -140) {
                    Arcars.remove(rect3);
                    addtraffico(false);
                }
            }

            //ottimizzazione auto 2° corsia
            for (int i = 0; i < Arcars2.size(); i++) {
                rect4 = Arcars2.get(i);
                if (rect4.x < -80) {
                    Arcars2.remove(rect4);
                    animacorsia();
                }
            }

            //ottimizzazione alberi
            for (int i = 0; i < tree.size(); i++) {
                rect4 = tree.get(i);
                if (rect4.x < -300) {
                    tree.remove(rect4);
                    addalberi();
                }
            }

            //ottimizzazione spiaggia
            for (int i = 0; i < BeachEl.size(); i++) {
                rect4 = BeachEl.get(i);
                if (rect4.x < -700) {
                    BeachEl.remove(rect4);
                    addcosesullaspiaggia();
                }
            }

            //collisioni con le altre auto
            for (Rectangle ai : Arcars) {
                if (ai.intersects(Player)) {
                    //fermo tutto il mondo di gioco
                    speed = 0;
                    GameOver = true;


                    try {
                        //avvio il suono dell'esplosione
                        explosion();
                        //sovrascrivo il missile con una bella esplosione
                        URL path = Main.class.getResource("Images/boom.png");
                        assert path != null;

                        player[0] = ImageIO.read(path);
                        player[1] = ImageIO.read(path);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                    //fermo il thread
                    time.stop();
                }
            }
            repaint();
        }
    }
}


