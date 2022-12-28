package controller;

import model.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadAction implements ActionListener {
    private static String loadHeaderPath;
    private static String loadLinePath;
    Table tableData;
    JMenuItem save;
    JTable invoicesJTable;
    public LoadAction(Table table, JMenuItem menuItem, JTable invoicesJTable) {
        tableData = table;
        save = menuItem;
        this.invoicesJTable = invoicesJTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Select the Header file first, then the Line file", "Load files", JOptionPane.PLAIN_MESSAGE);
        JFileChooser fhc = new JFileChooser();
        if (fhc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            loadHeaderPath = fhc.getSelectedFile().getPath();
        }
        JFileChooser flc = new JFileChooser();
        if (flc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            loadLinePath = flc.getSelectedFile().getPath();
        }
        tableData.loadInvoices (loadHeaderPath, loadLinePath);
        invoicesJTable.setModel(Actions.refreshInvoicesJTable(tableData.getInvoices()));
        Actions.setHeaderPathForSave(loadHeaderPath);
        Actions.setLinePathForSave(loadLinePath);
//        save.setEnabled(true);
    }

    public static String getLoadHeaderPath() {
        return loadHeaderPath;
    }

    public static String getLoadLinePath() {
        return loadLinePath;
    }
}
