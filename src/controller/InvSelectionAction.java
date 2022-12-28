package controller;

import model.Item;
import model.Table;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class InvSelectionAction implements ListSelectionListener {
    Table tableData;
    JTable invoicesJTable;
    JTable invoiceItemsJTable;
    JLabel invNoLabel;
    JLabel invTotalLabel;
    JTextField invoiceDateTf;
    JTextField customerNameTf;

    public InvSelectionAction(Table table, JTable inv, JTable invItJT, JLabel invNo, JLabel invTotal, JTextField invDate, JTextField cusName) {
        tableData = table;
        invoicesJTable = inv;
        invoiceItemsJTable = invItJT;
        invNoLabel = invNo;
        invTotalLabel = invTotal;
        invoiceDateTf = invDate;
        customerNameTf = cusName;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
                var tableModel = new DefaultTableModel();
                tableModel.addColumn("No.");
                tableModel.addColumn("Item Name");
                tableModel.addColumn("Item Price");
                tableModel.addColumn("Count");
                tableModel.addColumn("Item Total");

                if(invoicesJTable.getSelectedRow() < 0 || invoicesJTable.getSelectedRow() >= tableData.getInvoices().size()){
                    invoiceItemsJTable.setModel(tableModel);
                    invNoLabel.setText("Invoice Number ");
                    invTotalLabel.setText("Invoice Total ");
                    invoiceDateTf.setText("");
                    customerNameTf.setText("");
                    return;
                }
                var invoice = tableData.getInvoices().get(invoicesJTable.getSelectedRow());

                List<Item> items = invoice.getItems();
                for (int i = 0; i < items.size(); i++) {
                    Item item = items.get(i);
                    tableModel.addRow(new String[]{String.valueOf(i+1), item.getName(), String.valueOf(item.getPrice()), String.valueOf(item.getCount()), String.valueOf(item.getTotal())});
                }
                invoiceItemsJTable.setModel(tableModel);
                invNoLabel.setText("Invoice Number "+ invoice.getNumber());
                invTotalLabel.setText("Invoice Total "+ invoice.getTotal());
                invoiceDateTf.setText(invoice.getDate());
                customerNameTf.setText(invoice.getName());
            }
}
