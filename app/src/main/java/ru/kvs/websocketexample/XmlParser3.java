package ru.kvs.websocketexample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



public class XmlParser3 {
    private ParkingService parkingService = new ParkingService();

    public List parse(InputStream is) throws XmlPullParserException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList objects = doc.getElementsByTagName("Objects");

            if (objects.getLength() > 0) {
                Node object = objects.item(0);
                if (object.getNodeType() == Node.ELEMENT_NODE) {
                    Element objectElement = (Element) object;
                    parseObjectsElement(objectElement);
                }
            }

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }


        return new ArrayList();
    }

    private void parseObjectsElement(Element element) {
        NodeList objects = element.getChildNodes();
        for (int i = 0; i < objects.getLength(); i++) {
            Node object = objects.item(i);

            if (object.getNodeType() == Node.ELEMENT_NODE) {
                Element objectElement = (Element) object;
                parseObjectParkingService(objectElement);
            }
        }
    }

    private void parseObjectParkingService(Element element) {
        NodeList objects = element.getChildNodes();
        for (int i = 0; i < objects.getLength(); i++) { // id, object(list)
            Node object = objects.item(i);

            if (object.getNodeType() == Node.ELEMENT_NODE) {
                Element objectElement = (Element) object;

                if (objectElement.getTagName().equals("id")) {
                    String id = objectElement.getFirstChild().getNodeValue();
                    parkingService.setId(id);
                } else {
                    parseParkingServiceList(objectElement);
                }
            }
        }
    }

    private void parseParkingServiceList(Element objectElement) {
        NodeList nodes = objectElement.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element nodeElement = (Element) node;

                if (!nodeElement.getTagName().equals("id")) {
                    parseParkingFacility(nodeElement);
                }
            }
        }
    }

    private void parseParkingFacility(Element nodeElement) {
        ParkingLot parkingLot = new ParkingLot();
        parkingService.addParkingLot(parkingLot);

        NodeList nodes = nodeElement.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) { // id, object(list)
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (element.getTagName().equals("id")) {
                    String id = element.getFirstChild().getTextContent();
                    parkingLot.setId(id);
                } else if (element.getTagName().equals("InfoItem")){
                    if(element.getAttribute("name").equals("MaxParkingHours")) {
                        String maxParkingHours = getValue("value", element);
                        parkingLot.setMaxParkingHours(maxParkingHours);
                    } else if (element.getAttribute("name").equals("Owner")) {
                        String owner = getValue("value", element);
                        parkingLot.setOwner(owner);
                    }
                } else if (element.getTagName().equals("Object")) {
                    if (element.getAttribute("type").equals("schema:GeoCoordinates")) {
                        GeoCoordinates position = parseGeoCoordinates(element);
                        parkingLot.setPosition(position);
                    } else if (element.getAttribute("type").equals("schema:OpeningHoursSpecification")) {
                        OpeningHours openingHours = parseOpeningHoursSpecification(element);
                        parkingLot.setOpeningHours(openingHours);
                    } else if (element.getAttribute("type").equals("list")) {
                        parseParkingSpotTypesList(element);
                    }
                }
            }
        }
    }


    private GeoCoordinates parseGeoCoordinates(Element objectElement) {
        GeoCoordinates position = new GeoCoordinates();

        NodeList nodes = objectElement.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element nodeElement = (Element) node;

                if (nodeElement.getTagName().equals("id")) {
                    continue;
                } else if (nodeElement.getTagName().equals("InfoItem")) {
                    if(nodeElement.getAttribute("name").equals("longitude")) {
                        String lon = getValue("value", nodeElement);
                        double longitude = Double.parseDouble(lon);
                        position.setLongitude(longitude);
                    } else if(nodeElement.getAttribute("name").equals("latitude")) {
                        String lat = getValue("value", nodeElement);
                        double latitude = Double.parseDouble(lat);
                        position.setLatitude(latitude);
                    }
                }
            }
        }
        return position;
    }

    private OpeningHours parseOpeningHoursSpecification(Element objectElement) {
        OpeningHours openingHours = new OpeningHours();

        NodeList nodes = objectElement.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element nodeElement = (Element) node;

                if (nodeElement.getTagName().equals("id")) {
                    continue;
                } else if (nodeElement.getTagName().equals("InfoItem")) {
                    if(nodeElement.getAttribute("name").equals("closes")) {
                        String close = getValue("value", nodeElement);
                        openingHours.setClose(close);
                    } else if(nodeElement.getAttribute("name").equals("opens")) {
                        String open = getValue("value", nodeElement);
                        openingHours.setOpen(open);
                    }
                }
            }
        }
        return openingHours;
    }

    private void parseParkingSpotTypesList(Element objectElement) {
        NodeList nodes = objectElement.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element nodeElement = (Element) node;

                if (!nodeElement.getTagName().equals("id")) {
                    parseSpotTypeSection(nodeElement);
                }
            }
        }
    }

    private void parseSpotTypeSection(Element nodeElement) {
    }


    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }



}
