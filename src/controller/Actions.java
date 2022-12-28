package controller;

import model.Invoice;
import model.Item;
import model.Table;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.List;

public class Actions {
    private static String headerPathForSave;
    private static String linePathForSave;


    public static void uploadInvoicesFromFileToTable(Table table, String headerPath) {
        try {
            File file = new File(headerPath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] invoiceData;
            while ((line = br.readLine()) != null) {
                invoiceData = line.split(",");
                int number = Integer.parseInt(invoiceData[0]);
                String date = invoiceData[1];
                String name = invoiceData[2];
                Invoice invoice = new Invoice(number, date, name);
                table.addInvoice(invoice);

            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void uploadItemsFromFileToTable(Table table, String linePath) {
        try {
            File file = new File(linePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] itemData;
            while ((line = br.readLine()) != null) {
                itemData = line.split(",");
                int number = Integer.parseInt(itemData[0]);
                String name = itemData[1];
                double price = Double.parseDouble(itemData[2]);
                int count = Integer.parseInt(itemData[3]);
                Item item = new Item(name, price, count);
                Invoice invoice = table.getInvoiceByNumber(number);
                if (invoice != null) {
                    invoice.addItem(item);
                }


            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void writeInvoicesOnFile(Table table, String saveHeaderFilePath, String saveLineFilePath) {
        int invNo;
        String date;
        String cusName;
        File headerFile;
        File lineFile;
        headerFile = new File(saveHeaderFilePath);
        lineFile = new File(saveLineFilePath);
        try (FileWriter writer = new FileWriter(headerFile)) {
            for (Invoice invoice : table.getInvoices()) {
                invNo = invoice.getNumber();
                date = invoice.getDate();
                cusName = invoice.getName();
                writer.write(String.valueOf(invNo));
                writer.write(",");
                writer.write(date);
                writer.write(",");
                writer.write(cusName);
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter(lineFile)) {
            for (Invoice invoice : table.getInvoices()) {
                invNo = invoice.getNumber();
                for (Item item : invoice.getItems()) {
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
                    writer.write(System.getProperty("line.separator"));

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DefaultTableModel refreshInvoicesJTable(List<Invoice> tableData) {
        var tableModel = new DefaultTableModel();
        tableModel.addColumn("No.");
        tableModel.addColumn("Date");
        tableModel.addColumn("Customer");
        tableModel.addColumn("Total");

        for (Invoice invoice : tableData) {
            tableModel.addRow(new String[]{String.valueOf(invoice.getNumber()), invoice.getDate(), invoice.getName(), String.valueOf(invoice.getTotal())});
        }
        return tableModel;
    }

    public static String getHeaderPathForSave() {
        return headerPathForSave;
    }

    public static void setHeaderPathForSave(String headerPathForSave) {
        headerPathForSave = headerPathForSave;
    }

    public static String getLinePathForSave() {
        return linePathForSave;
    }

    public static void setLinePathForSave(String linePathForSave) {
        linePathForSave = linePathForSave;
    }
}
