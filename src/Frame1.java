import lib.Table;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Frame1 extends JFrame implements ActionListener {
    private JTable invoicesTable;
    private JTable invoiceItemsTable;
    private JMenuBar menuBar;
    private JMenuItem load;
    private JMenuItem save;

    private JButton createNewInvoice;
    private JButton deleteInvoice;
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


//Constructor
    public Frame1() {
        super("Sales Invoice Generator");
        setSize(1000, 650);
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
        String[][] invTableData = {
                {"1", "22-11-2020", "Ali", "1500"},
                {"2", "13-10-2021", "Saleh", "500"},
                {"","","",""}
        };

        invoicesTable = new JTable(invTableData, invTableCols);
        deleteInvoice = new JButton("Delete Invoice");
//        deleteInvoice.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
        createNewInvoice = new JButton("Create New Invoice");
        invoiceDataPanel5.add(deleteInvoice);
        invoiceDataPanel5.add(createNewInvoice);
        leftPanel.add(new JLabel("Invoices Table"));
        leftPanel.add(new JScrollPane(invoicesTable));
        leftPanel.add(invoiceDataPanel5);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        leftPanel.setBorder(blackline);
        add(leftPanel);

//Right Panel
        rightPanel.setBounds(350,0,350,500);
        rightPanel.setLayout(new FlowLayout());
        invoiceDataPanel1.setBounds(355,5,345,50);
        invoiceDataPanel2.setBounds(355,60,345,50);
        invoiceDataPanel3.setBounds(355,115,345,50);
        invoiceDataPanel4.setBounds(355,170,345,50);
        saveBtn = new JButton("Save");
        cancel = new JButton("Cancel");
        rightPanel.setBorder(blackline);
        invoiceDateTf = new JTextField(15);
        customerNameTf = new JTextField(15);
        String[] invItemsTableCols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
        String[][] invItemsTableData = {
                {"","","","",""},
                {"","","","",""},
                {"","","","",""},
                {"","","","",""}
        };
        invoiceItemsTable = new JTable(invItemsTableData, invItemsTableCols);
        invoiceDataPanel1.add(new JLabel("Invoice Number "));
        invoiceDataPanel2.add(new JLabel("Invoice Date "));
        invoiceDataPanel2.add(invoiceDateTf);
        invoiceDataPanel3.add(new JLabel("Customer Name "));
        invoiceDataPanel3.add(customerNameTf);
        invoiceDataPanel4.add(new JLabel("Invoice Total "+"123"));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(invoiceDataPanel1);
        rightPanel.add(invoiceDataPanel2);
        rightPanel.add(invoiceDataPanel3);
        rightPanel.add(invoiceDataPanel4);
        rightPanel.add(new JLabel("Invoice Items"));
        rightPanel.add(new JScrollPane(invoiceItemsTable));
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
