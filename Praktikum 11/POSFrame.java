import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class POSFrame extends JFrame {
    private final List<Product> products = new ArrayList<>();
    private final List<CartItem> cart = new ArrayList<>();

    private JTable productTable;
    private JTable cartTable;
    private JTextField qtyField;
    private JLabel selectedLabel;
    private JLabel totalLabel;
    private JLabel pointsLabel;
    private JTextArea receiptArea;
    private DefaultTableModel productModel;
    private DefaultTableModel cartModel;

    private JButton addButton;
    private JButton checkoutButton;
    private JButton printButton;

    public POSFrame() {
        setTitle("POIN Off-Sales - Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenuBar();
        initProductData();
        initComponents();
        bindEvents();

        setSize(1000, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e ->
            JOptionPane.showMessageDialog(
                this,
                "POIN Off-Sales - Praktikum Java Swing",
                "About",
                JOptionPane.INFORMATION_MESSAGE
            )
        );
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void initProductData() {
        products.add(new Product("P001", "Air Mineral 600ml", 3000));
        products.add(new Product("P002", "Kopi Sachet", 5000));
        products.add(new Product("P003", "Roti isi", 8000));
        products.add(new Product("P004", "Snack Keripik", 6000));
        products.add(new Product("P005", "Minuman Botol", 12000));
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel productPanel = new JPanel(new BorderLayout(5, 5));
        productPanel.setBorder(BorderFactory.createTitledBorder("Produk"));

        productModel = new DefaultTableModel(
            new Object[] {"ID", "Nama Produk", "Harga (Rp)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Product p : products) {
            productModel.addRow(new Object[] {
                p.getId(), p.getName(), p.getPrice()
            });
        }

        productTable = new JTable(productModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel productSouthPanel = new JPanel(new BorderLayout(5, 5));
        productSouthPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        selectedLabel = new JLabel("Pilih produk dari tabel di kiri");
        selectedLabel.setFont(selectedLabel.getFont().deriveFont(Font.BOLD));

        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        qtyPanel.add(new JLabel("Qty:"));
        qtyField = new JTextField("1", 4);
        addButton = new JButton("Add to Cart");
        qtyPanel.add(qtyField);
        qtyPanel.add(addButton);

        productSouthPanel.add(selectedLabel, BorderLayout.NORTH);
        productSouthPanel.add(qtyPanel, BorderLayout.SOUTH);
        productPanel.add(productSouthPanel, BorderLayout.SOUTH);

        JPanel cartPanel = new JPanel(new BorderLayout(5, 5));
        cartPanel.setBorder(BorderFactory.createTitledBorder("Keranjang"));

        cartModel = new DefaultTableModel(
            new Object[] {"ID", "Nama Produk", "Qty", "Harga", "Subtotal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(cartModel);
        cartPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel cartSouthPanel = new JPanel(new BorderLayout(5, 8));
        cartSouthPanel.setBorder(new EmptyBorder(8, 8, 5, 8));

        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBorder(new EmptyBorder(0, 45, 0, 0));

        totalLabel = new JLabel("Total: Rp0,00");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        pointsLabel = new JLabel("Points: 0");
        pointsLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        checkoutButton = new JButton("Checkout");
        printButton = new JButton("Cetak");

        JLabel strukTitleLabel = new JLabel("Struk:");
        strukTitleLabel.setFont(strukTitleLabel.getFont().deriveFont(Font.BOLD));

        summaryPanel.add(totalLabel);
        summaryPanel.add(Box.createVerticalStrut(4));
        summaryPanel.add(pointsLabel);
        summaryPanel.add(Box.createVerticalStrut(8));
        summaryPanel.add(checkoutButton);
        summaryPanel.add(Box.createVerticalStrut(4));
        summaryPanel.add(printButton);
        summaryPanel.add(Box.createVerticalStrut(8));
        summaryPanel.add(strukTitleLabel);

        receiptArea = new JTextArea(8, 30);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        cartSouthPanel.add(summaryPanel, BorderLayout.NORTH);
        cartSouthPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        cartPanel.add(cartSouthPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        mainPanel.add(productPanel);
        mainPanel.add(cartPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void bindEvents() {
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = productTable.getSelectedRow();
                if (row >= 0) {
                    Product selected = products.get(row);
                    selectedLabel.setText(
                        "Dipilih: " + selected.getId() +
                        " - " + selected.getName() +
                        " (" + formatRupiah(selected.getPrice()) + ")"
                    );
                }
            }
        });

        addButton.addActionListener(e -> addSelectedProductToCart());
        checkoutButton.addActionListener(e -> checkout());
        printButton.addActionListener(e -> printReceipt());
    }

    private void addSelectedProductToCart() {
        int row = productTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih produk terlebih dahulu.",
                "Produk belum dipilih",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyField.getText().trim());
            if (qty <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Qty harus berupa bilangan bulat lebih dari 0.",
                "Input tidak valid",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Product selected = products.get(row);
        cart.add(new CartItem(selected, qty));
        refreshCartTable();
        updateSummary();
    }

    private void refreshCartTable() {
        cartModel.setRowCount(0);
        for (CartItem item : cart) {
            Product p = item.getProduct();
            cartModel.addRow(new Object[] {
                p.getId(),
                p.getName(),
                item.getQty(),
                p.getPrice(),
                item.getSubtotal()
            });
        }
    }

    private void updateSummary() {
        int total = 0;
        for (CartItem item : cart) {
            total += item.getSubtotal();
        }
        int points = total / 1000;

        totalLabel.setText("Total: " + formatRupiah(total));
        pointsLabel.setText("Points: " + points);
    }

    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Keranjang masih kosong.",
                "Checkout gagal",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int total = 0;
        StringBuilder receipt = new StringBuilder();
        receipt.append("===== POIN OFF-SALES =====\n");
        receipt.append("Toko: Demo Toko\n");
        receipt.append("========================================\n");
        receipt.append(String.format("%-5s %-17s %3s %12s\n", "ID", "Nama", "Qty", "Subtotal"));

        for (CartItem item : cart) {
            Product p = item.getProduct();
            int subtotal = item.getSubtotal();
            total += subtotal;

            receipt.append(String.format(
                "%-5s %-17s %3d %12s\n",
                p.getId(),
                p.getName(),
                item.getQty(),
                formatRupiah(subtotal)
            ));
        }

        int points = total / 1000;
        receipt.append("----------------------------------------\n");
        receipt.append(String.format("%-16s %s\n", "TOTAL:", formatRupiah(total)));
        receipt.append("POINTS DIDAPAT: " + points + " (1 point per Rp 1000)\n");
        receipt.append("========================================\n");
        receipt.append("Terima kasih! Silakan kunjungi kembali.");

        receiptArea.setText(receipt.toString());

        cart.clear();
        refreshCartTable();
        updateSummary();
    }

    private void printReceipt() {
        if (receiptArea.getText().isBlank()) {
            JOptionPane.showMessageDialog(
                this,
                "Belum ada struk yang dapat dicetak."
            );
            return;
        }
        try {
            receiptArea.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal mencetak: " + ex.getMessage()
            );
        }
    }

    private String formatRupiah(int amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("Rp#,##0.00", symbols);
        return df.format(amount);
    }
}