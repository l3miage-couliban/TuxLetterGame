/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author couliban
 */
public class Dico extends DefaultHandler {

    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;

    // Le niveau courant lors du parcours du XML avec le parseur SAX
    private int currentLevel;

    // le mot à constituer lors du parcours du XML avec le parseur SAX
    private StringBuilder currentValue;
    
    // Déclaration du gestionnaire SAX
    private Dico handler;

    public Dico(String cheminFichierDico) {
        super();

        // Initialisation des les listes de mots
        listeNiveau1 = new ArrayList<>();
        listeNiveau2 = new ArrayList<>();
        listeNiveau3 = new ArrayList<>();
        listeNiveau4 = new ArrayList<>();
        listeNiveau5 = new ArrayList<>();

        // Initialisation du mot à constituer
        currentValue = new StringBuilder();

        this.cheminFichierDico = cheminFichierDico;
    }
    
    public Dico getDico() {
        return handler;
    } 

    public String getMotDepuisListeNiveaux(int niveau) {
        String mot = "";

        switch (verifieNiveau(niveau)) {
            case 1:
                mot = getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                mot = getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                mot = getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                mot = getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                mot = getMotDepuisListe(listeNiveau5);
                break;
            default:
                break;
        }

        return mot;
    }

    public void ajouteMotADico(int niveau, String mot) {
        switch (verifieNiveau(niveau)) {
            case 1:
                listeNiveau1.add(mot);
                break;
            case 2:
                listeNiveau2.add(mot);
                break;
            case 3:
                listeNiveau3.add(mot);
                break;
            case 4:
                listeNiveau4.add(mot);
                break;
            case 5:
                listeNiveau5.add(mot);
                break;
            default:
                break;
        }
    }

    public String getCheminFichierDico() {
        return "";
    }

    private int verifieNiveau(int niveau) {
        if (niveau < 1 || niveau > 5) {
            Random rand = new Random();
            niveau = rand.nextInt(5) + 1;
        }
        return niveau;
    }

    private String getMotDepuisListe(ArrayList<String> list) {
        String mot;
        Random rand = new Random();
        if (list.isEmpty()) {
            mot = "tux";
        } else {
            mot = list.get(rand.nextInt(list.size()));
        }
        return mot;
    }

    // lire le dictionnaire XML à l'aide du parseur DOM
    public void lireDictionnaireDOM() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(cheminFichierDico);
            doc.getDocumentElement().normalize();

            NodeList dico = doc.getElementsByTagName("niveau");

            for (int i = 0; i < dico.getLength(); i++) {
                Element niveau = (Element) dico.item(i);
                NodeList mots = niveau.getElementsByTagName("mot");

                this.ajouteMotsADico(i + 1, mots);
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
        }

    }
     

    private void ajouteMotsADico(int niveau, NodeList mots) {
        for (int j = 0; j < mots.getLength(); j++) {
            Node mot = mots.item(j);
            this.ajouteMotADico(niveau, mot.getTextContent());

        }
    }

    // lire le dictionnaire XML à l'aide du parseur SAX
    public void lireDictionnaire() {
        try {
            // Création d'un factory de parseurs SAX
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // Création d'un parseur SAX
            SAXParser saxParser = factory.newSAXParser();

            // Lecture d'un fichier XML avecun DefaultHandler
            //XMLReader parser = saxParser.getXMLReader();
            this.handler = new Dico(cheminFichierDico);
            saxParser.parse(new File(cheminFichierDico), handler);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("niveau")) {
            // Sauvegarde du niveau
            currentLevel = Integer.parseInt(attributes.getValue("numero"));
        }

        if (qName.equalsIgnoreCase("mot")) {
            // Réinitialiser la variable contenant le mot précédent
            currentValue.setLength(0);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("mot")) {
            ajouteMotADico(currentLevel, currentValue.toString());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        //construction du mot
        currentValue.append(ch, start, length);
    }

    @Override
    public void startDocument() {}

    @Override
    public void endDocument() {}
    

}
