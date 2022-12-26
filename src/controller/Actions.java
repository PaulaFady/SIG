package controller;

import model.Invoice;
import model.Item;
import model.Table;

import java.io.*;

public class Actions {
    private static final String INVOICES_FILE_PATH = "src/InvoiceHeader.csv";
    private static final String ITEMS_FILE_PATH = "src/InvoiceLine.csv";


    public static void uploadInvoicesFromFileToTable(Table table){
        try {
            File file = new File(INVOICES_FILE_PATH);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] invoiceData;
            while((line = br.readLine()) != null) {
                invoiceData = line.split(",");
                int number = Integer.parseInt(invoiceData[0]);
                String date = invoiceData[1];
                String name = invoiceData[2];
                Invoice invoice = new Invoice(number, date, name);
                table.addInvoice(invoice);


            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public static void uploadItemsFromFileToTable(Table table){
        try {
            File file = new File(ITEMS_FILE_PATH);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] itemData;
            while((line = br.readLine()) != null) {
                itemData = line.split(",");
                int number = Integer.parseInt(itemData[0]);
                String name = itemData[1];
                double price = Double.parseDouble(itemData[2]);
                int count = Integer.parseInt(itemData[3]);
                Item item = new Item(name, price, count);
                Invoice invoice = table.getInvoiceByNumber(number);
                if(invoice != null){
                    invoice.addItem(item);
                }


            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void writeInvoicesOnFile (Table table){
        int invNo;
        String date;
        String cusName;
        File headerFile = new File(INVOICES_FILE_PATH);
        File lineFile = new File(ITEMS_FILE_PATH);
        try(FileWriter writer = new FileWriter(headerFile)) {
            for (Invoice invoice : table.getInvoices()) {
                invNo = invoice.getNumber();
                date = invoice.getDate();
                cusName = invoice.getName();
                writer.write(String.valueOf(invNo));
                writer.write(",");
                writer.write(date);
                writer.write(",");
                writer.write(cusName);
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(FileWriter writer = new FileWriter(lineFile)) {
            for (Invoice invoice : table.getInvoices()) {
                invNo = invoice.getNumber();
                for(Item item : invoice.getItems()){
                    String itemName = item.getName();
                    String itemPrice = String.valueOf(item.getPrice());
                    String itemCount = String.valueOf(item.getCount());
                    writer.write(String.valueOf(invNo));
                    writer.write(",");
                    writer.write(itemName);
                    writer.write(",");
                    writer.write(itemPrice);
                    writer.write(",");
                    writer.write(itemCount);
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
