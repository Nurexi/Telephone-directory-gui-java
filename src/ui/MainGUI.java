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

    // ─────────────────────────────────────────────────
    //   Improved by EMAN AHMED
    // ─────────────────────────────────────────────────
    private JPanel buildForm() {
        JPanel card = new CardPanel(16, BG_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(22, 24, 22, 24));

        // Title
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titleRow.setBackground(BG_CARD);
        titleRow.setAlignmentX(LEFT_ALIGNMENT);

        // Person icon drawn via Java2D
        JPanel tIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT_BLUE);
                int cx = getWidth() / 2, cy = getHeight() / 2;
                // Head
                g2.fillOval(cx - 6, cy - 10, 12, 12);
                // Body
                g2.fillRoundRect(cx - 9, cy + 3, 18, 10, 8, 8);
                g2.dispose();
            }
        };
        tIcon.setPreferredSize(new Dimension(22, 22));
        tIcon.setOpaque(false);

        JLabel tLbl = new JLabel("Contact Details");
        tLbl.setFont(FONT_SECTION);
        tLbl.setForeground(TEXT_TITLE);

        titleRow.add(tIcon);
        titleRow.add(tLbl);
        card.add(titleRow);
        card.add(gap(18));

        // Fields row
        JPanel fields = new JPanel(
                new GridLayout(1, 2, 18, 0));
        fields.setBackground(BG_CARD);
        fields.setAlignmentX(LEFT_ALIGNMENT);
        fields.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 78));

        fields.add(labeledField(
                "Full Name", "Enter contact name..."));
        fields.add(labeledField(
                "Phone Number", "Enter phone number..."));

        card.add(fields);
        card.add(gap(18));

        // Buttons row — fills full card width, fixed height
        JPanel btnsWrap = new JPanel(new BorderLayout());
        btnsWrap.setBackground(BG_CARD);
        btnsWrap.setAlignmentX(LEFT_ALIGNMENT);
        btnsWrap.setPreferredSize(new Dimension(Integer.MAX_VALUE, 48));
        btnsWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        btnsWrap.setMinimumSize(new Dimension(0, 48));

        JPanel btns = new JPanel(new GridLayout(1, 5, 12, 0));
        btns.setBackground(BG_CARD);

        addBtn    = mkBtn("Add",    ACCENT_BLUE,  true,  "add");
        searchBtn = mkBtn("Search", ACCENT_BLUE,  false, "search");
        updateBtn = mkBtn("Update", ACCENT_PURP,  false, "edit");
        deleteBtn = mkBtn("Delete", ACCENT_RED,   false, "delete");
        clearBtn  = mkBtn("Clear",  TEXT_MUTED,   false, "clear");

        btns.add(addBtn);
        btns.add(searchBtn);
        btns.add(updateBtn);
        btns.add(deleteBtn);
        btns.add(clearBtn);

        btnsWrap.add(btns, BorderLayout.CENTER);
        card.add(btnsWrap);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(BG_MAIN);
        wrap.setBorder(new EmptyBorder(0, 28, 0, 28));
        wrap.add(card, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel labeledField(String label,
                                String ph) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(BG_CARD);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(LEFT_ALIGNMENT);

        JTextField field = new JTextField();
        field.setFont(FONT_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(BG_INPUT);
        field.setCaretColor(ACCENT_BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COL, 1),
                new EmptyBorder(10, 14, 10, 14)
        ));
        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 46));
        field.setAlignmentX(LEFT_ALIGNMENT);

        // Placeholder
        field.setText(ph);
        field.setForeground(TEXT_DIM);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(ph)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                    field.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(
                                            ACCENT_BLUE, 2),
                                    new EmptyBorder(9, 13, 9, 13)
                            ));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(ph);
                    field.setForeground(TEXT_DIM);
                }
                field.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        BORDER_COL, 1),
                                new EmptyBorder(10, 14, 10, 14)
                        ));
            }
        });

        col.add(lbl);
        col.add(Box.createVerticalStrut(6));
        col.add(field);

        if (label.equals("Full Name"))    nameField  = field;
        if (label.equals("Phone Number")) phoneField = field;

        return col;
    }

    private JButton mkBtn(String text, Color fg,
                          boolean filled, String iconType) {
        final int IS     = 18;   // icon bounding box px
        final int GAP    = 8;    // icon → text gap
        final int RADIUS = 24;   // corner arc

        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth(), h = getHeight();

                // ── Soft shadow (filled only) ────────────────
                if (filled && !getModel().isPressed()) {
                    g2.setColor(new Color(fg.getRed(), fg.getGreen(),
                            fg.getBlue(), 50));
                    g2.fill(new RoundRectangle2D.Float(
                            2, 3, w - 4, h - 1, RADIUS, RADIUS));
                }

                // ── Background ───────────────────────────────
                if (filled) {
                    g2.setColor(getModel().isPressed() ? fg.darker()
                            : getModel().isRollover() ? fg.brighter() : fg);
                    g2.fill(new RoundRectangle2D.Float(
                            0, 0, w, h - 2, RADIUS, RADIUS));
                } else {
                    g2.setColor(getModel().isRollover()
                            ? new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), 15)
                            : BG_CARD);
                    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, RADIUS, RADIUS));
                    g2.setColor(getModel().isRollover() ? fg : BORDER_COL);
                    g2.setStroke(new BasicStroke(
                            getModel().isRollover() ? 1.8f : 1.4f));
                    g2.draw(new RoundRectangle2D.Float(
                            0.7f, 0.7f, w - 1.4f, h - 1.4f, RADIUS, RADIUS));
                }

                // ── Measure block to centre icon + text ──────
                FontMetrics fm = g2.getFontMetrics(getFont());
                int textW  = fm.stringWidth(text);
                int blockW = IS + GAP + textW;
                int startX = (w - blockW) / 2;
                int midY   = filled ? (h - 2) / 2 : h / 2;
                int ix     = startX;
                int iy     = midY - IS / 2;

                // ── Icon ─────────────────────────────────────
                Color ic = filled ? Color.WHITE : fg;
                g2.setColor(ic);
                BasicStroke thick = new BasicStroke(2.0f,
                        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                BasicStroke thin  = new BasicStroke(1.5f,
                        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                g2.setStroke(thick);

                switch (iconType) {
                    case "add": {
                        int m = IS / 2;
                        g2.drawLine(ix + m, iy + 3,       ix + m, iy + IS - 3);
                        g2.drawLine(ix + 3, iy + m,       ix + IS - 3, iy + m);
                        break;
                    }
                    case "search": {
                        int r = 6;
                        g2.drawOval(ix, iy + 1, r * 2, r * 2);
                        g2.drawLine(ix + r * 2 - 1, iy + r * 2,
                                    ix + IS - 1,    iy + IS);
                        break;
                    }
                    case "edit": {
                        int[] bx = { ix+IS-5, ix+IS-1, ix+5, ix+1 };
                        int[] by = { iy+1,    iy+5,    iy+IS-1, iy+IS-5 };
                        g2.drawPolygon(bx, by, 4);
                        g2.drawLine(ix+1, iy+IS-5, ix+3, iy+IS);
                        g2.drawLine(ix+3, iy+IS,   ix+5, iy+IS-1);
                        g2.setStroke(thin);
                        g2.drawLine(ix+IS-5, iy+1, ix+IS-1, iy+5);
                        break;
                    }
                    case "delete": {
                        g2.setStroke(thin);
                        g2.drawRoundRect(ix+6, iy, IS-12, 4, 2, 2);
                        g2.setStroke(thick);
                        g2.drawLine(ix+1, iy+5, ix+IS-1, iy+5);
                        g2.drawRoundRect(ix+2, iy+7, IS-4, IS-8, 3, 3);
                        g2.setStroke(thin);
                        g2.drawLine(ix+6,    iy+10, ix+6,    iy+IS-3);
                        g2.drawLine(ix+IS/2, iy+10, ix+IS/2, iy+IS-3);
                        g2.drawLine(ix+IS-6, iy+10, ix+IS-6, iy+IS-3);
                        break;
                    }
                    case "clear": {
                        g2.drawArc(ix+1, iy+1, IS-2, IS-2, 90, -270);
                        int ax = ix + IS/2 + 1, ay = iy + IS - 1;
                        g2.drawLine(ax, ay, ax+4, ay-4);
                        g2.drawLine(ax, ay, ax-4, ay-4);
                        break;
                    }
                }

                // ── Label ────────────────────────────────────
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(getFont());
                g2.setColor(filled ? Color.WHITE : fg);
                int tx = startX + IS + GAP;
                int ty = midY + (fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(text, tx, ty);

                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(filled ? Color.WHITE : fg);
        btn.setBackground(BG_CARD);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBorder(new EmptyBorder(0, 0, 0, 0));
        btn.setPreferredSize(new Dimension(120, 48));
        btn.setMinimumSize(new Dimension(80, 48));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

     // ─────────────────────────────────────────────────
    //  TABLE CARD Developed by Siham Desta
    // ─────────────────────────────────────────────────
    private JPanel buildTable() {
        JPanel card = new CardPanel(16, BG_CARD);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 22, 20, 22));

        // Header row
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(BG_CARD);
        hdr.setBorder(new EmptyBorder(0, 0, 14, 0));

        JPanel hl = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        hl.setBackground(BG_CARD);
        // People icon drawn via Java2D
        JPanel hIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT_BLUE);
                int cx = getWidth() / 2, cy = getHeight() / 2;
                // Back person
                g2.setColor(new Color(ACCENT_BLUE.getRed(), ACCENT_BLUE.getGreen(), ACCENT_BLUE.getBlue(), 140));
                g2.fillOval(cx + 1, cy - 9, 9, 9);
                g2.fillRoundRect(cx - 1, cy + 1, 12, 9, 5, 5);
                // Front person
                g2.setColor(ACCENT_BLUE);
                g2.fillOval(cx - 9, cy - 9, 9, 9);
                g2.fillRoundRect(cx - 11, cy + 1, 12, 9, 5, 5);
                g2.dispose();
            }
        };
        hIcon.setPreferredSize(new Dimension(22, 22));
        hIcon.setOpaque(false);
        JLabel hTitl = new JLabel("All Contacts");
        hTitl.setFont(FONT_SECTION);
        hTitl.setForeground(TEXT_TITLE);
        hl.add(hIcon);
        hl.add(hTitl);

        cntBadge = new JLabel("0 contacts");
        cntBadge.setFont(FONT_BADGE);
        cntBadge.setForeground(ACCENT_BLUE);
        cntBadge.setOpaque(true);
        cntBadge.setBackground(new Color(239, 246, 255));
        cntBadge.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(191, 219, 254), 1),
                        new EmptyBorder(5, 14, 5, 14)
                ));

        hdr.add(hl,       BorderLayout.WEST);
        hdr.add(cntBadge, BorderLayout.EAST);

        // Table model
        String[] cols = {"#", "Name",
                "Phone Number", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        contactTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(
                    TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0
                            ? BG_TABLE_ROW : BG_TABLE_ALT);
                } else {
                    c.setBackground(BG_TABLE_SEL);
                }
                return c;
            }
        };

        contactTable.setFont(FONT_TABLE);
        contactTable.setForeground(TEXT_PRIMARY);
        contactTable.setBackground(BG_TABLE_ROW);
        contactTable.setSelectionBackground(BG_TABLE_SEL);
        contactTable.setSelectionForeground(TEXT_PRIMARY);
        contactTable.setRowHeight(56);
        contactTable.setShowGrid(false);
        contactTable.setIntercellSpacing(new Dimension(0, 2));
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactTable.setFillsViewportHeight(true);

        // Column widths
        contactTable.getColumnModel().getColumn(0).setPreferredWidth(55);
        contactTable.getColumnModel().getColumn(0).setMaxWidth(65);
        contactTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        contactTable.getColumnModel().getColumn(2).setPreferredWidth(230);
        contactTable.getColumnModel().getColumn(3).setPreferredWidth(120);

        // Header
        JTableHeader th = contactTable.getTableHeader();
        th.setBackground(new Color(248, 250, 252));
        th.setForeground(TEXT_MUTED);
        th.setFont(FONT_TH);
        th.setReorderingAllowed(false);
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COL));
        th.setPreferredSize(new Dimension(0, 40));

        // Custom renderers
        contactTable.getColumnModel().getColumn(0).setCellRenderer(new IdxRenderer());
        contactTable.getColumnModel().getColumn(1).setCellRenderer(new AvatarRenderer());
        contactTable.getColumnModel().getColumn(2).setCellRenderer(new PhoneRenderer());
        contactTable.getColumnModel().getColumn(3).setCellRenderer(new BadgeRenderer());

        JScrollPane scroll = new JScrollPane(contactTable);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        scroll.getViewport().setBackground(BG_TABLE_ROW);

        card.add(hdr,    BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(BG_MAIN);
        wrap.setBorder(new EmptyBorder(0, 28, 0, 28));
        wrap.add(card, BorderLayout.CENTER);
        return wrap;
    }

    // ─────────────────────────────────────────────────
    //  STATUS BAR
    // ─────────────────────────────────────────────────
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_CARD);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        1, 0, 0, 0, BORDER_COL),
                new EmptyBorder(10, 28, 10, 28)
        ));

        statusLbl = new JLabel(
                "\u25CF  Ready \u2014 all systems operational");
        statusLbl.setFont(FONT_STATUS);
        statusLbl.setForeground(ACCENT_GREEN);

        // Database icon panel + label
        JPanel dbPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        dbPanel.setBackground(BG_CARD);
        JPanel dbIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TEXT_MUTED);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int cx = getWidth()/2, cy = getHeight()/2;
                int dw = 14, dh = 4, dx = cx - dw/2;
                g2.drawOval(dx, cy - 7, dw, dh);
                g2.drawLine(dx, cy - 5, dx, cy + 5);
                g2.drawLine(dx + dw, cy - 5, dx + dw, cy + 5);
                g2.drawOval(dx, cy - 2, dw, dh);
                g2.drawOval(dx, cy + 3, dw, dh);
                g2.dispose();
            }
        };
        dbIcon.setPreferredSize(new Dimension(18, 18));
        dbIcon.setOpaque(false);
        JLabel dbLbl = new JLabel("telephone_directory");
        dbLbl.setFont(FONT_STATUS);
        dbLbl.setForeground(TEXT_MUTED);
        dbPanel.add(dbIcon);
        dbPanel.add(dbLbl);

        bar.add(statusLbl, BorderLayout.WEST);
        bar.add(dbPanel,   BorderLayout.EAST);
        return bar;
    }

    // ─────────────────────────────────────────────────
    //  LISTENERS
    // ─────────────────────────────────────────────────
    private void attachListeners() {
        addBtn.addActionListener(e    -> handleAdd());
        deleteBtn.addActionListener(e -> handleDelete());
        updateBtn.addActionListener(e -> handleUpdate());
        searchBtn.addActionListener(e -> handleSearch());
        clearBtn.addActionListener(e  -> {
            clearFields();
            setStatus("\u25CF  Fields cleared",
                    TEXT_MUTED);
        });

        contactTable.getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        int row = contactTable
                                .getSelectedRow();
                        if (row >= 0) {
                            putField(nameField,
                                    "Enter contact name...",
                                    (String) tableModel
                                            .getValueAt(row, 1));
                            putField(phoneField,
                                    "Enter phone number...",
                                    (String) tableModel
                                            .getValueAt(row, 2));
                        }
                    }
                });
    }

    // ─────────────────────────────────────────────────
    //  HANDLERS
    // ─────────────────────────────────────────────────
    private void handleAdd() {
        String name  = getVal(nameField,
                "Enter contact name...");
        String phone = getVal(phoneField,
                "Enter phone number...");

        if (name.isEmpty() || phone.isEmpty()) {
            err("Please fill in all required fields.");
            return;
        }
        if (manager.addContact(name, phone)) {
            ok("Contact added successfully.");
            lastVal.setText(name);
            refreshTable();
            clearFields();
            setStatus("\u25CF  Added: " + name,
                    ACCENT_GREEN);
        } else {
            err("This name is already used. " +
                    "Please enter a unique name.");
        }
    }

    private void handleDelete() {
        String name = getVal(nameField,
                "Enter contact name...");
        if (name.isEmpty()) {
            int row = contactTable.getSelectedRow();
            if (row >= 0) {
                name = (String) tableModel
                        .getValueAt(row, 1);
            } else {
                err("Please select or enter a name.");
                return;
            }
        }
        int c = JOptionPane.showConfirmDialog(this,
                "Delete \"" + name + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (c == JOptionPane.YES_OPTION) {
            if (manager.deleteContact(name)) {
                ok("Contact deleted successfully.");
                refreshTable();
                clearFields();
                setStatus("\u25CF  Deleted: " + name,
                        ACCENT_RED);
            } else {
                err("Contact not found.");
            }
        }
    }

    private void handleUpdate() {
        String name  = getVal(nameField,
                "Enter contact name...");
        String phone = getVal(phoneField,
                "Enter phone number...");
        if (name.isEmpty() || phone.isEmpty()) {
            err("Please fill in all required fields.");
            return;
        }
        if (manager.updateContact(name, phone)) {
            ok("Contact updated successfully.");
            refreshTable();
            clearFields();
            setStatus("\u25CF  Updated: " + name,
                    ACCENT_PURP);
        } else {
            err("Contact not found.");
        }
    }

    private void handleSearch() {
        String name = getVal(nameField,
                "Enter contact name...");
        if (name.isEmpty()) {
            err("Please enter a name to search.");
            return;
        }
        Contact found = manager.searchContact(name);
        if (found != null) {
            for (int i = 0;
                 i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 1)
                        .toString()
                        .equalsIgnoreCase(name)) {
                    contactTable
                            .setRowSelectionInterval(i, i);
                    contactTable.scrollRectToVisible(
                            contactTable
                                    .getCellRect(i, 0, true));
                    break;
                }
            }
            putField(phoneField,
                    "Enter phone number...",
                    found.getPhoneNumber());
            ok("Contact Found!\n\n"
                    + "Name:   " + found.getName()
                    + "\nPhone:  "
                    + found.getPhoneNumber());
            setStatus("\u25CF  Found: "
                    + found.getName(), ACCENT_BLUE);
        } else {
            err("Contact not found.");
        }
    }

    // ─────────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────────
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Contact> all = manager.getAllContacts();
        int i = 1;
        for (Contact c : all) {
            tableModel.addRow(new Object[]{
                    String.format("%02d", i++),
                    c.getName(),
                    c.getPhoneNumber(),
                    "Active"
            });
        }
        int n = all.size();
        totalVal.setText(String.valueOf(n));
        cntBadge.setText(n + " contacts");
        if (!all.isEmpty()) {
            lastVal.setText(
                    all.get(all.size()-1).getName());
        } else {
            lastVal.setText("—");
        }
        setStatus(
                "\u25CF  Ready — all systems operational",
                ACCENT_GREEN);
    }

    private void clearFields() {
        putField(nameField,
                "Enter contact name...", "");
        putField(phoneField,
                "Enter phone number...", "");
        nameField.requestFocus();
        contactTable.clearSelection();
    }

    private void putField(JTextField f,
                          String ph, String val) {
        if (val == null || val.isEmpty()) {
            f.setText(ph);
            f.setForeground(TEXT_DIM);
        } else {
            f.setText(val);
            f.setForeground(TEXT_PRIMARY);
        }
    }

    private String getVal(JTextField f, String ph) {
        String t = f.getText().trim();
        return t.equals(ph) ? "" : t;
    }

    private void setStatus(String msg, Color col) {
        statusLbl.setText(msg);
        statusLbl.setForeground(col);
    }

    private void err(String msg) {
        JOptionPane.showMessageDialog(this, msg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void ok(String msg) {
        JOptionPane.showMessageDialog(this, msg,
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private Component gap(int h) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(0, h));
        p.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, h));
        p.setMinimumSize(new Dimension(0, h));
        return p;
    }

     // ─────────────────────────────────────────────────
    //  INNER — CardPanel (shadow + rounded)
    // ─────────────────────────────────────────────────
    static class CardPanel extends JPanel {
        private final int r;
        private final Color bg;
        CardPanel(int r, Color bg) {
            this.r = r; this.bg = bg;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            // Soft shadow
            for (int i = 5; i >= 1; i--) {
                g2.setColor(new Color(0, 0, 0,
                        5 * (6 - i)));
                g2.fill(new RoundRectangle2D.Float(
                        i, i,
                        getWidth()  - i * 2,
                        getHeight() - i * 2,
                        r * 2, r * 2));
            }
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Float(
                    0, 0, getWidth(), getHeight(),
                    r * 2, r * 2));
            g2.setColor(new Color(218, 225, 234));
            g2.setStroke(new BasicStroke(0.8f));
            g2.draw(new RoundRectangle2D.Float(
                    0.5f, 0.5f,
                    getWidth()  - 1,
                    getHeight() - 1,
                    r * 2, r * 2));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ─────────────────────────────────────────────────
    //  INNER — Table Renderers
    // ─────────────────────────────────────────────────

    // Row index
    static class IdxRenderer
            extends DefaultTableCellRenderer {
        IdxRenderer() {
            setHorizontalAlignment(CENTER);
        }
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel,
                boolean foc, int row, int col) {
            super.getTableCellRendererComponent(
                    t, v, sel, foc, row, col);
            setForeground(TEXT_DIM);
            setFont(new Font("Segoe UI",
                    Font.PLAIN, 12));
            setBorder(new EmptyBorder(0, 8, 0, 8));
            return this;
        }
    }

    // Phone
    static class PhoneRenderer
            extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel,
                boolean foc, int row, int col) {
            super.getTableCellRendererComponent(
                    t, v, sel, foc, row, col);
            setForeground(TEXT_MUTED);
            setFont(new Font("Segoe UI",
                    Font.PLAIN, 13));
            setBorder(new EmptyBorder(0, 12, 0, 12));
            return this;
        }
    }

    // Status badge
    static class BadgeRenderer
            extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel,
                boolean foc, int row, int col) {
            JPanel p = new JPanel(
                    new FlowLayout(FlowLayout.CENTER, 0, 0));
            p.setBackground(sel ? BG_TABLE_SEL :
                    (row % 2 == 0
                            ? BG_TABLE_ROW : BG_TABLE_ALT));
            JLabel b = new JLabel("\u25CF  Active") {
                @Override
                protected void paintComponent(
                        Graphics g) {
                    Graphics2D g2 =
                            (Graphics2D) g.create();
                    g2.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints
                                    .VALUE_ANTIALIAS_ON);
                    g2.setColor(
                            new Color(220, 252, 231));
                    g2.fill(new RoundRectangle2D.Float(
                            0, 0, getWidth(), getHeight(),
                            getHeight(), getHeight()));
                    g2.setColor(
                            new Color(134, 239, 172));
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(new RoundRectangle2D.Float(
                            0.5f, 0.5f,
                            getWidth()-1, getHeight()-1,
                            getHeight(), getHeight()));
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            b.setFont(new Font("Segoe UI",
                    Font.BOLD, 11));
            b.setForeground(new Color(21, 128, 61));
            b.setOpaque(false);
            b.setBorder(new EmptyBorder(4, 12, 4, 12));
            p.add(b);
            return p;
        }
    }

    // Avatar + name
    static class AvatarRenderer
            extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel,
                boolean foc, int row, int col) {
            String name = v == null ? "" : v.toString();
            Color rowBg = sel ? BG_TABLE_SEL :
                    (row % 2 == 0
                            ? BG_TABLE_ROW : BG_TABLE_ALT);

            JPanel p = new JPanel(
                    new FlowLayout(FlowLayout.LEFT, 14, 0));
            p.setBackground(rowBg);

            Color ac = AV_COLORS[
                    Math.abs(name.hashCode())
                            % AV_COLORS.length];
            String ini = initials(name);

            JLabel av = new JLabel(ini) {
                @Override
                protected void paintComponent(
                        Graphics g) {
                    Graphics2D g2 =
                            (Graphics2D) g.create();
                    g2.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING,
                            RenderingHints
                                    .VALUE_ANTIALIAS_ON);
                    g2.setColor(ac);
                    g2.fillOval(0, 0,
                            getWidth(), getHeight());
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            av.setHorizontalAlignment(CENTER);
            av.setFont(new Font("Segoe UI",
                    Font.BOLD, 13));
            av.setForeground(Color.WHITE);
            av.setOpaque(false);
            av.setPreferredSize(new Dimension(38, 38));

            JLabel nl = new JLabel(name);
            nl.setFont(new Font("Segoe UI",
                    Font.BOLD, 13));
            nl.setForeground(TEXT_PRIMARY);

            p.add(av);
            p.add(nl);
            return p;
        }

        private String initials(String n) {
            if (n == null || n.isEmpty()) return "?";
            String[] p = n.trim().split("\\s+");
            if (p.length == 1) {
                return p[0].length() >= 2
                        ? p[0].substring(0, 2).toUpperCase()
                        : p[0].toUpperCase();
            }
            return ("" + p[0].charAt(0)
                    + p[1].charAt(0)).toUpperCase();
        }
    }
}