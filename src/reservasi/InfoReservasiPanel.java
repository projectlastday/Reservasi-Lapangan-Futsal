package reservasi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import reservasi.dao.ReservasiDAO;

public class InfoReservasiPanel extends JPanel {

    private static final Color CONTENT_BG = new Color(255, 255, 255);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color HEADER_BG = new Color(241, 245, 249);
    private static final Color BORDER_COLOR = new Color(203, 213, 225);

    private JTable table;
    private DefaultTableModel tableModel;
    private ReservasiDAO dao;

    public InfoReservasiPanel() {
        dao = new ReservasiDAO();
        initUI();

        // Refresh data every time the panel is shown
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadData();
            }
        });
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(CONTENT_BG);

        // Header Section
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(CONTENT_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(16, 24, 16, 24);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;

        JLabel titleLabel = new JLabel("Info Reservasi Hari Ini");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_PRIMARY);
        headerPanel.add(titleLabel, gbc);

        add(headerPanel, BorderLayout.NORTH);

        // Table Section
        String[] columnNames = {"Nama Customer", "Lapangan", "Rentang Waktu"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(BORDER_COLOR);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(226, 232, 240));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(HEADER_BG);
        tableHeader.setForeground(TEXT_PRIMARY);
        tableHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 24, 24, 24));
        scrollPane.getViewport().setBackground(CONTENT_BG);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0); // clear existing data
        List<String[]> data = dao.getReservasiHariIni();
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }
}
