package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateInvAction implements ActionListener {
    JTable invoicesJTable;
    public CreateInvAction(JTable jTable) {
        invoicesJTable = jTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((DefaultTableModel)(invoicesJTable.getModel())).addRow(new String[]{"", "", "", ""});
    }
}
