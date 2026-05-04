package ui;

import model.Contact;
import service.ContactManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {

    // Backend
    private final ContactManager manager = new ContactManager();

    // Input fields
    private JTextField nameField;
    private JTextField phoneField;

    // Buttons
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton updateBtn;
    private JButton searchBtn;
    private JButton clearBtn;

    // Table
    private DefaultTableModel tableModel;
    private JTable contactTable;

    // Status bar
    private JLabel statusLabel;

    public MainGUI() {
        setTitle("Telephone Directory");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
        attachListeners();
        refreshTable(); // ← shows "Total contacts: 0" on startup

        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));

        // ── NORTH: input + buttons ────────────────────
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // Row 1 — input fields
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        inputRow.setBorder(BorderFactory.createTitledBorder("Contact Details"));

        JLabel nameLabel  = new JLabel("Name:");
        nameField         = new JTextField(16);
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField        = new JTextField(16);

        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        phoneLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        phoneField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        inputRow.add(nameLabel);
        inputRow.add(nameField);
        inputRow.add(phoneLabel);
        inputRow.add(phoneField);

        // Row 2 — buttons
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        buttonRow.setBorder(BorderFactory.createTitledBorder("Actions"));

        addBtn    = new JButton("Add Contact");
        deleteBtn = new JButton("Delete Contact");
        updateBtn = new JButton("Update Contact");
        searchBtn = new JButton("Search");
        clearBtn  = new JButton("Clear Fields");

        styleButton(addBtn,    new Color(39, 174, 96),   Color.WHITE);
        styleButton(deleteBtn, new Color(231, 76, 60),   Color.WHITE);
        styleButton(updateBtn, new Color(230, 126, 34),  Color.WHITE);
        styleButton(searchBtn, new Color(52, 152, 219),  Color.WHITE);
        styleButton(clearBtn,  new Color(127, 140, 141), Color.WHITE);

        buttonRow.add(addBtn);
        buttonRow.add(deleteBtn);
        buttonRow.add(updateBtn);
        buttonRow.add(searchBtn);
        buttonRow.add(clearBtn);

        topPanel.add(inputRow);
        topPanel.add(buttonRow);

        // ── CENTER: table ─────────────────────────────
        String[] columns = {"Name", "Phone Number"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactTable.setRowHeight(26);
        contactTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        contactTable.setGridColor(new Color(220, 220, 220));
        contactTable.setShowGrid(true);

        contactTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13));
        contactTable.getTableHeader().setBackground(
                new Color(52, 152, 219));
        contactTable.getTableHeader().setForeground(Color.WHITE);
        contactTable.getTableHeader().setReorderingAllowed(false);

        contactTable.setDefaultRenderer(Object.class,
                new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int col) {
                        Component c = super.getTableCellRendererComponent(
                                table, value, isSelected, hasFocus, row, col);
                        if (!isSelected) {
                            c.setBackground(row % 2 == 0
                                    ? Color.WHITE
                                    : new Color(235, 245, 255));
                        }
                        return c;
                    }
                });

        JScrollPane scrollPane = new JScrollPane(contactTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Contacts"));

        // ── SOUTH: status bar ─────────────────────────
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        statusPanel.setBackground(new Color(236, 240, 241));
        statusPanel.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, new Color(189, 195, 199)));

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(80, 80, 80));
        statusPanel.add(statusLabel);

        add(topPanel,    BorderLayout.NORTH);
        add(scrollPane,  BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void attachListeners() {
        addBtn.addActionListener(e -> handleAdd());
        deleteBtn.addActionListener(e -> handleDelete());
        updateBtn.addActionListener(e -> handleUpdate());
        searchBtn.addActionListener(e -> handleSearch());
        clearBtn.addActionListener(e -> {
            clearFields();
            setStatus("Fields cleared");
        });

        // Click table row → fill input fields automatically
        contactTable.getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        int row = contactTable.getSelectedRow();
                        if (row >= 0) {
                            nameField.setText(
                                    (String) tableModel.getValueAt(row, 0));
                            phoneField.setText(
                                    (String) tableModel.getValueAt(row, 1));
                        }
                    }
                });
    }

    // ── Add Handler ───────────────────────────────────
    private void handleAdd() {
        String name  = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean added = manager.addContact(name, phone);

        if (added) {
            JOptionPane.showMessageDialog(this,
                    "Contact added successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
            clearFields();
            setStatus("Added: " + name);
        } else {
            JOptionPane.showMessageDialog(this,
                    "This name is already used. Please enter a unique name.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Delete Handler ────────────────────────────────
    private void handleDelete() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            int row = contactTable.getSelectedRow();
            if (row >= 0) {
                name = (String) tableModel.getValueAt(row, 0);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete \"" + name + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = manager.deleteContact(name);

            if (deleted) {
                JOptionPane.showMessageDialog(this,
                        "Contact deleted successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                clearFields();
                setStatus("Deleted: " + name);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Contact not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Update Handler ────────────────────────────────
    private void handleUpdate() {
        String name     = nameField.getText().trim();
        String newPhone = phoneField.getText().trim();

        if (name.isEmpty() || newPhone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean updated = manager.updateContact(name, newPhone);

        if (updated) {
            JOptionPane.showMessageDialog(this,
                    "Contact updated successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
            clearFields();
            setStatus("Updated: " + name);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Contact not found.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Search Handler ────────────────────────────────
    private void handleSearch() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact found = manager.searchContact(name);

        if (found != null) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).toString()
                        .equalsIgnoreCase(name)) {
                    contactTable.setRowSelectionInterval(i, i);
                    contactTable.scrollRectToVisible(
                            contactTable.getCellRect(i, 0, true));
                    break;
                }
            }
            phoneField.setText(found.getPhoneNumber());
            JOptionPane.showMessageDialog(this,
                    "Contact Found!\n\n" +
                            "Name:  " + found.getName() + "\n" +
                            "Phone: " + found.getPhoneNumber(),
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE);
            setStatus("Found: " + found.getName());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Contact not found.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Helpers ───────────────────────────────────────
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Contact> all = manager.getAllContacts();
        for (Contact c : all) {
            tableModel.addRow(new Object[]{c.getName(), c.getPhoneNumber()});
        }
        // ← Phase 13: show live contact count
        statusLabel.setText("Total contacts: " + all.size());
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        nameField.requestFocus();
    }

    private void setStatus(String msg) {
        statusLabel.setText(msg);
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
    }
}