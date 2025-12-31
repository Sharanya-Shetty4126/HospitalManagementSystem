package DAO;

import connectivity.DBConnection;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    // ================= ADD ADDRESS =================
    // New version for transaction-safe insert
public int addAddress(Address address, Connection con) throws Exception {
    String sql = "INSERT INTO address (STREET_NAME, HOUSE_NAME, CITY, STATE, COUNTRY, PIN_CODE) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, address.getStreet());
        ps.setString(2, address.getHouseName());
        ps.setString(3, address.getCity());
        ps.setString(4, address.getState());
        ps.setString(5, address.getCountry());
        ps.setString(6, address.getPinCode());

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    address.setAddressID(generatedId);
                    return generatedId;
                }
            }
        }
    }
    return -1;
}


    // ================= GET ALL ADDRESSES =================
    public List<Address> getAllAddresses() {

        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM address";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Address address = new Address(
                    rs.getString("country"),
                    rs.getString("state"),
                    rs.getString("city"),
                    rs.getString("street_name"),
                    rs.getString("house_name"),
                    rs.getString("pin_code")
                );

                address.setAddressID(rs.getInt("address_id"));
                addresses.add(address);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return addresses;
    }

    // ================= UPDATE ADDRESS =================
    public boolean updateAddress(Address address) {

        String sql = "UPDATE address SET " +
                     "street_name=?, house_name=?, city=?, state=?, country=?, pin_code=? " +
                     "WHERE address_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getHouseName());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getCountry());
            ps.setString(6, address.getPinCode());
            ps.setInt(7, address.getAddressID());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE ADDRESS =================
    public boolean deleteAddress(int addressId) {

        String sql = "DELETE FROM address WHERE address_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, addressId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public int findAddressId(Address address,Connection con) {
    int addressId = -1;

    String sql = "SELECT address_id FROM address WHERE country = ? AND state = ? AND city = ? AND street_name = ? AND house_name = ? AND pin_code = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, address.getCountry());
        ps.setString(2, address.getState());
        ps.setString(3, address.getCity());
        ps.setString(4, address.getStreet());
        ps.setString(5, address.getHouseName());
        ps.setString(6, address.getPinCode());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            addressId = rs.getInt("address_id");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return addressId;
}

}
