/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import game.Dico;
import org.xml.sax.SAXException;

/**
 *
 * @author couliban
 */
public class TestDico {
    public static void main(String args[]) throws SAXException {
        // Declare et initialise un Dico
        Dico dico = new Dico("data/xml/dico.xml");
        
        dico.lireDictionnaireDOM();
        
        System.out.println(dico.getMotDepuisListeNiveaux(1));
        System.out.println(dico.getMotDepuisListeNiveaux(2));
        System.out.println(dico.getMotDepuisListeNiveaux(3));
        System.out.println(dico.getMotDepuisListeNiveaux(4));
        System.out.println(dico.getMotDepuisListeNiveaux(5));
    }
}
