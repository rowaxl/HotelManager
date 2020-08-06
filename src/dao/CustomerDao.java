package dao;

import helper.DBConnection;
import models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDao {
    public static Customer findCustomer(String email) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Customer WHERE email_address = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();


            if (!rs.next()) {
                return null;
            }

            Customer result = new Customer(
                    rs.getString("email_address"),
                    rs.getString("full_name"),
                    rs.getString("phone_num")
            );

            con.close();

            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void addNewCustomer(Customer c) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Customer (email_address, full_name, phone_num) VALUES (?, ?, ?)");
            ps.setString(1, c.getEmailAddress());
            ps.setString(2, c.getFullName());
            ps.setString(3, c.getPhoneNum());

            ps.executeUpdate();

            con.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
