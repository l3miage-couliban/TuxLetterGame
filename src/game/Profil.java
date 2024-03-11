/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author couliban
 */
public class Profil {
    private String nom;
    private String avatar;
    private String dateNaissance;
    private ArrayList<Partie> parties;
    public Document _doc;
    private final String XML_PROFIL_DIR = "src/data/xml/profils/";
    private final String XSLT_PROFIL_PATH = "src/data/xsl/profil.xsl";
    private final String HTML_PROFIL_DIR = "src/data/html/profils/";

    public Profil(String nom, String dateNaissance) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.avatar = "../../images/bear.png";
        parties = new ArrayList();
    }

    public Profil(String nom) {
        this.nom = nom;
        parties = new ArrayList();
    }
    
    public Partie recupererPartie(int id) {
        boolean trouve = false;
        int i = 0;
        Partie partie = null;
      
        
        while(i<parties.size() && !trouve){
            if(parties.get(i).getId() == id) {
                trouve = true;
            } else {
                i++;
            }
        }
        return parties.get(i);
    }

    public void ajoutePartie(Partie partie) {
        Partie queue = null ;
        
        if (parties.size() != 0) {
            queue = parties.get(parties.size() -1);
            partie.setId(queue.getId()+1);
        } else {
            partie.setId(1);
        }
        
        
        parties.add(partie);
    }

    public void sauvegarder() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            _doc = documentBuilder.newDocument();

            //root element (profil)
            Element profilElt = _doc.createElement("profil");
            _doc.appendChild(profilElt);
            
            // set xmlns attribute to profil element
            Attr xmlnsAttr = _doc.createAttribute("xmlns");
            xmlnsAttr.setValue("http://myGame/tux");
            profilElt.setAttributeNode(xmlnsAttr);

            // nom element
            Element nomElt = _doc.createElement("nom");
            nomElt.appendChild(_doc.createTextNode(nom));
            profilElt.appendChild(nomElt);

            // avatar element
            Element avatarElt = _doc.createElement("avatar");
            avatarElt.appendChild(_doc.createTextNode(avatar));
            profilElt.appendChild(avatarElt);

            //date de naissance element
            Element dateNaissanceElt = _doc.createElement("anniversaire");
            dateNaissanceElt.appendChild(_doc.createTextNode(profileDateToXmlDate(dateNaissance)));
            profilElt.appendChild(dateNaissanceElt);

            // parties element
            Element partiesElt = _doc.createElement("parties");
            for (int i = 0; i < parties.size(); i++) {
                Partie partie = parties.get(i);
                System.out.println(partie.getId()+"-"+partie.getDate());
                partie.setDate(profileDateToXmlDate(partie.getDate()));
                partiesElt.appendChild(partie.getPartie(_doc));
                partie.setDate(xmlDateToProfileDate(partie.getDate()));
            }
            profilElt.appendChild(partiesElt);
            

            toXML(XML_PROFIL_DIR + nom + ".xml");
            toHTML();
            openInBrowser();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    public boolean charge(String nomJoueur) {
        boolean exist = false;
        try {
            File tempFile = new File(XML_PROFIL_DIR + nom + ".xml");
            exist = tempFile.exists();

            if (exist) {
                _doc = fromXML(XML_PROFIL_DIR + nom + ".xml");
                //Récupérer le nom
                this.nom = _doc.getElementsByTagName("nom").item(0)
                        .getTextContent();

                // Récupérer l'avatar
                this.avatar = _doc.getElementsByTagName("avatar").item(0)
                        .getTextContent();

                // Récupérer la date de naissance
                this.dateNaissance = xmlDateToProfileDate(_doc.getElementsByTagName("anniversaire")
                        .item(0).getTextContent());

                // Récupérer les parties du profil
                NodeList partieNodes = _doc.getElementsByTagName("partie");

                // Extraction des parties existantes
                for (int i = 0; i < partieNodes.getLength(); i++) {
                    Element partieElt = (Element) partieNodes.item(i);
                    Partie partie = new Partie(partieElt);
                    
                    partie.setDate(xmlDateToProfileDate(partie.getDate()));
                    parties.add(partie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exist;
    }

    // Crée un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(nomFichier);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    // écrit le resultat du jeu dans la console
    public void toConsole() {
        System.out.println("+----------------------------------+");
        System.out.println("|             RESULTATS            |");
        System.out.println("+----------------------------------+");
        System.out.println();
        System.out.println("Nom: "+nom);
        System.out.println("Date de naissance: "+dateNaissance);
        
        
        System.out.println("\n          DERNIERE PARTIE JOUEE          \n");
        System.out.println("-------------------");
        
        // Affichage de la dernière partie jouée
        Partie partie = parties.get(parties.size()-1);
        System.out.println("+ Date: "+partie.getDate());
        System.out.print("Temps: "+partie.getTemps()+"\t");
        System.out.print("Niveau: "+partie.getNiveau()+"\t");
        System.out.print("Score: "+partie.getTrouve()+"%\t");
        System.out.println("Mot: "+partie.getMot()+"\t");
        
        System.out.println("-------------------");
        
        
    }

    // Sauvegarde un DOM en XML
    private void toXML(String nomFichier) {
        try {
            // create xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(_doc);
            StreamResult streamResult = new StreamResult(new File(nomFichier));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // Sauvegarder un DOM en HTML
    private void toHTML() {
        try {
            //DOMSource xmlDOMSource = new DOMSource(_doc);
            Source xmlDOMSource = new StreamSource(XML_PROFIL_DIR+nom+".xml");
            Source xsltSource = new StreamSource(this.XSLT_PROFIL_PATH);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(xsltSource);
            
            StreamResult streamResult = new StreamResult(new File(this.HTML_PROFIL_DIR+nom+".html"));

            //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlDOMSource, streamResult);
            
        } catch (TransformerException e) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // Open in browser
    private void openInBrowser() {
        try {
            File htmlFile = new File(this.HTML_PROFIL_DIR+nom+".html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        String[] dateTable;
        
        dateTable = xmlDate.split("-");
        // récupérer le jour
        //date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date = dateTable[2];
        date += "/";
        // récupérer le mois
        //date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += dateTable[1];
        date += "/";
        // récupérer l'année
        //date += xmlDate.substring(0, xmlDate.indexOf("-"));
        date += dateTable[0];

        return date;
    }

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }

}
