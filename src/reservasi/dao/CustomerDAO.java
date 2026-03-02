package reservasi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import reservasi.Koneksi;
import reservasi.model.CustomerModel;

public class CustomerDAO {

    public boolean insertCustomer(CustomerModel customer) {
        String sql = "INSERT INTO customer (nama_customer, no_hp) VALUES (?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getNamaCustomer());
            ps.setString(2, customer.getNoHp());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CustomerModel> getCustomerList() {
        List<CustomerModel> list = new ArrayList<>();
        String sql = "SELECT id_customer, nama_customer, no_hp FROM customer ORDER BY nama_customer";
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CustomerModel customer = new CustomerModel(
                    rs.getString("nama_customer"),
                    rs.getString("no_hp")
                );
                customer.setIdCustomer(rs.getInt("id_customer"));
                list.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
