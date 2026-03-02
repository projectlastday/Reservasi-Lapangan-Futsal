package reservasi;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import reservasi.dao.ReservasiDAO;
import reservasi.dao.CustomerDAO;
import reservasi.model.CustomerModel;
import reservasi.model.ReservasiModel;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TambahReservasiPanel extends JPanel {

    private static final Color HEADER_BG = new Color(226, 232, 240);
    private static final Color SIDEBAR_BG = new Color(203, 213, 225);
    private static final Color PANEL_BG = new Color(241, 245, 249);
    private static final Color CONTENT_BG = new Color(255, 255, 255);
    private static final Color ACCENT = new Color(59, 130, 246);
    private static final Color ACCENT_HOVER = new Color(37, 99, 235);
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(203, 213, 225);

    private JDateChooser txtTanggal;
    private JComboBox<CustomerModel> cmbCustomer;
    private JTextField txtNoHp;
    private JComboBox<String> cmbLapangan;
    private JLabel lblTotalJam;
    private JLabel lblTotalHarga;
    private JPanel slotsPanel;
    private List<JCheckBox> slotCheckboxes;
    private List<String[]> lapanganData;
    private List<String[]> slotData;
    private ReservasiDAO dao;
    private CustomerDAO customerDao;

    public TambahReservasiPanel() {
        dao = new ReservasiDAO();
        customerDao = new CustomerDAO();
        slotCheckboxes = new ArrayList<>();
        lapanganData = dao.getLapanganList();
        slotData = dao.getTimeSlots();
        initUI();
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshCustomerList();
            }
        });
    }

    private void refreshCustomerList() {
        cmbCustomer.removeAllItems();
        List<CustomerModel> customers = customerDao.getCustomerList();
        for (CustomerModel customer : customers) {
            cmbCustomer.addItem(customer);
        }
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        setBackground(CONTENT_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Tambah Reservasi");
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
        add(createLabel("Nama Customer"), gbc);
        cmbCustomer = new JComboBox<>();
        cmbCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCustomer.setBackground(CONTENT_BG);
        cmbCustomer.addActionListener(e -> {
            CustomerModel selected = (CustomerModel) cmbCustomer.getSelectedItem();
            if (selected != null) {
                txtNoHp.setText(selected.getNoHp());
            } else {
                txtNoHp.setText("");
            }
        });
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(cmbCustomer, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        add(createLabel("No. HP"), gbc);
        txtNoHp = createTextField();
        txtNoHp.setEditable(false);
        txtNoHp.setBackground(PANEL_BG);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(txtNoHp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        add(createLabel("Tanggal"), gbc);
        txtTanggal = new JDateChooser();
        txtTanggal.setDateFormatString("yyyy-MM-dd");
        txtTanggal.setDate(new java.util.Date());
        txtTanggal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(txtTanggal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        add(createLabel("Lapangan"), gbc);
        cmbLapangan = new JComboBox<>();
        cmbLapangan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbLapangan.setBackground(CONTENT_BG);
        for (String[] lap : lapanganData) {
            cmbLapangan.addItem(lap[1] + "  (Rp " + lap[2] + "/jam)");
        }
        cmbLapangan.addActionListener(e -> updateTotal());
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        add(cmbLapangan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        add(createLabel("Pilih Slot Waktu"), gbc);

        slotsPanel = new JPanel();
        slotsPanel.setLayout(new BoxLayout(slotsPanel, BoxLayout.Y_AXIS));
        slotsPanel.setBackground(CONTENT_BG);
        slotsPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        for (String[] slot : slotData) {
            JCheckBox cb = new JCheckBox(slot[1] + " - " + slot[2]);
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            cb.setBackground(CONTENT_BG);
            cb.setForeground(TEXT_PRIMARY);
            cb.putClientProperty("slotId", Integer.parseInt(slot[0]));
            cb.addActionListener(e -> updateTotal());
            slotCheckboxes.add(cb);
            slotsPanel.add(cb);
        }
        JScrollPane scrollSlots = new JScrollPane(slotsPanel);
        scrollSlots.setPreferredSize(new Dimension(300, 150));
        scrollSlots.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollSlots.getViewport().setBackground(CONTENT_BG);
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(scrollSlots, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        summaryPanel.setBackground(PANEL_BG);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        lblTotalJam = new JLabel("Total Jam: 0");
        lblTotalJam.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalJam.setForeground(TEXT_PRIMARY);
        summaryPanel.add(lblTotalJam);

        lblTotalHarga = new JLabel("Total Harga: Rp 0");
        lblTotalHarga.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalHarga.setForeground(ACCENT);
        summaryPanel.add(lblTotalHarga);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        add(summaryPanel, gbc);

        JButton btnSave = new JButton("Simpan Reservasi");
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

        JButton btnCheck = new JButton("Cek Ketersediaan");
        btnCheck.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCheck.setForeground(TEXT_PRIMARY);
        btnCheck.setBackground(HEADER_BG);
        btnCheck.setFocusPainted(false);
        btnCheck.setBorderPainted(false);
        btnCheck.setOpaque(true);
        btnCheck.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCheck.setPreferredSize(new Dimension(200, 40));
        btnCheck.addActionListener(e -> handleCheckAvailability());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnPanel.setBackground(CONTENT_BG);
        btnPanel.add(btnCheck);
        btnPanel.add(btnSave);

        gbc.gridy = 9;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);
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

    private List<Integer> getSelectedSlotIds() {
        List<Integer> selected = new ArrayList<>();
        for (JCheckBox cb : slotCheckboxes) {
            if (cb.isSelected()) {
                selected.add((int) cb.getClientProperty("slotId"));
            }
        }
        return selected;
    }

    private double getHargaPerJam() {
        int idx = cmbLapangan.getSelectedIndex();
        if (idx >= 0 && idx < lapanganData.size()) {
            return Double.parseDouble(lapanganData.get(idx)[2]);
        }
        return 0;
    }

    private int getSelectedLapanganId() {
        int idx = cmbLapangan.getSelectedIndex();
        if (idx >= 0 && idx < lapanganData.size()) {
            return Integer.parseInt(lapanganData.get(idx)[0]);
        }
        return -1;
    }

    private void updateTotal() {
        int totalJam = getSelectedSlotIds().size();
        double totalHarga = totalJam * getHargaPerJam();
        lblTotalJam.setText("Total Jam: " + totalJam);
        lblTotalHarga.setText("Total Harga: Rp " + String.format("%,.0f", totalHarga));
    }

    private Date parseTanggal() throws ParseException {
        java.util.Date parsed = txtTanggal.getDate();
        if (parsed == null) {
            throw new ParseException("Tanggal tidak valid", 0);
        }
        return new Date(parsed.getTime());
    }

    private void handleCheckAvailability() {
        try {
            Date tanggal = parseTanggal();
            int idLapangan = getSelectedLapanganId();
            List<Integer> selectedSlots = getSelectedSlotIds();

            if (selectedSlots.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih minimal 1 slot waktu.", "Peringatan", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            List<Integer> booked = dao.getBookedSlots(tanggal, idLapangan);
            List<String> conflicts = new ArrayList<>();
            for (JCheckBox cb : slotCheckboxes) {
                int slotId = (int) cb.getClientProperty("slotId");
                if (cb.isSelected() && booked.contains(slotId)) {
                    conflicts.add(cb.getText());
                }
            }

            if (conflicts.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua slot tersedia!", "Tersedia", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Slot berikut sudah dipesan:\n" + String.join("\n", conflicts),
                        "Tidak Tersedia", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Format tanggal salah. Gunakan yyyy-MM-dd.", "Error", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void handleSave() {
        CustomerModel selCust = (CustomerModel) cmbCustomer.getSelectedItem();
        if (selCust == null) {
            JOptionPane.showMessageDialog(null, "Pilih Customer terlebih dahulu.", "Peringatan", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        
        String namaCustomer = selCust.getNamaCustomer();
        String noHp = selCust.getNoHp();

        List<Integer> selectedSlots = getSelectedSlotIds();
        if (selectedSlots.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pilih minimal 1 slot waktu.", "Peringatan", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        try {
            Date tanggal = parseTanggal();
            int idLapangan = getSelectedLapanganId();

            if (!dao.checkAvailability(tanggal, idLapangan, selectedSlots)) {
                JOptionPane.showMessageDialog(null, "Beberapa slot sudah dipesan. Cek ketersediaan terlebih dahulu.", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            int totalJam = selectedSlots.size();
            double totalHarga = totalJam * getHargaPerJam();
            double dpAmount = 0;

            ReservasiModel model = new ReservasiModel();
            model.setTanggal(tanggal);
            model.setIdLapangan(idLapangan);
            model.setTotalJam(totalJam);
            model.setTotalHarga(totalHarga);
            model.setIdStatusReservasi(1);

            String result = dao.save(model, selectedSlots, namaCustomer, noHp, dpAmount);

            if ("SUCCESS".equals(result)) {
                JOptionPane.showMessageDialog(null, "Reservasi berhasil disimpan!", "Sukses", JOptionPane.PLAIN_MESSAGE);
                resetForm();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan reservasi:\n" + result, "Error", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Format tanggal salah. Gunakan yyyy-MM-dd.", "Error", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void resetForm() {
        if (cmbCustomer.getItemCount() > 0) {
            cmbCustomer.setSelectedIndex(0);
        }
        txtNoHp.setText("");
        txtTanggal.setDate(new java.util.Date());
        for (JCheckBox cb : slotCheckboxes) {
            cb.setSelected(false);
        }
        if (cmbLapangan.getItemCount() > 0) {
            cmbLapangan.setSelectedIndex(0);
        }
        updateTotal();
    }
}
