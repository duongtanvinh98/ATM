package atm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.internal.org.objectweb.asm.Type;

public class Customer{

    protected int Customerid;
    protected int Idcode;
    protected String Lastname, Name, Date, Status;

    public Customer() {
    }
    public Customer(int ccode) throws SQLException{
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Customer where Customerid = " + ccode);
            while (rs.next()) {
                Customerid = rs.getInt("Customerid");
                Lastname = rs.getString("Lastname");
                Name = rs.getNString("Name");
                Idcode = rs.getInt("Idcode");
                Date = rs.getString("Date");
                Status = rs.getString("Status");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public Customer(int Customerid, int Idcode, String Lastname, String Name, String Date, String Email, String Status) {
        this.Customerid = Customerid;
        this.Idcode = Idcode;
        this.Lastname = Lastname;
        this.Name = Name;
        this.Date = Date;
        this.Status = Status;
    }

    public Customer(int Customerid, String Lastname, String Name) {
        this.Customerid = Customerid;
        this.Lastname = Lastname;
        this.Name = Name;
    }

    
    public int getCustomerid() {
        return Customerid;
    }

    public void setCustomerid(int Customerid) {
        this.Customerid = Customerid;
    }

    public int getIdcode() {
        return Idcode;
    }

    public void setIdcode(int Idcode) {
        this.Idcode = Idcode;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

   // @Override
//    public String toString() {
//        return Customerid + " " + Lastname + " " + Name + " " + Idcode + " " + Date + " " + Email + " " + Status;
//    }

    private ResultSet executeQuery(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void SelectCustomer(int ccode) throws SQLException {
        
    }

    public int UpdateStatus(int ccode, String stt) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int rs = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Update Customer set Status=? where Customerid=?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, stt);
            pstm.setInt(2, ccode);
            int exc = pstm.executeUpdate();
            if (exc == 1) {
                rs = 1;
            } else rs = -1;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rs;
    }

    public static Customer MoreCustomes(String lname, String name, int idcode, String date, String status) throws SQLException {
        Connection conn = null;
        int ccode = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            CallableStatement cs= conn.prepareCall("{call CreateCustomer(?,?,?,?,?,?)}");
            cs.setString(1, lname);
            cs.setString(2, name);
            cs.setInt(3, idcode);
            cs.setString(4, date);
            cs.setString(5, status);
            cs.registerOutParameter(6,Type.INT);
            cs.execute();
            ccode=cs.getInt(6);
            return new Customer(ccode);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return null;
    }
}
