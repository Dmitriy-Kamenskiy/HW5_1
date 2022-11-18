package ru.netology;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static File textFile;
    public static File jsonFile;
    public static boolean isTxtBasket = false;

    public static void main(String[] args) throws IOException, ParseException {
        if (isTxtBasket) {
            textFile = new File("basket.txt");
            Basket.loadFromTxtFile(textFile);
        } else {
            jsonFile = new File("basket.json");
            Basket.loadFromJsonFile(jsonFile);
        }
    }
}