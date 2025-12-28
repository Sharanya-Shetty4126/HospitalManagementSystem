import DAO.AddressDAO;
import model.Address;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {

        AddressDAO dao = new AddressDAO();

        // 1️⃣ Create Address object
        Address addr = new Address(
                0,                     // ID ignored (auto-generated)
                "India",
                "Tamil Nadu",
                "Chennai",
                "MG Road",
                "Green Villa",
                "600001"
        );

        // 2️⃣ Insert Address
        int addressId = dao.addAddress(addr);

        if (addressId > 0) {
            System.out.println("Address inserted successfully. ID = " + addressId);
        } else {
            System.out.println("Address insertion failed");
        }

        // 3️⃣ Fetch all addresses
        List<Address> list = dao.getAllAddresses();

        System.out.println("----- ADDRESS LIST -----");
        for (Address a : list) {
            System.out.println(a);
        }
    }
}
