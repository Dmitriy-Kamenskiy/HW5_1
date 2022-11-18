package ru.netology;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
public class ClientLog {

    protected Collection<Integer[]> clientLogs = new ArrayList<>();
    protected int productNum;
    protected int amount;


    public void log(int productNum, int amount) {
        this.productNum = productNum;
        this.amount = amount;
        clientLogs.add(new Integer[] {productNum, amount});
    }

    public void exportAsCSV(File txtFile) {
        try(CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            StringJoiner logItem = new StringJoiner(",")
                    .add("productNum")
                    .add("amount");
            String[] logArrayForCsv = logItem.toString().split(",");

            writer.writeNext(logArrayForCsv);
            for (Integer[] clientLog : clientLogs){
                logItem = new StringJoiner(",")
                        .add(clientLog[0].toString())
                        .add(clientLog[1].toString());
                logArrayForCsv = logItem.toString().split(",");
                writer.writeNext(logArrayForCsv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
