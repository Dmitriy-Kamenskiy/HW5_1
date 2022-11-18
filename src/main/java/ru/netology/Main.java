package ru.netology;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {
    public static String typeFileLoad = "json";
    public static String typeFileSave  = "json";
    public static String pathFileLoad = "basket.json";
    public static String pathFileSave = "basket.json";
    public static String pathFileLog = "client.csv";
    public static File textFile = new File(pathFileLoad);
    public static File jsonFile = new File(pathFileLoad);
    public static File logFile = new File(pathFileLog);
    public static boolean isLoad = false;
    public static boolean isSave = false;
    public static boolean isLog = false;
    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {

        configLoad(new File("shop.xml"));

        if (!isLoad) {
            pathFileLoad = "";
        } else {
            if (!typeFileLoad.equals(typeFileSave)) {
                System.err.println("Ошибка конфигурации: Формат загрузки корзины не равен формату сохранения");
                return;
            }
        }
        if (typeFileSave.equals("text"))
            Basket.loadFromTxtFile(textFile);
        else
            Basket.loadFromJsonFile(jsonFile);
    }

    public static void configLoad(File shopXml) throws ParserConfigurationException, IOException, SAXException {
        if (shopXml.exists()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(shopXml);

            NodeList nodeList = doc.getElementsByTagName("load");
            Element element = (Element) nodeList.item(0);
            isLoad = element.getElementsByTagName("enabled").item(0)
                    .getTextContent().equals("true");
            pathFileLoad = element.getElementsByTagName("fileName").item(0)
                    .getTextContent();
            typeFileLoad = element.getElementsByTagName("format").item(0)
                    .getTextContent();

            nodeList = doc.getElementsByTagName("save");
            element = (Element) nodeList.item(0);
            isSave = element.getElementsByTagName("enabled").item(0)
                    .getTextContent().equals("true");
            pathFileSave = element.getElementsByTagName("fileName").item(0)
                    .getTextContent();
            typeFileSave = element.getElementsByTagName("format").item(0)
                    .getTextContent();

            nodeList = doc.getElementsByTagName("log");
            element = (Element) nodeList.item(0);
            isLog = element.getElementsByTagName("enabled").item(0)
                    .getTextContent().equals("true");
            pathFileLog = element.getElementsByTagName("fileName").item(0)
                    .getTextContent();

        }
    }
}