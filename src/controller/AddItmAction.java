package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItmAction implements ActionListener {
    JTable invoiceItemsJTable;
    public AddItmAction(JTable jTable) {
        invoiceItemsJTable = jTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((DefaultTableModel)(invoiceItemsJTable.getModel())).addRow(new String[]{"", "", "", "", ""});
    }
}
