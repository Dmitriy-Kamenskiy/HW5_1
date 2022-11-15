package ru.netology;

import java.io.File;
import java.io.IOException;

public class Main {
    public static File textFile;
    public static void main(String[] args) throws IOException {
        textFile = new File("basket.txt");
        Basket.loadFromTxtFile(textFile);
    }
}