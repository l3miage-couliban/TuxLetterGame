/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Nagninimeta
 */
public class EditeurDico {
    
    Document _doc;
    private final String CHEMIN_DICO_XML = "src/data/xml/dico.xml";
    private final String CHEMIN_DICO_XSLT = "src/data/xsl/dico.xsl";
    private final String CHEMIN_DICO_HTML = "src/data/html/dico.html";
    
    public EditeurDico() {
        lireDOM();
    }
    
    /*
    * Ajout des mots au dictionnaire
    * Lire le dictionnaire XML à l'aide du parseur DOM
    */
    private void lireDOM() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            _doc = builder.parse(CHEMIN_DICO_XML);
            _doc.getDocumentElement().normalize();
        } catch (IOException | ParserConfigurationException | SAXException e) {
        }
    }
    
    // Enregistrer le document DOM sous la forme d'un fichier XML
    public void ecrireDOM() {
        try {
            // create xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(_doc);
            StreamResult streamResult = new StreamResult(new File(CHEMIN_DICO_XML));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);
            
            toHTML();
            openInBrowser();
        } catch (TransformerException e) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // Ajouter un mot et son niveau dans le dictionnaire
    public void ajouterMot(String mot, int niveau) {
        Element niveauElt = chercherNiveau(niveau);
        
        // creation du nouveau mot
        Element motElt = _doc.createElement("mot");
        motElt.appendChild(_doc.createTextNode(mot));
        
        // ajout du mot au niveau
        niveauElt.appendChild(motElt);
    }
    
    // Chercher le niveau
    private Element chercherNiveau(int niveau) {
        NodeList dico = _doc.getElementsByTagName("niveau");
        
        int i = 0;
        Element niveauElt = null;
        
        while(i<dico.getLength() && niveauElt == null) {
            niveauElt = (Element) dico.item(i);
       
            if(Integer.parseInt(niveauElt.getAttribute("numero")) != niveau) {
                niveauElt = null;
                i++;
            }
        }
        
        return niveauElt;
    }
    
    // chercher si le mot existe déjà dans le dictionnaire
    public boolean motExsite(String mot) {
        NodeList mots = _doc.getElementsByTagName("mot");
        
        int i = 0;
        boolean existe = false;
        
        while(i<mots.getLength() && !existe) {
            Element motElt = (Element) mots.item(i);
            
            if(motElt.getTextContent().equals(mot)) {
                existe = true;
            } else {
                i++;
            }
        }
        
        return existe;
    }
    
     // Sauvegarder un DOM en HTML
    private void toHTML() {
        try {
            //DOMSource xmlDOMSource = new DOMSource(_doc);
            Source xmlDOMSource = new StreamSource(CHEMIN_DICO_XML);
            Source xsltSource = new StreamSource(CHEMIN_DICO_XSLT);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(xsltSource);
            
            StreamResult streamResult = new StreamResult(new File(CHEMIN_DICO_HTML));

            //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlDOMSource, streamResult);
            
        } catch (TransformerException e) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // Open in browser
    private void openInBrowser() {
        try {
            File htmlFile = new File(CHEMIN_DICO_HTML);
            Desktop.getDesktop().browse(htmlFile.toURI());
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
