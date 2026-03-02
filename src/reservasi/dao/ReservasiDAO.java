package reservasi.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import reservasi.Koneksi;
import reservasi.model.ReservasiModel;

public class ReservasiDAO {

    public List<String[]> getLapanganList() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT id_lapangan, nama_lapangan, harga_per_jam FROM lapangan";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[]{
                    String.valueOf(rs.getInt("id_lapangan")),
                    rs.getString("nama_lapangan"),
                    String.valueOf(rs.getDouble("harga_per_jam"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String[]> getTimeSlots() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT id_slot, jam_mulai, jam_selesai FROM time_slot ORDER BY jam_mulai";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[]{
                    String.valueOf(rs.getInt("id_slot")),
                    rs.getString("jam_mulai"),
                    rs.getString("jam_selesai")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getBookedSlots(Date tanggal, int idLapangan) {
        List<Integer> booked = new ArrayList<>();
        String sql = "SELECT rd.id_slot FROM reservasi_detail rd " +
                     "JOIN reservasi r ON rd.id_reservasi = r.id_reservasi " +
                     "WHERE r.tanggal = ? AND r.id_lapangan = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, tanggal);
            ps.setInt(2, idLapangan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    booked.add(rs.getInt("id_slot"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booked;
    }

    public boolean checkAvailability(Date tanggal, int idLapangan, List<Integer> selectedSlots) {
        List<Integer> booked = getBookedSlots(tanggal, idLapangan);
        for (int slot : selectedSlots) {
            if (booked.contains(slot)) {
                return false;
            }
        }
        return true;
    }

    public int getOrCreateCustomer(Connection conn, String namaCustomer, String noHp) throws SQLException {
        String selectSql = "SELECT id_customer FROM customer WHERE no_hp = ?";
        try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setString(1, noHp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_customer");
                }
            }
        }

        String insertSql = "INSERT INTO customer (nama_customer, no_hp) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, namaCustomer);
            ps.setString(2, noHp);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to get or create customer.");
    }

    public String save(ReservasiModel reservasi, List<Integer> selectedSlots,
                        String namaCustomer, String noHp, double dpAmount) {
        Connection conn = null;
        try {
            conn = Koneksi.getKoneksi();
            conn.setAutoCommit(false);

            int idCustomer = getOrCreateCustomer(conn, namaCustomer, noHp);
            reservasi.setIdCustomer(idCustomer);

            String kodeReservasi = "RES-" + System.currentTimeMillis();
            int idUser = 1; // default fallback user for now

            String insertReservasi = "INSERT INTO reservasi (kode_reservasi, id_customer, id_lapangan, id_user, tanggal, total_jam, total_harga, id_status_reservasi) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            int idReservasi;
            try (PreparedStatement ps = conn.prepareStatement(insertReservasi, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, kodeReservasi);
                ps.setInt(2, reservasi.getIdCustomer());
                ps.setInt(3, reservasi.getIdLapangan());
                ps.setInt(4, idUser);
                ps.setDate(5, reservasi.getTanggal());
                ps.setInt(6, reservasi.getTotalJam());
                ps.setDouble(7, reservasi.getTotalHarga());
                ps.setInt(8, reservasi.getIdStatusReservasi());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idReservasi = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to get generated reservasi ID.");
                    }
                }
            }

            double hargaPerSlot = reservasi.getTotalHarga() / reservasi.getTotalJam();
            String insertDetail = "INSERT INTO reservasi_detail (id_reservasi, id_slot, harga) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertDetail)) {
                for (int slotId : selectedSlots) {
                    ps.setInt(1, idReservasi);
                    ps.setInt(2, slotId);
                    ps.setDouble(3, hargaPerSlot);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            if (dpAmount > 0) {
                String insertPembayaran = "INSERT INTO pembayaran (id_reservasi, jumlah_bayar, id_metode) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertPembayaran)) {
                    ps.setInt(1, idReservasi);
                    ps.setDouble(2, dpAmount);
                    ps.setInt(3, 1);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return "SUCCESS";

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<String[]> getAllReservasi() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT r.id_reservasi, r.tanggal, c.nama_customer, l.nama_lapangan, r.total_jam, r.total_harga " +
                     "FROM reservasi r " +
                     "JOIN customer c ON r.id_customer = c.id_customer " +
                     "JOIN lapangan l ON r.id_lapangan = l.id_lapangan " +
                     "ORDER BY r.tanggal DESC, r.id_reservasi DESC";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new String[]{
                    String.valueOf(rs.getInt("id_reservasi")),
                    String.valueOf(rs.getDate("tanggal")),
                    rs.getString("nama_customer"),
                    rs.getString("nama_lapangan"),
                    String.valueOf(rs.getInt("total_jam")),
                    String.format("Rp %,.0f", rs.getDouble("total_harga"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String[]> getReservasiHariIni() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT c.nama_customer, l.nama_lapangan, MIN(ts.jam_mulai) as waktu_mulai, MAX(ts.jam_selesai) as waktu_selesai " +
                     "FROM reservasi r " +
                     "JOIN customer c ON r.id_customer = c.id_customer " +
                     "JOIN lapangan l ON r.id_lapangan = l.id_lapangan " +
                     "JOIN reservasi_detail rd ON r.id_reservasi = rd.id_reservasi " +
                     "JOIN time_slot ts ON rd.id_slot = ts.id_slot " +
                     "WHERE r.tanggal = CURDATE() " +
                     "GROUP BY r.id_reservasi, c.nama_customer, l.nama_lapangan " +
                     "ORDER BY waktu_mulai ASC";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String durasi = rs.getString("waktu_mulai") + " - " + rs.getString("waktu_selesai");
                list.add(new String[]{
                    rs.getString("nama_customer"),
                    rs.getString("nama_lapangan"),
                    durasi
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
