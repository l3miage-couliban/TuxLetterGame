/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author couliban
 */
public class Room {

    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureTop;
    private String textureSouth;
    private final String PLATEAU = "src/data/xml/plateau.xml";

    public Room() {
        fromXML();
        /*setDepth(100);
        setHeight(60);
        setWidth(100);
        setTextureBottom("textures/floor.png");
        setTextureNorth("textures/floor.png");
        setTextureEast("textures/floor.png");
        setTextureWest("textures/floor.png");*/
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }
    
    private void fromXML() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(PLATEAU);
            doc.getDocumentElement().normalize();
            
            // set depth
            int _depth = Integer.parseInt(doc.getElementsByTagName("depth").item(0).getTextContent());
            setDepth(_depth);
            
            // set width
            int _width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent());
            setWidth(_width);
            
            // set height
            int _height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent());
            setHeight(_height);
            
            // set textureBottom
            String _textureBottom = doc.getElementsByTagName("textureBottom").item(0).getTextContent();
            setTextureBottom(_textureBottom);
            
            // set textureNorth
            String _textureNorth = doc.getElementsByTagName("textureNorth").item(0).getTextContent();
            setTextureNorth(_textureNorth);
            
            // set textureEast
            String _textureEast = doc.getElementsByTagName("textureEast").item(0).getTextContent();
            setTextureEast(_textureEast);
            
            // set textureWest
            String _textureWest = doc.getElementsByTagName("textureWest").item(0).getTextContent();
            setTextureWest(_textureWest);
            
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
