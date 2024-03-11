/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import java.text.Normalizer;

/**
 *
 * @author couliban
 */
public class Letter extends EnvNode {
    private char letter;
    
    private Env env;
    private Room room;

    public Letter(Env env, Room room, Double x, Double z, char let) {
        this.env = env; // initialisation de l'attribut env
        this.room = room; // initialisation de l'attribut room
        letter = toLowerCase(let);
        letter = stripAccent(let);
        
        setScale(3.0);
        setX(x);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(z); // positionnement au milieu de la profondeur de la roo
        
        setTexture("models/letter/"+((letter != ' ')? letter : "cube")+".png");
        setModel("models/letter/cube.obj");
    }
    
    
    public char toLowerCase(char c) {
        return Character.toLowerCase(c);
    }
    
    public char stripAccent(char c) {
        String s;
        s = Normalizer.normalize(""+c, Normalizer.Form.NFKD);
        s = s.replaceAll("[\\p{M}]", "");
        
        
        return s.charAt(0);
    }
    
    
    public char getLetter() {
        return letter;
    }
}
