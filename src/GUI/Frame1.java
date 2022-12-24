package GUI;

import lib.Item;
import lib.Table;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Frame1 extends JFrame implements ActionListener {

    private JTable invoicesTable;
    private JTable invoiceItemsTable;
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
                var tableModel = new DefaultTableModel();
                tableModel.addColumn("No.");
                tableModel.addColumn("Date");
                tableModel.addColumn("Customer");
                tableModel.addColumn("Total");

                for (lib.Invoice invoice : invoices) {
                    tableModel.addRow(new String[]{String.valueOf(invoice.getNumber()), invoice.getDate(), invoice.getName(), String.valueOf(invoice.getTotal())});
                }
                invoicesTable.setModel(tableModel);
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

        invoicesTable = new JTable(invTableData, invTableCols);
        invoicesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                var tableModel = new DefaultTableModel();
                tableModel.addColumn("No.");
                tableModel.addColumn("Item Name");
                tableModel.addColumn("Item Price");
                tableModel.addColumn("Count");
                tableModel.addColumn("Item Total");

                if(invoicesTable.getSelectedRow() < 0 || invoicesTable.getSelectedRow() >= tableData.getInvoices().size()){
                    invoiceItemsTable.setModel(tableModel);
                    invNoLabel.setText("Invoice Number ");
                    invTotalLabel.setText("Invoice Total ");
                    invoiceDateTf.setText("");
                    customerNameTf.setText("");
                    return;
                }
                var invoice = tableData.getInvoices().get(invoicesTable.getSelectedRow());

                List<Item> items = invoice.getItems();
                for (int i = 0; i < items.size(); i++) {
                    Item item = items.get(i);
                    tableModel.addRow(new String[]{String.valueOf(i+1), item.getName(), String.valueOf(item.getPrice()), String.valueOf(item.getCount()), String.valueOf(item.getTotal())});
                }
                invoiceItemsTable.setModel(tableModel);
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
            tableData.deleteInvoice(invoicesTable.getSelectedRow());
                var tableModel = new DefaultTableModel();
                tableModel.addColumn("No.");
                tableModel.addColumn("Date");
                tableModel.addColumn("Customer");
                tableModel.addColumn("Total");

                for (lib.Invoice invoice : tableData.getInvoices()) {
                    tableModel.addRow(new String[]{String.valueOf(invoice.getNumber()), invoice.getDate(), invoice.getName(), String.valueOf(invoice.getTotal())});
                }
                invoicesTable.setModel(tableModel);

            }
        });
        createNewInvoice = new JButton("Create New Invoice");
        createNewInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((DefaultTableModel)(invoicesTable.getModel())).addRow(new String[]{"", "", "", ""});
            }
        });
        invoiceDataPanel5.add(deleteInvoice);
        invoiceDataPanel5.add(createNewInvoice);
        leftPanel.add(new JLabel("Invoices Table"));
        leftPanel.add(new JScrollPane(invoicesTable));
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
                ((DefaultTableModel)(invoiceItemsTable.getModel())).addRow(new String[]{"", "", "", "", ""});
            }
        });
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Edit Table
            if(invoicesTable.getSelectedRow() <= tableData.getInvoices().size()){
                List<Item> itemsFromGui = new ArrayList<>(invoiceItemsTable.getRowCount());
                for(int i=0; i< invoiceItemsTable.getRowCount(); i++){
                    itemsFromGui.get(i).setName((invoiceItemsTable.getModel().getValueAt(i,1)).toString());
                    itemsFromGui.get(i).setPrice(Double.parseDouble((invoiceItemsTable.getModel().getValueAt(i,2)).toString()));
                    itemsFromGui.get(i).setCount(Integer.parseInt((invoiceItemsTable.getModel().getValueAt(i,3)).toString()));
                }

                tableData.editInvoiceByIndex(invoicesTable.getSelectedRow(), invoiceDateTf.getText(), customerNameTf.getText(), itemsFromGui);


            }
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
                invoiceItemsTable.setModel(tableModel);
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
        invoiceItemsTable = new JTable(invItemsTableData, invItemsTableCols);
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
        rightPanel.add(new JScrollPane(invoiceItemsTable));
        invoiceItemsPanel.add(addItem);
        invoiceItemsPanel.add(saveBtn);
        invoiceItemsPanel.add(cancel);
        rightPanel.add(invoiceItemsPanel);
        add(rightPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
