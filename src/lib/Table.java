package lib;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Invoice> invoices;
    private final String INVOICES_FILE_PATH = "src/InvoiceHeader.csv";
    private final String ITEMS_FILE_PATH = "src/InvoiceLine.csv";

    public Table() {
        invoices = new ArrayList<>();
        uploadInvoicesFromFileToTable(INVOICES_FILE_PATH);
        uploadItemsFromFileToTable(ITEMS_FILE_PATH);
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
    public void addInvoice(Invoice invoice){
        this.invoices.add(invoice);
    }
    public void deleteInvoice(int index){

        if(index >=0 && index < invoices.size()){
            invoices.remove(index);
        }
    }
    public Invoice getInvoiceByNumber(int number){
        for (Invoice invoice:invoices) {
            if (invoice.getNumber() == number) {
                return invoice;
            }
        }
        return null;
    }

    public void editInvoiceByIndex(int index, String date, String cusName, List<Item> items){
        invoices.get(index).setDate(date);
        invoices.get(index).setName(cusName);
        invoices.get(index).setItems(items);
    }
        private void uploadInvoicesFromFileToTable(String invoicesFilePath){
        try {
            File file = new File(invoicesFilePath);
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
                addInvoice(invoice);


            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private void uploadItemsFromFileToTable(String itemsFilePath){
        try {
            File file = new File(itemsFilePath);
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
                Invoice invoice = getInvoiceByNumber(number);
                if(invoice != null){
                    invoice.addItem(item);
                }


            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public void writeInvoicesOnFile (){
        int invNo;
        String date;
        String cusName;
        File headerFile = new File(INVOICES_FILE_PATH);
        File lineFile = new File(ITEMS_FILE_PATH);
        try(FileWriter writer = new FileWriter(headerFile)) {
            for (Invoice invoice : invoices) {
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
            for (Invoice invoice : invoices) {
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

