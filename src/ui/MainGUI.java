package ui;

import model.Contact;
import service.ContactManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

// ── Color Palette ─────────────────────────────────
// Improved by AMMAR ABDUREHMAN
// Added semantic color naming and hover variants
public class MainGUI extends JFrame {

    // ── Color Palette ─────────────────────────────────
    private static final Color BG_MAIN      = new Color(245, 247, 250);
    private static final Color BG_CARD      = Color.WHITE;
    private static final Color BG_INPUT     = new Color(250, 251, 253);
    private static final Color BG_TABLE_ROW = Color.WHITE;
    private static final Color BG_TABLE_ALT = new Color(248, 250, 253);
    private static final Color BG_TABLE_SEL = new Color(235, 244, 255);
    private static final Color ACCENT_BLUE  = new Color(37,  99,  235);
    private static final Color ACCENT_GREEN = new Color(22,  163, 74);
    private static final Color ACCENT_ORG   = new Color(234, 88,  12);
    private static final Color ACCENT_RED   = new Color(220, 38,  38);
    private static final Color ACCENT_PURP  = new Color(124, 58,  237);
    private static final Color TEXT_TITLE   = new Color(10,  15,  30);
    private static final Color TEXT_PRIMARY = new Color(30,  41,  59);
    private static final Color TEXT_MUTED   = new Color(100, 116, 139);
    private static final Color TEXT_DIM     = new Color(148, 163, 184);
    private static final Color BORDER_COL   = new Color(218, 225, 234);

    // ── Avatar Colors ─────────────────────────────────
    private static final Color[] AV_COLORS = {
            new Color(79,  70,  229),
            new Color(16, 185, 129),
            new Color(245, 158, 11),
            new Color(239, 68,  68),
            new Color(59, 130, 246),
            new Color(236, 72, 153),
            new Color(20, 184, 166),
            new Color(168, 85, 247),
    };

    // ── Fonts ─────────────────────────────────────────
    private static final Font FONT_TITLE    =
            new Font("Segoe UI", Font.BOLD,  26);
    private static final Font FONT_SUBTITLE =
            new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SECTION  =
            new Font("Segoe UI", Font.BOLD,  16);
    private static final Font FONT_STAT_NUM =
            new Font("Segoe UI", Font.BOLD,  32);
    private static final Font FONT_STAT_LBL =
            new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BTN      =
            new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_INPUT    =
            new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_LABEL    =
            new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_TABLE    =
            new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_TH       =
            new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_BADGE    =
            new Font("Segoe UI", Font.BOLD,  11);
    private static final Font FONT_STATUS   =
            new Font("Segoe UI", Font.BOLD,  12);

    // ── Backend ───────────────────────────────────────
    private final ContactManager manager = new ContactManager();

    // ── Fields ────────────────────────────────────────
    private JTextField nameField;
    private JTextField phoneField;

    // ── Buttons ───────────────────────────────────────
    private JButton addBtn;
    private JButton searchBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton clearBtn;

    // ── Table ─────────────────────────────────────────
    private DefaultTableModel tableModel;
    private JTable contactTable;

    // ── Stats + Status ────────────────────────────────
    private JLabel totalVal;
    private JLabel lastVal;
    private JLabel statusLbl;
    private JLabel cntBadge;

    // ─────────────────────────────────────────────────
    public MainGUI() {
        setTitle("Telephone Directory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 920);
        setMinimumSize(new Dimension(800, 750));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout());
        buildUI();
        attachListeners();
        refreshTable();
        setVisible(true);
    }

    // ─────────────────────────────────────────────────
    //  BUILD UI
    // ─────────────────────────────────────────────────
    private void buildUI() {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(BG_MAIN);

        root.add(buildHeader());
        root.add(gap(16));
        root.add(buildStats());
        root.add(gap(16));
        root.add(buildForm());
        root.add(gap(16));
        root.add(buildTable());
        root.add(gap(20));

        JScrollPane scroll = new JScrollPane(root);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_MAIN);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll,        BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    // ─────────────────────────────────────────────────
    //  HEADER
    // ─────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(BG_CARD);
        hdr.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, BORDER_COL),
                new EmptyBorder(18, 28, 18, 28)
        ));

        // Left — logo + text
        JPanel left = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 16, 0));
        left.setBackground(BG_CARD);

        // Logo
        JPanel logo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(37, 99, 235),
                        getWidth(), getHeight(), new Color(99, 102, 241));
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
                // Draw phone handset icon using Java2D
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int cx = getWidth() / 2, cy = getHeight() / 2;
                // Phone body (rounded rect)
                g2.fillRoundRect(cx - 9, cy - 13, 18, 26, 5, 5);
                g2.setColor(new Color(37, 99, 235));
                // Screen area
                g2.fillRoundRect(cx - 6, cy - 10, 12, 14, 2, 2);
                g2.setColor(Color.WHITE);
                // Home button dot
                g2.fillOval(cx - 2, cy + 6, 4, 4);
                g2.dispose();
            }
        };
        logo.setPreferredSize(new Dimension(52, 52));
        logo.setOpaque(false);

        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setBackground(BG_CARD);

        JLabel t1 = new JLabel("Telephone Directory");
        t1.setFont(FONT_TITLE);
        t1.setForeground(TEXT_TITLE);

        JLabel t2 = new JLabel("Contact Management System");
        t2.setFont(FONT_SUBTITLE);
        t2.setForeground(TEXT_MUTED);

        txt.add(t1);
        txt.add(Box.createVerticalStrut(2));
        txt.add(t2);

        left.add(logo);
        left.add(txt);

        // Right — badge
        JLabel badge = new JLabel(
                "  \u25CF  MySQL Connected  ");
        badge.setFont(FONT_STATUS);
        badge.setForeground(new Color(21, 128, 61));
        badge.setOpaque(true);
        badge.setBackground(new Color(220, 252, 231));
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(134, 239, 172), 1),
                new EmptyBorder(6, 6, 6, 6)
        ));

        JPanel right = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 0, 10));
        right.setBackground(BG_CARD);
        right.add(badge);

        hdr.add(left,  BorderLayout.WEST);
        hdr.add(right, BorderLayout.EAST);
        return hdr;
    }
}
    
 // ─────────────────────────────────────────────────
    //  Improved by HAYAT SHEKUR
    // ─────────────────────────────────────────────────
    private JPanel buildStats() {
        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setBackground(BG_MAIN);
        row.setBorder(new EmptyBorder(0, 28, 0, 28));

        totalVal = new JLabel("0");
        lastVal  = new JLabel("—");

        row.add(statCard(
                "Total Contacts",
                totalVal, "in directory",
                ACCENT_BLUE,
                new Color(219, 234, 254),
                new Color(239, 246, 255),
                "people"));

        row.add(statCard(
                "Last Added",
                lastVal, "most recent",
                ACCENT_GREEN,
                new Color(134, 239, 172),
                new Color(240, 253, 244),
                "clock"));

        row.add(statCard(
                "Database",
                new JLabel("Active"),
                "telephone_directory",
                ACCENT_ORG,
                new Color(253, 186, 116),
                new Color(255, 247, 237),
                "database"));

        return row;
    }

    private JPanel statCard(String label, JLabel valLbl,
                            String sub, Color accent,
                            Color iconBorder, Color iconBg,
                            String iconType) {
        JPanel card = new CardPanel(16, BG_CARD);
        card.setLayout(new BorderLayout(0, 0));
        card.setBorder(new EmptyBorder(20, 22, 20, 22));

        // Text
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(BG_CARD);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_STAT_LBL);
        lbl.setForeground(TEXT_MUTED);

        valLbl.setFont(FONT_STAT_NUM);
        valLbl.setForeground(TEXT_TITLE);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLbl.setForeground(TEXT_DIM);

        col.add(lbl);
        col.add(Box.createVerticalStrut(6));
        col.add(valLbl);
        col.add(Box.createVerticalStrut(4));
        col.add(subLbl);

        // Icon box — draws background + icon via Java2D
        JPanel iconBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                // Background rounded rect
                g2.setColor(iconBg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
                g2.setColor(iconBorder);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 14, 14));
                // Draw icon
                g2.setColor(accent);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int cx = getWidth() / 2, cy = getHeight() / 2;
                if ("people".equals(iconType)) {
                    // Two person silhouettes
                    // Back person (right)
                    g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 140));
                    g2.fillOval(cx + 1, cy - 13, 10, 10);
                    g2.fillRoundRect(cx - 1, cy - 2, 14, 12, 6, 6);
                    // Front person (left)
                    g2.setColor(accent);
                    g2.fillOval(cx - 10, cy - 13, 10, 10);
                    g2.fillRoundRect(cx - 13, cy - 2, 14, 12, 6, 6);
                } else if ("clock".equals(iconType)) {
                    // Clock circle
                    g2.setColor(iconBg);
                    g2.fillOval(cx - 11, cy - 11, 22, 22);
                    g2.setColor(accent);
                    g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawOval(cx - 11, cy - 11, 22, 22);
                    // Clock hands
                    g2.drawLine(cx, cy, cx, cy - 7);   // hour hand up
                    g2.drawLine(cx, cy, cx + 6, cy);   // minute hand right
                    // Center dot
                    g2.fillOval(cx - 2, cy - 2, 4, 4);
                } else if ("database".equals(iconType)) {
                    // Database cylinder
                    int dw = 20, dh = 6, dx = cx - dw/2;
                    // Top ellipse
                    g2.setColor(iconBg);
                    g2.fillOval(dx, cy - 12, dw, dh);
                    g2.setColor(accent);
                    g2.drawOval(dx, cy - 12, dw, dh);
                    // Body
                    g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 60));
                    g2.fillRect(dx, cy - 9, dw, 18);
                    g2.setColor(accent);
                    g2.drawLine(dx, cy - 9, dx, cy + 9);
                    g2.drawLine(dx + dw, cy - 9, dx + dw, cy + 9);
                    // Middle ellipse
                    g2.setColor(iconBg);
                    g2.fillOval(dx, cy - 3, dw, dh);
                    g2.setColor(accent);
                    g2.drawOval(dx, cy - 3, dw, dh);
                    // Bottom ellipse
                    g2.setColor(iconBg);
                    g2.fillOval(dx, cy + 6, dw, dh);
                    g2.setColor(accent);
                    g2.drawOval(dx, cy + 6, dw, dh);
                }
                g2.dispose();
            }
        };
        iconBox.setPreferredSize(new Dimension(52, 52));
        iconBox.setOpaque(false);

        JPanel iw = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        iw.setBackground(BG_CARD);
        iw.add(iconBox);

        card.add(col, BorderLayout.CENTER);
        card.add(iw,  BorderLayout.EAST);
        return card;
    }