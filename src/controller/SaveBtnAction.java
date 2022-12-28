package controller;

import model.Invoice;
import model.Item;
import model.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SaveBtnAction implements ActionListener {
    Table tableData;
    JTable invoicesJTable;
    JTable invoiceItemsJTable;
    JTextField invoiceDateTf;
    JTextField customerNameTf;


    public SaveBtnAction(Table table, JTable invJT, JTable invItJT, JTextField invDate, JTextField cusName) {
        tableData = table;
        invoicesJTable = invJT;
        invoiceItemsJTable = invItJT;
        invoiceDateTf = invDate;
        customerNameTf = cusName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = invoicesJTable.getSelectedRow();
        String date = invoiceDateTf.getText();
        String cusName = customerNameTf.getText();
        Invoice invoice;
        // Edit Table
        if(index < tableData.getInvoices().size()){
            invoice = tableData.getInvoices().get(index);
            invoice.setName(cusName);
            invoice.setDate(date);
            // Create Table
        } else{
            int no = 1;
            if (index > 0){
                no = tableData.getInvoices().get(tableData.getInvoices().size()-1).getNumber()+1;
            }
            invoice = new Invoice(no , date, cusName);
            tableData.addInvoice(invoice);
        }
        var oldItems = invoice.getItems();
        invoice.setItems(new ArrayList<>());
        try{
            for(int i=0; i< invoiceItemsJTable.getRowCount(); i++){
                String itemName = String.valueOf((invoiceItemsJTable.getModel().getValueAt(i,1)));
                double itemPrice = Double.parseDouble(String.valueOf(invoiceItemsJTable.getModel().getValueAt(i,2)));
                int itemCount = Integer.parseInt(String.valueOf(invoiceItemsJTable.getModel().getValueAt(i,3)));
                Item item = new Item(itemName, itemPrice, itemCount);
                invoice.addItem(item);
            }}
        catch (Exception ex){
            invoice.setItems(oldItems);
        }
        invoicesJTable.setModel(Actions.refreshInvoicesJTable(tableData.getInvoices()));
    }
}
