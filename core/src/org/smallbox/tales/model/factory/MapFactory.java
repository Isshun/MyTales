package org.smallbox.tales.model.factory;

import com.badlogic.gdx.utils.GdxRuntimeException;
import org.smallbox.tales.Game;
import org.smallbox.tales.Settings;
import org.smallbox.tales.model.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by Alex on 06/11/2014.
 */
public class MapFactory {
    public static MapModel fromJSON(String name) {
        try {
            File fXmlFile = new File("maps", name.endsWith(".xml") ? name : name + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            //Element mapElement = (Element)doc.getElementsByTagName("map");

            MapModel map = new MapModel(doc.getDocumentElement().getAttribute("id"));

            if (!doc.getDocumentElement().getAttribute("music").isEmpty()) {
                map.setMusic(doc.getDocumentElement().getAttribute("music"));
            }

            map.setName(doc.getDocumentElement().getAttribute("name"));

            System.out.println("----------------------------");

            // Load areas
            {
                NodeList nList = doc.getDocumentElement().getElementsByTagName("area");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        try {
                            AreaModel area = new GroundModel(eElement.getAttribute("path"), eElement.getAttribute("name"), Integer.parseInt(eElement.getAttribute("index")));
                            map.setArea(Integer.parseInt(eElement.getAttribute("z")), Integer.parseInt(eElement.getAttribute("x")), Integer.parseInt(eElement.getAttribute("y")), area);
                        } catch (GdxRuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // Load items
            {
                NodeList nList = doc.getDocumentElement().getElementsByTagName("item");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        ItemModel item = Game.getInstance().getItem(eElement.getAttribute("id"));
                        map.addItem(Integer.parseInt(eElement.getAttribute("z")), Integer.parseInt(eElement.getAttribute("x")), Integer.parseInt(eElement.getAttribute("y")), item);
                    }
                }
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void toJSON(MapModel map) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("map");
            doc.appendChild(rootElement);

            addAttr(doc, rootElement, "name", map.getName());
            addAttr(doc, rootElement, "id", map.getId());
            addAttr(doc, rootElement, "music", map.getMusic());

            addAreas(doc, rootElement, map);
            addItems(doc, rootElement, map);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("maps", map.getId() + ".xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private static void addItems(Document doc, Element rootElement, MapModel map) {
        Element areas = doc.createElement("items");
        rootElement.appendChild(areas);

        for (int z = 2; z < 5; z++) {
            for (int x = 0; x < 100; x++) {
                for (int y = 0; y < 100; y++) {
                    ItemModel item = map.getItem(z, x, y);
                    if (item != null) {
                        Element areaElement = doc.createElement("item");

                        addAttr(doc, areaElement, "z", z);
                        addAttr(doc, areaElement, "x", x);
                        addAttr(doc, areaElement, "y", y);
                        addAttr(doc, areaElement, "name", item.getName());
                        addAttr(doc, areaElement, "path", item.getPath());
                        addAttr(doc, areaElement, "index", 0);
                        addAttr(doc, areaElement, "id", item.getId());

                        areas.appendChild(areaElement);
                    }
                }
            }
        }
    }

    private static void addAttr(Document doc, Element element, String name, int value) {
        Attr attr = doc.createAttribute(name);
        attr.setValue(String.valueOf(value));
        element.setAttributeNode(attr);
    }

    private static void addAttr(Document doc, Element element, String name, String value) {
        Attr attr = doc.createAttribute(name);
        attr.setValue(value);
        element.setAttributeNode(attr);
    }

    private static void addAreas(Document doc, Element rootElement, MapModel map) {
        Element areas = doc.createElement("areas");
        rootElement.appendChild(areas);

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                AreaModel area = map.getArea(0, x, y);
                if (area != null) {
                    Element areaElement = doc.createElement("area");

                    addAttr(doc, areaElement, "z", 0);
                    addAttr(doc, areaElement, "x", x);
                    addAttr(doc, areaElement, "y", y);
                    addAttr(doc, areaElement, "name", area.getName());
                    addAttr(doc, areaElement, "path", area.getPath());
                    addAttr(doc, areaElement, "index", area.getIndex());

                    areas.appendChild(areaElement);
                }
            }
        }
    }
}
