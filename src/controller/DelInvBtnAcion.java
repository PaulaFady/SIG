package controller;

import model.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelInvBtnAcion implements ActionListener {
    Table tableData;
    JTable invoicesJTable;
    public DelInvBtnAcion(Table table, JTable jTable) {
        tableData = table;
        invoicesJTable = jTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tableData.deleteInvoice(invoicesJTable.getSelectedRow());
        invoicesJTable.setModel(Actions.refreshInvoicesJTable(tableData.getInvoices()));
    }
}
