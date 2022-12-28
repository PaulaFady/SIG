package controller;

import model.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveAsAction implements ActionListener {

    private static String headerFilePath;
    private static String lineFilePath;
    Table tableData;
    JMenuItem save;
    public SaveAsAction(Table table, JMenuItem save) {
        tableData = table;
        this.save = save;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Select the Header csv file destination first, then the Line file destination", "Save files as", JOptionPane.PLAIN_MESSAGE);
        JFileChooser saveHeaderPath = new JFileChooser();
        if (saveHeaderPath.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            headerFilePath = saveHeaderPath.getSelectedFile().getPath();
        }
        JFileChooser saveLinePath = new JFileChooser();
        if (saveLinePath.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            lineFilePath = saveLinePath.getSelectedFile().getPath();
        }
        Actions.writeInvoicesOnFile(tableData, headerFilePath, lineFilePath);
        Actions.setHeaderPathForSave(headerFilePath);
        Actions.setLinePathForSave(lineFilePath);
//        save.setEnabled(true);
    }
}
