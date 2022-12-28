package controller;

import model.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveAction implements ActionListener {
    Table tableData;
    public SaveAction(Table table) {
        tableData = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            Actions.writeInvoicesOnFile(tableData, Actions.getHeaderPathForSave(), Actions.getLinePathForSave());
            JOptionPane.showMessageDialog(null, "File saved successfully", "Note", JOptionPane.PLAIN_MESSAGE);
    }
}
