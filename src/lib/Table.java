package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Invoice> invoices;
    private final String INVOICES_FILE_PATH = "C:\\Users\\Paula Fady\\IdeaProjects\\SIG\\src\\InvoiceHeader.csv";
    private final String ITEMS_FILE_PATH = "C:\\Users\\Paula Fady\\IdeaProjects\\SIG\\src\\InvoiceLine.csv";

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
    public void deleteInvoiceByNumber(int number){
        int id = -1;
        for(int i=0 ; i< invoices.size(); i++){
            if(invoices.get(i).getNumber()== number){
                id = i;
            }
        }
        if(id >=0){
            invoices.remove(id);
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
}
