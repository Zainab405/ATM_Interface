package atm.classes;

import atm.database.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class User {

    private int id;
    private String username;
    private String account_number;

    private String password;
    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addNewUser() {

        boolean add = Data.MangmentQueries("insert into users values('" + id + "','" + username + "','" + account_number + "','" + password + "','0')");
        if (add) {
            System.out.println(" Created sccesufully");
        } else {

            System.out.println("Error Durring create user ");
        }

    }

    public static int getUserIdByAccountNumber(String accountNumber) {

        int user_id = 0;

        ResultSet rs = Data.select("users", new String[]{"user_id"}, new String[]{"account_number"}, new String[]{accountNumber}, "=", "and");

        try {
            while (rs.next()) {
                user_id = rs.getInt("user_id");
            }
        } catch (SQLException ex) {

        }

        return user_id;
    }
}
