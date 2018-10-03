package atm;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.print.attribute.Size2DSyntax.MM;
import jdk.internal.org.objectweb.asm.Type;

public class Account extends Customer{

    protected int Accountid;
    private int Customerid, Pass;
    private double Balance;
    private String Lastlogin, Status;

    public Account() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Account(int accode) throws SQLException{
          this();
          Connection conn = null;
          PreparedStatement pstm=null;
          conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
          String sql="SELECT  Account.Accountid ,Customer.Customerid, Customer.Lastname, Customer.Name, Account.Balance, Account.Pass, Account.Lastlogin, \n" +
          "Account.Status\n" +
          "FROM Account INNER JOIN Customer ON Account.Customerid = Customer.Customerid where Account.Accountid=?";
          pstm=conn.prepareStatement(sql);
          pstm.setInt(1, accode);
          ResultSet rs = pstm.executeQuery();          
          while (rs.next()) {
              Accountid = rs.getInt("Accountid");
              Customerid = rs.getInt("Customerid");
              Lastname = rs.getString("Lastname");
              Name=rs.getString("Name");
              Balance = rs.getDouble("Balance");
              Pass = rs.getInt("Pass");
              Lastlogin = rs.getString("Lastlogin");
              Status = rs.getString("status");
          }
          try {
              if (conn != null) {
                  conn.close();
              }
          } catch (SQLException se) {
              se.printStackTrace();
          }
    }

    
    public Account(String Lastname,String Name,int Customerid,int Accountid,  int Pass, double Balance, String Lastlogin, String Status) throws SQLException {
        super(Customerid,Lastname,Name);
        this.Accountid = Accountid;
        this.Pass = Pass;
        this.Balance = Balance;
        this.Lastlogin = Lastlogin;
        this.Status = Status;
    }
    
    public int getAccountid() {
        return Accountid;
    }

    public void setAccountid(int Accountid) {
        this.Accountid = Accountid;
    }

    public int getCustomerid() {
        return Customerid;
    }

    public void setCustomerid(int Customerid) {
        this.Customerid = Customerid;
    }

    public int getPass() {
        return Pass;
    }

    public void setPass(int Pass) {
        this.Pass = Pass;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public String getLastlogin() {
        return Lastlogin;
    }

    public void setLastlogin(String Lastlogin) {
        this.Lastlogin = Lastlogin;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
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

    @Override
    public String toString() {
        return Accountid + " " + Customerid + " " + Balance + " " + Pass + " " + Lastlogin + " " + Status +" "+Lastname+" "+Name;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public Vector toVector(){
        Vector vt = new Vector(10,10);
        vt.add(Accountid+" - "+Lastname+" "+Name);
//        vt.add(Name);
        return vt;
    } 
    public static Vector<Account> getAccount() throws SQLException{
        Connection conn = null;
        PreparedStatement pstm = null;
        Vector vt = new Vector(10,10);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql="SELECT Account.Accountid, Customer.Lastname, Customer.Name\n" +
            "FROM Account INNER JOIN Customer ON Account.Customerid = Customer.Customerid";
            pstm=conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
              Account ac=new Account(rs.getInt("Accountid"));
              vt.add(ac.toVector());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vt;
    }     
    public static Account UpdatePass(int accode, int passw) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Update Account set Pass=? where Accountid=?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, passw);
            pstm.setInt(2, accode);
            pstm.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public int UpdateStatus(String stt, int accode) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int rs = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Update Account set Status=? where Accountid=?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, stt);
            pstm.setInt(2, accode);
            int exc = pstm.executeUpdate();
            if (exc == 1) {
                rs = 1;
            } else rs = -1;
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Account Loginacount(int accode, int passw) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
        String time = timeFormat.format(today.getTime());
        Account acc= null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Select * from Account where Accountid=? and Pass=?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, accode);
            pstm.setInt(2, passw);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                sql = "Update Account set Lastlogin = ? where Accountid =?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, time);
                pstm.setInt(2, accode);
                pstm.executeUpdate();
                acc=new Account(rs.getInt("Accountid"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return acc;
    }
    public static Account MoreAccount(int customerid, double balance, int pass,String lastlogin, String stt) throws SQLException {
        Connection conn = null;
        int accode=0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            CallableStatement cs= conn.prepareCall("{call CreateAccount(?,?,?,?,?,?)}");
            cs.setInt(1, customerid);
            cs.setDouble(2, balance);
            cs.setInt(3, pass);
            cs.setString(4, lastlogin);
            cs.setString(5, stt);
            cs.registerOutParameter(6,Type.INT);
            boolean rs = cs.execute();
            while (rs == true){
                cs.close();
            }
            accode=cs.getInt(6);
            return new Account(accode);
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
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
