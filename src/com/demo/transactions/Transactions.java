package com.demo.transactions;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Transactions {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new FileReader("C:/workspace/q1.test_data"));
        Map<String, String> transactionsMap = new LinkedHashMap<String, String>();
        String line;

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            if ( !line.isEmpty() &&
                    (!line.contains("SZ  [") && !line.contains("smh [record")  && !line.contains("[record") && !line.contains("[void") )) {

                String[] columns = line.split("\"");
                transactionsMap.put(columns[0].trim(), columns[1]);
            }

            if( line.contains("]SZ"))
                break;
        }
        System.out.println(transactionsMap);
        System.out.println(transactionsMap.size());

        //creating csv file
        FileWriter csvWriter = new FileWriter("C:/workspace/transactions.csv");

        for (Map.Entry<String,String> entry : transactionsMap.entrySet()) {

            if(!entry.getValue().contains(" ")){

                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                //writing in csv file
                csvWriter.append(String.join(",", entry.getKey(), entry.getValue()));
                csvWriter.append("\n");
            }
        }

        csvWriter.flush();
        csvWriter.close();
        System.out.println("transactions.csv written successfully");

        //creating xlsx sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet( " Transactions Info ");

        XSSFRow row;
        int rowId = 0;
        Set< String > keyId = transactionsMap.keySet();
        for (String key : keyId) {

            if (!transactionsMap.get(key).contains(" ")) {
                row = spreadsheet.createRow(rowId++);
                row.createCell(0).setCellValue(key);
                row.createCell(1).setCellValue(transactionsMap.get(key));

            }
        }
        //Write in file system
        FileOutputStream output = new FileOutputStream(new File("C:/workspace/transactions.xlsx"));
        workbook.write(output);
        output.close();
        System.out.println("transactions.xlsx written successfully");
    }

}
