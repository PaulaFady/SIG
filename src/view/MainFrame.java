package view;

import controller.*;
import model.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

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
    private JLabel jLabelInvtable;
    private JLabel jLabelInvdate;
    private JLabel jLabelCusname;
    private JLabel jLabelInvitems;
    private JLabel invNoLabel = new JLabel("Invoice Number ");
    private JLabel invTotalLabel = new JLabel("Invoice Total ");


//Constructor
    public MainFrame() {
        super("Sales Invoice Generator");
        createFrameObjects();

//Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(load);
        fileMenu.add(save);
        save.setEnabled(false);
        fileMenu.add(saveAs);
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        load.addActionListener(new LoadAction(tableData, save, invoicesJTable));
        save.addActionListener(new SaveAction(tableData));
        saveAs.addActionListener(new SaveAsAction(tableData, save));


        setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
// Left Panel
        leftPanel.setBounds(0,0,350,500);
        invoicesJTable.getSelectionModel().addListSelectionListener(new InvSelectionAction(tableData, invoicesJTable, invoiceItemsJTable, invNoLabel,invTotalLabel,invoiceDateTf,customerNameTf));
        deleteInvoice.addActionListener(new DelInvBtnAcion(tableData, invoicesJTable));
        createNewInvoice.addActionListener(new CreateInvAction(invoicesJTable));

// Left Panel Layout
        leftPanel.add(jLabelInvtable);
        leftPanel.add(new JScrollPane(invoicesJTable));
        leftPanel.add(deleteInvoice);
        leftPanel.add(createNewInvoice);
        add(leftPanel);
 // Right Panel Layout
        rightPanel.setBounds(350,0,350,500);
        rightPanel.setLayout(new FlowLayout());
        addItem.addActionListener(new AddItmAction(invoiceItemsJTable));
        saveBtn.addActionListener(new SaveBtnAction(tableData, invoicesJTable, invoiceItemsJTable, invoiceDateTf, customerNameTf));
        cancel.addActionListener(new CancelBtnAction(invoiceItemsJTable, invNoLabel, invTotalLabel, invoiceDateTf, customerNameTf));

// Right Panel Layout
        invoiceDataPanel1.add(invNoLabel);
        invoiceDataPanel2.add(jLabelInvdate);
        invoiceDataPanel2.add(invoiceDateTf);
        invoiceDataPanel3.add(jLabelCusname);
        invoiceDataPanel3.add(customerNameTf);
        invoiceDataPanel4.add(invTotalLabel);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(invoiceDataPanel1);
        rightPanel.add(invoiceDataPanel2);
        rightPanel.add(invoiceDataPanel3);
        rightPanel.add(invoiceDataPanel4);
        rightPanel.add(jLabelInvitems);
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
        invoicesJTable.setModel(controller.Actions.refreshInvoicesJTable(invoices));
    }
    private void createFrameObjects(){
        tableData = new Table();
        setSize(1000, 550);
        setLocation(200, 50);

        load = new JMenuItem("Load File",'l');
        load.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        save = new JMenuItem("Save File",'s');
        save.setAccelerator(KeyStroke.getKeyStroke('S',KeyEvent.CTRL_DOWN_MASK));
        saveAs = new JMenuItem("Save As");
        int down = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        saveAs.setAccelerator(KeyStroke.getKeyStroke('S',down));
        String[] invTableCols = {"No.", "Date", "Customer", "Total"};
        String[][] invTableData = {};
        invoicesJTable = new JTable(invTableData, invTableCols) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        deleteInvoice = new JButton("Delete Invoice");
        createNewInvoice = new JButton("Create New Invoice");
        jLabelInvtable = new JLabel("Invoices Table");
        addItem = new JButton("Add Item");
        saveBtn = new JButton("Save invoice");
        cancel = new JButton("Cancel");
        invoiceDateTf = new JTextField(15);
        customerNameTf = new JTextField(15);
        String[] invItemsTableCols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
        String[][] invItemsTableData = {};
        invoiceItemsJTable = new JTable(invItemsTableData, invItemsTableCols);
        invoiceItemsJTable.setModel(new DefaultTableModel());
        jLabelInvdate = new JLabel("Invoice Date ");
        jLabelCusname = new JLabel("Customer Name ");
        jLabelInvitems = new JLabel("Invoice Items");
    }
}
