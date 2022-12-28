package view;

import controller.Actions;
import model.Invoice;
import model.Item;
import model.Table;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener {

    private JTable invoicesJTable;
    private JTable invoiceItemsJTable;
    private JMenuBar menuBar;
    private JMenuItem load;
    private JMenuItem save;
    private JMenuItem saveAs;

    private JButton createNewInvoice;
    private JButton deleteInvoice;
    private JButton addItem;
    private JButton saveBtn;
    private JButton cancel;
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel invoiceDataPanel1 = new JPanel();
    private JPanel invoiceDataPanel2 = new JPanel();
    private JPanel invoiceDataPanel3 = new JPanel();
    private JPanel invoiceDataPanel4 = new JPanel();
    private JPanel invoiceItemsButtonsPanel = new JPanel();
    private JTextField invoiceDateTf;
    private JTextField customerNameTf;
    private Table tableData;
    JLabel invNoLabel = new JLabel("Invoice Number ");
    JLabel invTotalLabel = new JLabel("Invoice Total ");
    private static String loadHeaderPath;
    private static String loadLinePath;
    private static String headerFilePath;
    private static String lineFilePath;


//Constructor
    public MainFrame() {
        super("Sales Invoice Generator");
        tableData = new Table();
        setSize(1000, 550);
        setLocation(200, 50);
//Menu
        load = new JMenuItem("Load File",'l');
        load.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        save = new JMenuItem("Save File",'s');
        save.setAccelerator(KeyStroke.getKeyStroke('S',KeyEvent.CTRL_DOWN_MASK));
        saveAs = new JMenuItem("Save As");
        int down = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        saveAs.setAccelerator(KeyStroke.getKeyStroke('S',down));
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(load);
        fileMenu.add(save);
        save.setEnabled(false);
        fileMenu.add(saveAs);
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        load.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);


        setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
// Left Panel
        leftPanel.setBounds(0,0,350,500);
        String[] invTableCols = {"No.", "Date", "Customer", "Total"};
        String[][] invTableData = {};

        invoicesJTable = new JTable(invTableData, invTableCols) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        }
        ;
// View Selected Invoice
        invoicesJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
        });
        deleteInvoice = new JButton("Delete Invoice");
        deleteInvoice.addActionListener(this);
        createNewInvoice = new JButton("Create New Invoice");
        createNewInvoice.addActionListener(this);

// Left Panel Layout
        leftPanel.add(new JLabel("Invoices Table"));
        leftPanel.add(new JScrollPane(invoicesJTable));
        leftPanel.add(deleteInvoice);
        leftPanel.add(createNewInvoice);
        add(leftPanel);
 // Right Panel Layout
        rightPanel.setBounds(350,0,350,500);
        rightPanel.setLayout(new FlowLayout());
        addItem = new JButton("Add Item");
        addItem.addActionListener(this);
        saveBtn = new JButton("Save invoice");
        saveBtn.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        invoiceDateTf = new JTextField(15);
        customerNameTf = new JTextField(15);
        String[] invItemsTableCols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
        String[][] invItemsTableData = {};
        invoiceItemsJTable = new JTable(invItemsTableData, invItemsTableCols);
        invoiceItemsJTable.setModel(new DefaultTableModel());
// Right Panel Layout
        invoiceDataPanel1.add(invNoLabel);
        invoiceDataPanel2.add(new JLabel("Invoice Date "));
        invoiceDataPanel2.add(invoiceDateTf);
        invoiceDataPanel3.add(new JLabel("Customer Name "));
        invoiceDataPanel3.add(customerNameTf);
        invoiceDataPanel4.add(invTotalLabel);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(invoiceDataPanel1);
        rightPanel.add(invoiceDataPanel2);
        rightPanel.add(invoiceDataPanel3);
        rightPanel.add(invoiceDataPanel4);
        rightPanel.add(new JLabel("Invoice Items"));
        rightPanel.add(new JScrollPane(invoiceItemsJTable));
        invoiceItemsButtonsPanel.add(addItem);
        invoiceItemsButtonsPanel.add(saveBtn);
        invoiceItemsButtonsPanel.add(cancel);
        rightPanel.add(invoiceItemsButtonsPanel);
        add(rightPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        initialLoad();
    }
    private void initialLoad(){
        tableData = new Table();
        var invoices = tableData.getInvoices();
        refreshInvoicesJTable(invoices);
    }

    private void refreshInvoicesJTable(List<Invoice> tableData) {
        var tableModel = new DefaultTableModel();
        tableModel.addColumn("No.");
        tableModel.addColumn("Date");
        tableModel.addColumn("Customer");
        tableModel.addColumn("Total");

        for (Invoice invoice : tableData) {
            tableModel.addRow(new String[]{String.valueOf(invoice.getNumber()), invoice.getDate(), invoice.getName(), String.valueOf(invoice.getTotal())});
        }
        invoicesJTable.setModel(tableModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(load)){
            JOptionPane.showMessageDialog(null, "Select the Header file first, then the Line file", "Load files", JOptionPane.PLAIN_MESSAGE);
            JFileChooser fhc = new JFileChooser();
            if (fhc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                loadHeaderPath = fhc.getSelectedFile().getPath();
            }
            JFileChooser flc = new JFileChooser();
            if (flc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                loadLinePath = flc.getSelectedFile().getPath();
            }
            tableData = new Table(loadHeaderPath, loadLinePath);
            var invoices = tableData.getInvoices();
            refreshInvoicesJTable(invoices);
            save.setEnabled(true);
        }

        else if (e.getSource().equals(save)) {
            Actions.writeInvoicesOnFile(tableData, loadHeaderPath, loadLinePath);
                JOptionPane.showMessageDialog(null, "File saved successfully", "Note", JOptionPane.PLAIN_MESSAGE);
        }

        else if (e.getSource().equals(saveAs)) {
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
        }

        else if (e.getSource().equals(deleteInvoice)) {
            tableData.deleteInvoice(invoicesJTable.getSelectedRow());
            refreshInvoicesJTable(tableData.getInvoices());
        }

        else if (e.getSource().equals(createNewInvoice)){
            ((DefaultTableModel)(invoicesJTable.getModel())).addRow(new String[]{"", "", "", ""});
        }

        else if (e.getSource().equals(addItem)) {
            ((DefaultTableModel)(invoiceItemsJTable.getModel())).addRow(new String[]{"", "", "", "", ""});
        }

        else if (e.getSource().equals(saveBtn)) {
            int index = invoicesJTable.getSelectedRow();
            String date = invoiceDateTf.getText();
            String cusName = customerNameTf.getText();
            Invoice invoice;
            // Edit Table
            if(index < tableData.getInvoices().size() && index >=0){
                invoice = tableData.getInvoices().get(index);
                invoice.setName(cusName);
                invoice.setDate(date);
                // Create Table
            } else{
                if (tableData == null){
                    tableData = new Table();
                }
                invoice = new Invoice(tableData.getInvoices().get(tableData.getInvoices().size()-1).getNumber()+1, date, cusName);
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
            refreshInvoicesJTable(tableData.getInvoices());
        }

        else if (e.getSource().equals(cancel)) {
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
    }
