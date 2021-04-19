package atm.main;

import atm.classes.Transactions;
import atm.classes.User;
import atm.database.Data;
import static atm.database.Data.setConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public void showMainData() {
        System.out.println("CHOOSE YOUR TARANSACTIONS \n ");
        System.out.println(" (1)- Withdraw \n (2)- Deposit \n (3)- Transfer \n (4)- Transaction History \n (5)- exit   ");
    }

    public void showUserMessage(String transaction_Type) {
        System.out.println("====== " + transaction_Type + " TRANSACTION ======== ");
        System.out.println("Please Enter The Amount : ");
    }

    public static void main(String[] args) throws SQLException {
        String userAccountNumber;
        String userPassword;
        String username;
       
        
        boolean login;
        double amount;
        Scanner input = new Scanner(System.in);
        Main main = new Main();

        System.out.print("Please Enter Your Account Number :  ");
        userAccountNumber = input.next();
        System.out.print("Please Enter Your Password :  ");
        userPassword = input.next();

        login = Data.checkUserAndPass(userAccountNumber, userPassword);
        if (login) {
            main.showMainData();
            String userChoose = input.next();

            // create object of transaction class
            Transactions transactions = new Transactions();

            switch (userChoose) {
                case "1":
                    main.showUserMessage("withdraw ");
                    amount = input.nextDouble();
                    transactions.setOp_type("withdraw");
                    transactions.setAmount(String.valueOf(amount));
                    transactions.setUser_id(User.getUserIdByAccountNumber(userAccountNumber));
                    transactions.setId(Data.getMakID("id", "operations"));
                    transactions.addOperation();
                    System.out.println("ok operation done successfully");
                    break;
                case "2":

                    main.showUserMessage("deposit ");
                    amount = input.nextDouble();
                    transactions.setOp_type("deposit");
                    transactions.setAmount(String.valueOf(amount));
                    transactions.setUser_id(User.getUserIdByAccountNumber(userAccountNumber));
                    transactions.setId(Data.getMakID("id", "operations"));
                    transactions.addOperation();
                    System.out.println("\n thank you ");
                    break;

                case "3":
                    String numberTransfer;
                    main.showUserMessage("transfer ");
                    amount = input.nextDouble();
                    System.out.println("Enter the account Number you will transfer money ");
                    numberTransfer = input.next();

                    // make transfer operation 
                    transactions.setOp_type("transfer to account number " + numberTransfer);
                    transactions.setAmount(String.valueOf(amount));
                    transactions.setUser_id(User.getUserIdByAccountNumber(userAccountNumber));
                    transactions.setId(Data.getMakID("id", "operations"));
                    transactions.addOperation();

                    // make deposit to user account from transferOperation
                    Transactions deposit = new Transactions();
                    deposit.setOp_type("deposit money from  account number " + numberTransfer);
                    deposit.setAmount(String.valueOf(amount));
                    deposit.setId(Data.getMakID("id", "operations"));
                    transactions.setUser_id(User.getUserIdByAccountNumber(numberTransfer));

                    System.out.println("\n thank you ");
                    break;

                case "4":
                    System.out.println("=======  Transaction History ========== \n Transaction Type \t| Amount \t|    Date");
                    ResultSet rs = Data.select("user_transactions", new String[]{"op_type,amount,timestamp"}, new String[]{"account_number"}, new String[]{userAccountNumber}, "=", "and");
                    
                    while (rs.next()) {
                        System.out.println("\t"+rs.getString("OP_TYPE") + "\t         |"+rs.getDouble("AMOUNT")+"\t        |"+rs.getString("TIMESTAMP"));
                    }

                    break;

                case "5":
                    break;

            }

        } else if (userAccountNumber.equals("101") && userPassword.equals("admin")) {
            
            System.out.println(" ======= Hello Admin  ====== \n create New User ");
              User user = new User();
              System.out.print("Please Enter username : ");
              username=input.next();
              System.out.print("Please Enter account number : " );
              userAccountNumber=input.next();
              
            System.out.print("please Enter Password : ");
            userPassword=input.next();
              
                user.setPassword(userPassword);
                user.setAccount_number(userAccountNumber);
              user.setPassword(userPassword);
              user.setUsername(username);
               user.setId(Data.getMakID("USER_ID", "users"));
           user.addNewUser();
            
            
                System.out.println("OK User created Successfuly .. now user can login with account number and password");
       
     

        } else {
            System.err.println("\"account number or password not correct\"");

        }

    }

}
