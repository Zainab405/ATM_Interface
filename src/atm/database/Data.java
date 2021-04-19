package atm.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



public class Data {
 private static String url = "";
    private static Connection con;
    private static String dbuser = "admin"; // uussername  of  server
    private static String dbpass = "";  // password of serve H2 Database   `timetablems`
    static PreparedStatement pst = null;
    static ResultSet rs = null;

    private static void setURl() {
        url = "jdbc:h2:./database/atm"; //thiis db username 
    }
 
    // this method used to set conncetion between java and mysql using jdbc
    public static Connection setConnection() {
        try { 
            setURl();
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, dbuser, dbpass);
           
            return con;      

        } catch (SQLException ex) {
         System.out.println(ex.getMessage());
     } catch (ClassNotFoundException ex) { 
     }
        return null;
    }
 public static boolean checkUserAndPass(String account_number, String password) {

        try {

            setConnection();
            Statement stmt = con.createStatement();
            String sql = "select account_number , password from users where account_number ='"+account_number+"' and password ='"+password+"' ";
           stmt.executeQuery(sql);         
           if( stmt.executeQuery(sql).first())
           {
                con.close();
                return true;
            }
            }catch (SQLException ex) {
            System.out.println(ex.getMessage() +" ...."+ex.getErrorCode());
        } 

        return false;
    }

    public static boolean MangmentQueries(String sql) {

        try {
            setConnection();
            Statement stmt = con.createStatement();
            stmt.execute(sql);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() +" "+ex.getErrorCode());
        }
        return false;

    }
   
    public static int getMakID(String cloName, String tbName) {

        String sql = "select max(" + cloName + ") from " + tbName + "";
        int max = 1;
        try {
            setConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                max = rs.getInt(1);
            }
            return max + 1;
        } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "s"+ex.getMessage() +" 4"+ex.getErrorCode());

        }
        return 0;
    }
    
    /*
    this method used to get dtat from Table by providing chooseFields and where Fields and values
    
    ex: 
    SQl=" select col1,col2 from TableName where id = 10 ";
        String[] chooseFields is (col1,col2,col3)
        String[] whereFields  is [id]
        values is [10]
        op is [ = ]
        sep is [ , ]
    
     */
      public static ResultSet select(String tbName, String[] chooseFields, String[] whereFields, String[] values, String op, String sep) {

        String sql = "select ";
        for (int i = 0; i < chooseFields.length; i++) {
            sql += chooseFields[i] + ",";
        }
        sql = sql.substring(0, sql.length() - 1);

        sql += " from " + tbName + " where";
        String slect = makewhere(sql, whereFields, op, sep);

        try {
                
            pst = setConnection().prepareStatement(slect);

            for (int i = 1; i <= values.length; i++) {
                pst.setString(i, values[i - 1]);
            }
            // rs=pst.executeQuery();

            return pst.executeQuery();

        } catch (Exception e) {
        }

        return null;
    }

      /*
      this method part of above method Select
      */
    public static String makewhere(String sql, String[] fields, String op, String sep) {

        String sql1 = sql;
        for (int i = 0; i < fields.length; i++) {
            sql1 += " " + fields[i] + " " + op + "? " + sep;
        }

        sql1 = sql1.substring(0, sql1.length() - sep.length());
        return sql1;
    }

    
}
