package model;

import java.util.ArrayList;
import java.util.List;

import static controller.Actions.uploadInvoicesFromFileToTable;
import static controller.Actions.uploadItemsFromFileToTable;

public class Table {
    private List<Invoice> invoices;

    public Table() {
        invoices = new ArrayList<>();
    }
    public void loadInvoices(String headerFile, String lineFile){
        invoices = new ArrayList<>();
        uploadInvoicesFromFileToTable(this, headerFile);
        uploadItemsFromFileToTable(this, lineFile);
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
    }

