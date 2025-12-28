package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectivity.DBConnection;
import model.Address;

public class AddressDAO {

    // Insert Address into DB and return generated ID
    public int addAddress(Address address) {
        String sql = "INSERT INTO address (street_name, house_name, city, state, country, pin_code) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, address.getStreet());
            ps.setString(2, address.getHouseName());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getCountry());
            ps.setString(6, address.getPinCode());

            ps.executeUpdate();

            // Get auto-generated address_id
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // return -1 if insert failed
    }

    // Fetch all addresses from DB
    public List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM address";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Address address = new Address(
                    rs.getInt("address_id"),
                    rs.getString("country"),
                    rs.getString("state"),
                    rs.getString("city"),
                    rs.getString("street_name"),
                    rs.getString("house_name"),
                    rs.getString("pin_code")
                );
                addresses.add(address);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresses;
    }
}
