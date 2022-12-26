package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static controller.Actions.uploadInvoicesFromFileToTable;
import static controller.Actions.uploadItemsFromFileToTable;

public class Table {
    private List<Invoice> invoices;
    private final String INVOICES_FILE_PATH = "src/InvoiceHeader.csv";
    private final String ITEMS_FILE_PATH = "src/InvoiceLine.csv";

    public Table() {
        invoices = new ArrayList<>();
        uploadInvoicesFromFileToTable(this);
        uploadItemsFromFileToTable(this);
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
    }

