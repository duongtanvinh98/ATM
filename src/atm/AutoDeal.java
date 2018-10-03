
package atm;

import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class AutoDeal extends Account{
    private int Accountid,ReceiveAcId;
    private double Level;
    private String Dealtype,Description;

    public AutoDeal() {
    }

    public AutoDeal(int Accountid, int ReceiveAcId, double Level, String Dealtype, String Description, int accode) throws SQLException {
        super(Accountid);
        this.Accountid = Accountid;
        this.ReceiveAcId = ReceiveAcId;
        this.Level = Level;
        this.Dealtype = Dealtype;
        this.Description = Description;
    }
    
}
