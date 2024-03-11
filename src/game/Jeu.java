/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author couliban
 */
abstract public class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    protected final Env env;
    private final Room mainRoom;
    private final Room menuRoom;
    private Profil profil;
    private Tux tux;
    protected ArrayList<Letter> letters;
    private final Dico dico;
    protected EnvTextMap menuText;                         //text (affichage des texte du jeu)
    private EditeurDico editeurDico;

    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie une liste de lettre
        letters = new ArrayList<Letter>();

        // Instancie un editeur de dictionnaire
        editeurDico = new EditeurDico();

        // Instancie un dictionnaire
        dico = new Dico("src/data/xml/dico.xml");

        // instancie le menuText
        menuText = new EnvTextMap(env);

        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("1. Afficher le resultat de la partie dans la console ?", "Affichage1", 250, 280);
        menuText.addText("2. Sauvegarder la partie ?", "Affichage2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("Entrez votre date de naissance (JJxMMxAAAA) : ", "dateNaissance", 100, 300);
        menuText.addText("Choisissez un niveau : ", "Niveau", 200, 300);
        menuText.addText("Entrez votre mot : ", "AjouteMot", 200, 300);
        menuText.addText("Le mot entré existe déjà !", "MotExiste", 200, 350);
        menuText.addText("Entrez l'id de la partie existante : ", "Partie", 120, 300);
        menuText.addText("Trouve ce mot : ", "Mot", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        menuText.addText("4. Ajouter un mot au dictionnaire ?", "Principal4", 250, 220);

    }

    public void execute() {
        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);

        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }

    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }

    private String getDateNaissance() {
        String dateNaissance = "";
        menuText.getText("dateNaissance").display();
        dateNaissance = menuText.getText("dateNaissance").lire(true);
        menuText.getText("dateNaissance").clean();
        return dateNaissance.replace('x', '/');
    }

    private int getNiveau() {
        String choix = "";
        int niveau;
        menuText.getText("Niveau").display();
        choix = menuText.getText("Niveau").lire(true);

        switch (choix) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                niveau = Integer.parseInt(choix);
                break;
            default:
                niveau = 1;
                break;
        }

        menuText.getText("Niveau").clean();
        return niveau;
    }

    private void afficheMot(String mot) {
        menuText.getText("Mot").addTextAndDisplay("", mot);
        Chronometre chrono = new Chronometre(3);
        chrono.start();
        while (chrono.remainsTime()) {
            env.advanceOneFrame();
        }
        chrono.stop();
        menuText.getText("Mot").clean();
    }

    private void afficheResultat() {
        // restaure la room du menu
        env.setRoom(menuRoom);

        //affiche menu
        menuText.getText("Question").display();
        menuText.getText("Affichage1").display();
        menuText.getText("Affichage2").display();

        // vérifie qu'une touche 1 ou 2 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        // nettoie l'environnement du texte
        menuText.getText("Question").clean();
        menuText.getText("Affichage1").clean();
        menuText.getText("Affichage2").clean();

        switch (touche) {
            case Keyboard.KEY_1:
                profil.toConsole();
                break;
            case Keyboard.KEY_2:
                profil.sauvegarder();
                break;
        }

    }

    private Partie choisirPartieExistante() {
        String choix = "";
        int id = 0;
        boolean idIsInteger = true;
        menuText.getText("Partie").display();
        choix = menuText.getText("Partie").lire(true);

        try {
            id = Integer.parseInt(choix);
        } catch (NumberFormatException e) {
            idIsInteger = false;
        }

        menuText.getText("Partie").clean();
        return idIsInteger ? profil.recupererPartie(id) : null;
    }

    private String demandeMot() {
        int niveau;
        String mot;

        niveau = getNiveau();

        menuText.getText("AjouteMot").display();
        mot = menuText.getText("AjouteMot").lire(true);

        // si le mot existe dans le dictionnaire,
        // redemander de rentrer un mot
        while (editeurDico.motExsite(mot)) {
            menuText.getText("MotExiste").display();
            mot = menuText.getText("AjouteMot").lire(true);
            menuText.getText("MotExiste").clean();
        }

        menuText.getText("AjouteMot").clean();

        return mot + " " + niveau;
    }

    // fourni, à compléter
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            // restaure la room du jeu
            env.setRoom(mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    
                    //choisi un niveau
                    int niveau = getNiveau();

                    // Definition de la date
                    Date date;
                    date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                    // Charger les mots du dictionnaire XML avec un parseur DOM
                    //dico.lireDictionnaireDOM();
                    // Charger les mots du dictionnaire XML avec un parseur SAX
                    dico.lireDictionnaire();

                    //Récupération d'un mot du dictionnaire
                    String mot = dico.getDico().getMotDepuisListeNiveaux(niveau);

                    // affiche le mot pendant 3 secondes
                    afficheMot(mot);
                    
                    // crée un nouvelle partie
                    partie = new Partie(formatter.format(date), mot, niveau);

                    // joue
                    joue(partie);

                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    profil.ajoutePartie(partie);
                    afficheResultat();
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    Partie existante = choisirPartieExistante();

                    // Definition de la date
                    date = new Date();
                    formatter = new SimpleDateFormat("dd/MM/yyyy");

                    partie = new Partie(formatter.format(date), existante.getMot(), existante.getNiveau());
                    
                    // affiche le mot pendant 3 secondes
                    afficheMot(existante.getMot());

                    // joue
                    joue(partie);

                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    profil.ajoutePartie(partie);
                    afficheResultat();
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;
        String dateNaissance;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
        menuText.getText("Principal4").display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();
        menuText.getText("Principal4").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                profil = new Profil(nomJoueur);
                if (profil.charge(nomJoueur)) {
                    choix = menuJeu();
                } else {
                    choix = MENU_VAL.MENU_SORTIE;//CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // demande la date de naissance du nouveau joueur
                dateNaissance = getDateNaissance();
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur, dateNaissance);
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
                break;
            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_4:
                // demande le niveau et le mot
                String[] mot = demandeMot().split(" ");

                // ajoute le mot et son niveau
                editeurDico.ajouterMot(mot[0], Integer.parseInt(mot[1]));

                // enregistre le dictionnaire modifié
                editeurDico.ecrireDOM();

        }
        return choix;
    }

    public void joue(Partie partie) {
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(mainRoom);

        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);
        
        // vider la liste de lettres
        letters.clear();

        //Récupération d'un mot du dictionnaire
        String mot = partie.getMot();

        int min = 10;
        int max = (int) mainRoom.getWidth() - 10;

        for (char c : mot.toCharArray()) {
            double randX = (double) ((Math.random() * (max - min)) + min);
            double randZ = (double) ((Math.random() * (max - min)) + min);
            Letter letter = new Letter(env, mainRoom, randX, randZ, c);
            letters.add(letter);
            env.addObject(letter);
        }

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);

        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.deplace();

            // Ici, on applique les regles
            finished = appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
    }

    protected double distance(Letter letter) {
        double abcisses = Math.pow((tux.getX() - letter.getX()), 2);
        double ordonnees = Math.pow((tux.getZ() - letter.getZ()), 2);
        return Math.sqrt(abcisses + ordonnees);
    }

    protected boolean collision(Letter letter) {
        double distance = distance(letter);
        return (distance < 5);
    }

    protected abstract void demarrePartie(Partie partie);

    protected abstract boolean appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

}
