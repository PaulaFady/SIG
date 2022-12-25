package GUI;

import lib.Invoice;
import lib.Item;
import lib.Table;

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

public class Frame1 extends JFrame implements ActionListener {

    private JTable invoicesJTable;
    private JTable invoiceItemsJTable;
    private JMenuBar menuBar;
    private JMenuItem load;
    private JMenuItem save;

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
    private JPanel invoiceDataPanel5 = new JPanel();

    private JPanel invoiceItemsPanel = new JPanel();
    private JTextField invoiceDateTf;
    private JTextField customerNameTf;
    private Table tableData;
    JLabel invNoLabel = new JLabel("Invoice Number ");
    JLabel invTotalLabel = new JLabel("Invoice Total ");


//Constructor
    public Frame1() {
        super("Sales Invoice Generator");
        tableData = new Table();
        setSize(1000, 550);
        setLocation(200, 50);
//Menu
        load = new JMenuItem("Load File",'l');
        load.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        save = new JMenuItem("Save File",'s');
        save.setAccelerator(KeyStroke.getKeyStroke('S',KeyEvent.CTRL_DOWN_MASK));
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(load);
        fileMenu.add(save);
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableData = new Table();
                var invoices = tableData.getInvoices();
                refreshInvoicesJTable(invoices);
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
//Left Panel
        leftPanel.setBounds(0,0,350,500);
        String[] invTableCols = {"No.", "Date", "Customer", "Total"};
        String[][] invTableData = {};

        invoicesJTable = new JTable(invTableData, invTableCols) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        }
        ;
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
        deleteInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            tableData.deleteInvoice(invoicesJTable.getSelectedRow());
                refreshInvoicesJTable(tableData.getInvoices());

            }
        });
        createNewInvoice = new JButton("Create New Invoice");
        createNewInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DefaultTableModel)(invoicesJTable.getModel())).addRow(new String[]{"", "", "", ""});
            }
        });
        invoiceDataPanel5.add(deleteInvoice);
        invoiceDataPanel5.add(createNewInvoice);
        leftPanel.add(new JLabel("Invoices Table"));
        leftPanel.add(new JScrollPane(invoicesJTable));
        leftPanel.add(invoiceDataPanel5);
//        Border blackline = BorderFactory.createLineBorder(Color.black);
//        leftPanel.setBorder(blackline);
        add(leftPanel);

//Right Panel
        rightPanel.setBounds(350,0,350,500);
        rightPanel.setLayout(new FlowLayout());
        invoiceDataPanel1.setBounds(355,5,345,50);
        invoiceDataPanel2.setBounds(355,60,345,50);
        invoiceDataPanel3.setBounds(355,115,345,50);
        invoiceDataPanel4.setBounds(355,170,345,50);
        addItem = new JButton("Add Item");
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DefaultTableModel)(invoiceItemsJTable.getModel())).addRow(new String[]{"", "", "", "", ""});
            }
        });
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Edit Table
                int index = invoicesJTable.getSelectedRow();
                String date = invoiceDateTf.getText();
                String cusName = customerNameTf.getText();
                Invoice invoice;
                if(index < tableData.getInvoices().size() && index >=0){
                    invoice = tableData.getInvoices().get(index);
                    invoice.setName(cusName);
                    invoice.setDate(date);
                } else{
                    if (tableData == null){
                        tableData = new Table();
                    }
                    invoice = new Invoice(tableData.getInvoices().size()+1, date, cusName);
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
            });

        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
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
        });
//        rightPanel.setBorder(blackline);
        invoiceDateTf = new JTextField(15);
        customerNameTf = new JTextField(15);
        String[] invItemsTableCols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
        String[][] invItemsTableData = {};
        invoiceItemsJTable = new JTable(invItemsTableData, invItemsTableCols);
        invoiceItemsJTable.setModel(new DefaultTableModel());
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
        invoiceItemsPanel.add(addItem);
        invoiceItemsPanel.add(saveBtn);
        invoiceItemsPanel.add(cancel);
        rightPanel.add(invoiceItemsPanel);
        add(rightPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

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

    }
}
