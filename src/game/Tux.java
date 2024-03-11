/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author couliban
 */
public class Tux extends EnvNode {

    private Env env;
    private Room room;

    public Tux(Env env, Room room) {
        this.env = env; // initialisation de l'attribut env
        this.room = room; // initialisation de l'attribut room
        setScale(5.0);
        setX(room.getWidth() / 2);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(room.getDepth() / 2); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux.png");
        setModel("models/tux/tux.obj");
    }

    public void deplace() {
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut
            this.setRotateY(180);
            double newZ = this.getZ() - 1.0;
            if(newZ > 5) {
                this.setZ(newZ);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            this.setRotateY(-90);
            double newX = this.getX() - 1.0;
            if (newX > 5) {
                this.setX(newX);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_K) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'gauche' ou Q
            // Droite
            this.setRotateY(90);
            double newX = this.getX() + 1.0;
            if (newX < 95) {
                this.setX(newX);
            }
            
        }
        if (env.getKeyDown(Keyboard.KEY_N) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'gauche' ou Q
            // Bas
            this.setRotateY(360);
            double newZ = this.getZ() + 1.0;
            if(newZ < 95) {
                this.setZ(newZ);
            }
        }
        
    
    }

}
