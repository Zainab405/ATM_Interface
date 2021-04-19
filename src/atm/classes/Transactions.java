
package atm.classes;

import atm.database.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transactions {
    
    private int id;
    private String op_type;
    private String amount;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
     public void addOperation(){
        
            SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date da=new Date();
            
                   
            boolean ad= Data.MangmentQueries("insert into operations values('"+id+"','"+op_type+"','"+amount+"','"+user_id+"','"+d.format(da)+"')");
        
        if (ad) {
           System.out.println("Operation Created sccesufully");
        } else {

             System.out.println("Error Durring Add");
         }
        
        }
    
    
}
