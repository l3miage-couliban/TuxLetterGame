/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author couliban
 */
public class Partie {
    private int id;
    private String date;
    private String mot;
    private int niveau;
    private int trouve;
    private int temps;
    
    public Partie(String date, String mot, int niveau){
        this.id = 0;
        this.date = date;
        this.mot = mot;
        this.niveau = niveau;
        
    }
    
    public Partie(Element partieElt) {
        id = Integer.parseInt(partieElt.getAttribute("id"));
        date = partieElt.getAttribute("date");
        
        Element motElt = (Element) partieElt.getElementsByTagName("mot")
                .item(0);
        mot = motElt.getTextContent();
        
        niveau = Integer.parseInt(motElt.getAttribute("niveau"));
        
        String trouvePercentage = partieElt.getAttribute("trouvé");
        trouve = Integer.parseInt(trouvePercentage.substring(0, trouvePercentage.length() -1));
        
        Element tempsElt = (Element) partieElt.getElementsByTagName("temps")
                .item(0);
        temps = Integer.parseInt(tempsElt.getTextContent());
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setTrouve(int nbLettresRestantes) {
        trouve = 100 - ((nbLettresRestantes * 100)/mot.length());
    }
    
    public void setTemps(int temps) {
        this.temps = temps;
    }
    
    public int getTemps() {
        return temps;
    }
    
    public int getTrouve() {
        return trouve;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    public String getMot() {
        return mot;
    }
    
    public Element getPartie(Document doc) {
        // partie element
        Element partie = doc.createElement("partie");
        
        // set id attribute to partie element
        Attr idAttr = doc.createAttribute("id");
        idAttr.setValue(""+id);
        partie.setAttributeNode(idAttr);
        
        // set date attribute to partie element
        Attr dateAttr = doc.createAttribute("date");
        dateAttr.setValue(date);
        partie.setAttributeNode(dateAttr);
        
        // set trouvé attribute to partie alement
        Attr trouveAttr = doc.createAttribute("trouvé");
        trouveAttr.setValue(trouve+"%");
        partie.setAttributeNode(trouveAttr);
        
        // temps element
        Element tempsElt = doc.createElement("temps");
        tempsElt.appendChild(doc.createTextNode(""+temps));
        
        // mot element
        Element motElt = doc.createElement("mot");
        motElt.appendChild(doc.createTextNode(mot));
        
        // set niveau attribute to mot element
        Attr niveauAttr = doc.createAttribute("niveau");
        niveauAttr.setValue(""+niveau);
        motElt.setAttributeNode(niveauAttr);
        
        partie.appendChild(tempsElt);
        partie.appendChild(motElt);
        
        return partie;
    }
    
    @Override
    public String toString() {
        return date;
    }
    
    
}
