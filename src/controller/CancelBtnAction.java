package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelBtnAction implements ActionListener {
    JTable invoiceItemsJTable;
    JLabel invNoLabel;
    JLabel invTotalLabel;
    JTextField invoiceDateTf;
    JTextField customerNameTf;


    public CancelBtnAction(JTable invItJT, JLabel invNo, JLabel invTotal, JTextField invDate, JTextField cusName) {
        invoiceItemsJTable = invItJT;
        invNoLabel = invNo;
        invTotalLabel = invTotal;
        invoiceDateTf = invDate;
        customerNameTf = cusName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var tableModel = new DefaultTableModel();
        tableModel.addColumn("No.");
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Item Price");
        tableModel.addColumn("Count");
        tableModel.addColumn("Item Total");
        invoiceItemsJTable.setModel(tableModel);
        invNoLabel.setText("Invoice Number ");
        invTotalLabel.setText("Invoice Total ");
        invoiceDateTf.setText("");
        customerNameTf.setText("");
    }
}
