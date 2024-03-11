/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author couliban
 */
public class JeuDevineLeMotOrdre extends Jeu {

    private int nbLettresrestantes;
    private Chronometre chrono;

    public JeuDevineLeMotOrdre() {
        super();
        nbLettresrestantes = 0;
    }

    private boolean tuxTrouveLettre() {
        Letter premiereLettre = letters.get(0);
        return collision(premiereLettre);
    }

    private int getNbLettresRestantes() {
        return nbLettresrestantes;
    }

    @Override
    protected void demarrePartie(Partie partie) {
        int niveau = partie.getNiveau();
        
        // instancie un chrono
        chrono = new Chronometre(20);
        
        // augmente la limite de temps de 2 secondes de plus par niveau
        chrono.augmenteLimite(niveau*2-2);
        
        chrono.start();
        nbLettresrestantes = letters.size();
    }

    @Override
    protected boolean appliqueRegles(Partie partie) {
        boolean termine = false;
        
        if (chrono.remainsTime()) {
            if (getNbLettresRestantes() == 0) {
                termine = true;
            } else if (tuxTrouveLettre()) {
                Letter letter = letters.get(0);
                env.removeObject(letter);
                letters.remove(0);
                nbLettresrestantes = letters.size();
            }
        } else {
            termine = true;
        }
        
        return termine;
    }

    @Override
    protected void terminePartie(Partie partie) {
        chrono.stop();
        partie.setTrouve(nbLettresrestantes);
        partie.setTemps(chrono.getSeconds());

    }

}
