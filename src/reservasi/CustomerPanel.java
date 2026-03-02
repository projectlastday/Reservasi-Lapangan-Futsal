package reservasi;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import reservasi.dao.CustomerDAO;
import reservasi.model.CustomerModel;

public class CustomerPanel extends JPanel {

    private static final Color CONTENT_BG = new Color(255, 255, 255);
    private static final Color ACCENT = new Color(59, 130, 246);
    private static final Color ACCENT_HOVER = new Color(37, 99, 235);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(203, 213, 225);

    private JTextField txtNamaCustomer;
    private JTextField txtNoHp;
    private CustomerDAO dao;

    public CustomerPanel() {
        dao = new CustomerDAO();
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        setBackground(CONTENT_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Tambah Customer");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        add(createLabel("Nama Customer"), gbc);
        txtNamaCustomer = createTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(txtNamaCustomer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        add(createLabel("No. HP"), gbc);
        txtNoHp = createTextField();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(txtNoHp, gbc);

        JButton btnSave = new JButton("Simpan");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setForeground(CONTENT_BG);
        btnSave.setBackground(ACCENT);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setOpaque(true);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setPreferredSize(new Dimension(200, 40));
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(ACCENT_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSave.setBackground(ACCENT);
            }
        });
        btnSave.addActionListener(e -> handleSave());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        btnPanel.setBackground(CONTENT_BG);
        btnPanel.add(btnSave);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnPanel, gbc);

        // Spacer to push everything to the top
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        add(new JPanel() {{ setBackground(CONTENT_BG); }}, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(TEXT_SECONDARY);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        return lbl;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField(20);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return tf;
    }

    private void handleSave() {
        String namaCustomer = txtNamaCustomer.getText().trim();
        String noHp = txtNoHp.getText().trim();

        if (namaCustomer.isEmpty() || noHp.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua kolom wajib diisi.", "Peringatan", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        CustomerModel model = new CustomerModel(namaCustomer, noHp);
        boolean success = dao.insertCustomer(model);

        if (success) {
            JOptionPane.showMessageDialog(null, "Customer berhasil disimpan!", "Sukses", JOptionPane.PLAIN_MESSAGE);
            txtNamaCustomer.setText("");
            txtNoHp.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan customer.", "Error", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
