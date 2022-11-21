package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;


import static java.util.stream.Collectors.toList;

public class Basket {
    protected String[] products;
    protected int[] prices;
    protected int[] productCounts;

    ClientLog clientLog = new ClientLog();

    public Basket(String[] products, int[] prices, int[] productCounts) {
        this.products = products;
        this.prices = prices;
        this.productCounts = productCounts;
    }

    public void addToCart(int productNum, int amount) throws IOException {
        productCounts[productNum] += amount;
        if (Main.isSave) {
            if (Main.typeFileSave.equals("text")) {
                saveTxt(Main.textFile);
            } else {
                saveJson(Main.jsonFile);
            }
        }
        clientLog.log(productNum, amount);
    }

    public void saveJson(File jsonFile){

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try (FileWriter file = new FileWriter(jsonFile)) {
            file.write(gson.toJson(this));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void basketShop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int productNumber;
            int productCount;
            System.out.println("Выберите товар и количество или введите `end`");
            String inputString = scanner.nextLine();
            if ("end".equals(inputString)) {
                printCart();
                clientLog.exportAsCSV(Main.logFile);
                break;
            }
            String[] parts = inputString.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);
            try {
                addToCart(productNumber, productCount);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void printCart() {
        int productCount;
        int currentPrice;
        long sumProducts = 0;
        System.out.println("Ваша корзина:");
        for (int j = 0; j < productCounts.length; j++) {
            productCount = productCounts[j];
            currentPrice = prices[j];
            if (productCount == 0) {
                continue;
            }
            System.out.println(products[j] + " " + productCount +
                    " шт " + currentPrice + " руб/шт " + currentPrice * productCount + " руб в сумме");
            sumProducts += (long) currentPrice * productCount;
        }
        System.out.println("Итого " + sumProducts + " руб");
    }

    public void printMenuCustomer() {
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < this.products.length; i++) {
            System.out.println(i + 1 + ". " + this.products[i] + " " + this.prices[i] + " руб/шт");
        }
    }

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String e : products)
                out.print(e + " ");
            out.println("");
            for (int e : prices)
                out.print(e + " ");
            out.println("");
            for (int e : productCounts)
                out.print(e + " ");
            out.println("");
            out.flush();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    
    public static Basket loadFromJsonFile(File jsonFile) throws IOException, ParseException {
        String[] productsL;
        int[] pricesL;
        int[] productCountsL;

        if (jsonFile.exists()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(jsonFile));

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Basket basket1 = gson.fromJson(obj.toString(), Basket.class);
            productsL = basket1.products;
            pricesL = basket1.prices;
            productCountsL = basket1.productCounts;
        } else {
            productsL = new String[]{"Хлеб", "Яблоки", "Молоко"};
            pricesL = new int[]{100, 200, 300};
            productCountsL = new int[productsL.length];
        }

        Basket basket = new Basket(productsL, pricesL, productCountsL);

        basket.printMenuCustomer();
        basket.basketShop();
        return basket;
    }
    
    public static Basket loadFromTxtFile (File textFile) throws IOException {
        String[] productsL;
        int[] pricesL;
        int[] productCountsL;

        if (textFile.exists()) {
            FileReader reader = new FileReader(textFile);
            Scanner scanner = new Scanner(reader);
            ArrayList<String> arrayList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                arrayList.add(scanner.nextLine());
            }
            scanner.close();
            reader.close();
            ArrayList<String[]> stringArrayList = (ArrayList<String[]>) arrayList.stream()
                    .map((s) -> s.split(" ")).collect(toList());
            productsL = stringArrayList.get(0).clone();
            pricesL = new int[productsL.length];
            productCountsL = new int[productsL.length];
            for (int i = 0; i < productsL.length; i++) {
                pricesL[i] = Integer.parseInt(stringArrayList.get(1)[i]);
                productCountsL[i] = Integer.parseInt(stringArrayList.get(2)[i]);
            }
        } else {
            productsL = new String[]{"Хлеб", "Яблоки", "Молоко"};
            pricesL = new int[]{100, 200, 300};
            productCountsL = new int[productsL.length];
        }

        Basket basket = new Basket(productsL, pricesL, productCountsL);
        basket.printMenuCustomer();
        basket.basketShop();
        return basket;
    }
}