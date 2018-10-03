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

public class Deal extends Account{

    private int Dealid;
    private String Daytime, Task, Descrip;
    private Double Amountdeal, Lastbalance;

    public Deal() {
    }
    public Deal(int dcode) throws SQLException{
     Connection conn = null;
        PreparedStatement pstm = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Select * from Deal where Dealid=?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, dcode);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Dealid = rs.getInt("Dealid");
                Accountid = rs.getInt("Accountid");
                Daytime = rs.getString("Daytime");
                Task = rs.getString("Task");
                Descrip = rs.getString("Descrip");
                Amountdeal = rs.getDouble("Amountdeal");
                Lastbalance = rs.getDouble("Lastbalance");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
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
    public Deal(int Dealid,int Accountid, String Daytime, String Task, String Descrip, Double Amountdeal, Double Lastbalance) throws SQLException {
        super(Accountid);
        this.Dealid = Dealid;
        this.Daytime = Daytime;
        this.Task = Task;
        this.Descrip = Descrip;
        this.Amountdeal = Amountdeal;
        this.Lastbalance = Lastbalance;
    }

    public int getDealid() {
        return Dealid;
    }

    public void setDealid(int Dealid) {
        this.Dealid = Dealid;
    }

    public int getAccountid() {
        return Accountid;
    }

    public void setAccountid(int Accountid) {
        this.Accountid = Accountid;
    }

    public String getDaytime() {
        return Daytime;
    }

    public void setDatetime(String Daytime) {
        this.Daytime = Daytime;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String Task) {
        this.Task = Task;
    }

    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String Descrip) {
        this.Descrip = Descrip;
    }

    public Double getAmountdeal() {
        return Amountdeal;
    }

    public void setAmuontdeal(Double Amountdeal) {
        this.Amountdeal = Amountdeal;
    }

    public Double getLastbalance() {
        return Lastbalance;
    }

    public void setLastbalance(Double Lastbalance) {
        this.Lastbalance = Lastbalance;
    }

    @Override
    public String toString() {
        return Dealid + " " + Accountid + " " + Daytime + " " + Task + " " + Descrip + " " + Amountdeal + " " + Lastbalance;
    }
    public Vector toVector(){
        Vector vt = new Vector(10,10);
        vt.add(Dealid);
        vt.add(Accountid);
        vt.add(Daytime);
        vt.add(Task);
        vt.add(Descrip);
        vt.add(Amountdeal);
        vt.add(Lastbalance);
        return vt;
    } 
    public static Vector<Vector> getAll(int accode) throws SQLException{
        Connection conn = null;
        PreparedStatement pstm = null;
        Vector vt = new Vector(10,10);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Select Dealid From Deal where Accountid =?";
            pstm=conn.prepareStatement(sql);
            pstm.setInt(1, accode);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                Deal dl = new Deal(rs.getInt("Dealid"));
                vt.add(dl.toVector());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vt;
    }
    public double LastWithdrawal(int accode, double amountdeal) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        double bal, lastbal = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Select Balance from Account where Accountid =?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, accode);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                bal = rs.getDouble("Balance");
                lastbal = bal - amountdeal;
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastbal;
    }

    public double Balances(int accode) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        double balance = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Select Balance from Account where Accountid =?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, accode);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                  balance = rs.getDouble("Balance");
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        }
            return balance;
    }
    public void AddDeal(int accode,String time, String task,String descrip, double amountdeal,double Lastbalance) throws SQLException{
        Connection conn = null;
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            CallableStatement cs= conn.prepareCall("{call CreateDeal(?,?,?,?,?,?,?)}");
               cs.setInt(1, accode);
                    cs.setString(2, time);
                    cs.setString(3, task);
                    cs.setString(4, descrip);
                    cs.setDouble(5, amountdeal);
                    cs.setDouble(6, Lastbalance);
                    cs.registerOutParameter(7,Type.INT);
                    cs.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
             try {
                 if (conn != null) {
                     conn.close();
                 }
             } catch (SQLException se) {
                 se.printStackTrace();
             }
         }
                 
    }
    public int Withdrawal(int accode, String task,String descrip, double amountdeal) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
        String sql,time = timeFormat.format(today.getTime());
        double lastbalance = LastWithdrawal(accode, amountdeal);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            
                sql = "Update Account set Balance=? where Accountid=?";
                pstm = conn.prepareStatement(sql);
                pstm.setDouble(1, lastbalance);
                pstm.setInt(2, accode);
                int exc =pstm.executeUpdate();
                if(exc==1){
                    AddDeal(accode, time, task, descrip, amountdeal, lastbalance);
                }   
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return 0;
    }
    public double Lastsend(int accode, double amountdeal) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        double bal, lastbalance = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Select Balance from Account where Accountid =?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, accode);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                bal = rs.getDouble("Balance");
                lastbalance = bal + amountdeal;
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastbalance;
    }
    public int SendAmount(int accode, String task,String descrip, double amountdeal) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
        String sql,time = timeFormat.format(today.getTime());
        double lastbalance = Lastsend(accode, amountdeal);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            if (amountdeal >= 10) {
                sql = "Update Account set Balance=? where Accountid=?";
                pstm = conn.prepareStatement(sql);
                pstm.setDouble(1, lastbalance);
                pstm.setInt(2, accode);
                int exc =pstm.executeUpdate();
                if(exc==1){
                    AddDeal(accode, time, task, descrip, amountdeal, lastbalance);
                }    
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return 0;
    } 
    public int ChuyenTien(int ac1,int ac2,double amountdeal) throws SQLException{
        Connection conn = null;
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
        String time= timeFormat.format(today.getTime());
        double acWithdrawal = LastWithdrawal(ac1, amountdeal);
        double acSend = Lastsend(ac2, amountdeal);    
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;instance=SQLEXPRESS;databaseName=ATM", "sa", "tanvinh");
            String sql = "Update Account set Balance=? where Accountid=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setDouble(1, acWithdrawal);
            pstm.setInt(2, ac1);
            pstm.executeUpdate();
            AddDeal(ac1, time, "Chuyển tiền", "Đã chuyển", amountdeal, acWithdrawal);
//            sql="Update Account set Balance=? where Accountid=?";    
            PreparedStatement pt=conn.prepareStatement(sql);
            pt.setDouble(1,acSend);
            pt.setInt(2, ac2);
            pt.executeUpdate();
            AddDeal(ac2, time, "Nhận tiền", "Đã nhận", amountdeal,acSend);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Deal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
