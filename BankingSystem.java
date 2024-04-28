// Main class to demonstrate banking operations
import java.io.*;
import java.util.*;

public class BankingSystem {

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Bank bank = Bank.getInstance();

        Account acc1 = new Account("001", 1000 , "123abc");
        Account acc2 = new Account("002", 2000 , "456def");
        bank.addAccount(acc1);
        bank.addAccount(acc2);

        SignUp_Or_SignIn(bank);
        

    }

    public static void SignUp_Or_SignIn(Bank bank){
        Scanner input = new Scanner(System.in);
        try{
            System.out.println("Do you want to \n1.Sign in (or)\n2.Sign Up or \n3. close the system.");
            System.out.println();
            System.out.print("Choose 1 or 2 or 3 : ");
            int chooseOne = input.nextInt();
            if(chooseOne == 1){
                SignIn(bank);
            }else if(chooseOne == 2){
                SignUp(bank);
            }else if(chooseOne ==3){
                Close_system();
            }
            else{
                System.out.println("Please select 1 or 2 or 3.");
                SignUp_Or_SignIn(bank);
            }
        
        }catch(InputMismatchException exception){
            System.out.println();
            System.out.println("Please enter 1 or 2 or 3.");
            System.out.println();
            SignUp_Or_SignIn(bank);
        }
        
    }
    public static void Close_system(){
        System.out.println("Thank you.\n \t ***** Have a Nice day. *****");
        System.exit(0);
    }

    public static void SignIn(Bank bank){
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter your Username\t: ");
        Account acc = null ;
        String username = input.nextLine();
        List<Account> accs = bank.getAccounts();
        boolean checkusername = false;
        boolean checkpw = false;
        for(int i = 0 ; i < accs.size() ; i++){

            if(username.equals(accs.get(i).getUsername())){
                checkusername = true;
                System.out.print("Please Enter your password\t: ");
                String password = input.nextLine();

                if(password.equals(accs.get(i).getPassword())){
                    acc = accs.get(i);
                    checkpw = true;
                    break;
                }
                break;
            }
        }
        if(checkusername == true && checkpw == true){

            System.out.println("Signing In successful.");    
            Control(acc , bank);
        }else if(checkusername == false){
            System.out.println("There is no account with this username.");
            SignUp_Or_SignIn(bank);
        }else{
            System.out.println("Incorrect password. Sign in again.");
            SignUp_Or_SignIn(bank);
        }
    }

    public static void Control(Account acc , Bank bank){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("What do you want to do?\n 1. Deposit\n 2. Withdraw\n 3. Transfer\n 4. Check Balance \nChoose : ");
                int chooseTwo = scanner.nextInt();
                if( chooseTwo == 1){

                    System_Deposite(acc , bank);

                }else if(chooseTwo ==2){
                    
                    System_Withdraw(acc , bank);
                }else if(chooseTwo ==3){
                    
                    Transfer(acc , bank);
                }else if(chooseTwo == 4){
                    CheckBalance(acc , bank);
                }
                else{
                    System.out.println("Invalid option.\tPlease choose 1 or 2 or 3 or  4.");
                    Control(acc, bank);
                }
        }catch(NumberFormatException exception){
            System.out.println("Invalid input.");
            Control(acc, bank);
        }
        
    }

    private static void CheckBalance(Account acc, Bank bank) {
        
        try{
           System.out.println("You have " + acc.getBalance() + " in your account.");
           Logout(acc, bank);
        }
        catch(InputMismatchException exception){
            System.out.println();
            System.out.println("Please enter 1 or 2 or 3.");
            System.out.println();
            Control(acc , bank);
        } 
        catch(NumberFormatException exception){
            System.out.println("Invalid input.");
            Control(acc, bank);
        }
        catch(Exception exception){
            System.out.println("System Error.");
            SignUp_Or_SignIn(bank);
        }

    }

    public static void SignUp(Bank bank){

        Scanner input = new Scanner(System.in);
        List<Account> accounts = bank.getAccounts();

        System.out.print("Enter username\t: ");
        String username = input.nextLine();
        for(int i = 0 ; i < accounts.size() ; i++){
            if(username.equals(accounts.get(i).getUsername())){
                System.out.println("User name is already exists.");
                SignUp(bank);
            }
        }
        System.out.print("Set your password\t: ");
        String password = input.nextLine();
        System.out.print("Enter the amount you want to deposit\t: ");
        try{
            double depositeAmount = input.nextDouble();
            Account acc = new Account(username, depositeAmount, password);
            bank.addAccount(acc);
            Control(acc, bank);
        }catch(NumberFormatException exception){
            System.out.println("Account Signup Failed.");
            SignUp_Or_SignIn(bank);
        }catch(Exception exception){
            System.out.println("An error has occurred.");
            SignUp(bank);
        }


    }

    public static void Transfer(Account account ,Bank bank){
        Account receiver = null;
        List<Account> accounts = bank.getAccounts();
        boolean checkacc = false;
        Scanner input = new Scanner(System.in);
        try{
            System.out.print("Which account you want to transfer?\nEnter username\t: ");
            String receiveStr = input.nextLine();

            for(int i = 0 ; i < accounts.size(); i++){

                if(receiveStr.equals(accounts.get(i).getUsername())){
                    checkacc = true;
                    System.out.print("Enter the amount you want to transfer\t: ");
                    double transfer_amount = scanner.nextDouble();
                    if(transfer_amount <=0 ){
                        System.out.println("Invalid amount. \t***Amount should be greater than 0.***\n");
                        Control(account, bank);
                    }else if(transfer_amount > account.getBalance()){
                        System.out.println("You don't have sufficient balance to do this transaction.");
                        Control(account, bank);
                    }
                    else{
                        receiver = accounts.get(i);
                        Transaction.makeWithdrawal(account, transfer_amount);
                        Transaction.makeDeposit(receiver, transfer_amount);

                        System.out.println("Transfer "+ transfer_amount +" to " + receiveStr + " account is successful.\n");
                        System.out.println("Account balances after transactions:");
                        System.out.print("Username: " + account.getUsername() + ", Balance: " + account.getBalance());
                        writeTransactionToFile(account , "transfer");            
                        writeTransactionToFile(receiver , "receive");
                        Logout(account, bank);
                    }
                }

            }
            if(checkacc == false){
                System.out.println("\nThe account you want to transfer doesn't exist.");
                Control(account, bank);
            }
            
            
        }catch(NumberFormatException exception){
            System.out.println("\nTransfer amount cannot contains characters.");
            System_Deposite(account , bank);
        }catch(Exception exception){
            System.out.println("Account Signup Failed.");
            Control(account , bank);
        }

    }

    public static void System_Withdraw(Account account , Bank bank){

        System.out.print("Enter the amount you want to withdraw\t: ");
        try{
            double withdrawAmount = scanner.nextDouble();
            if(withdrawAmount <=0){
                System.out.println("Invalid amount. \t***Amount should be greater than 0.***\n");
                Control(account, bank);
            }else if (account.getBalance() < withdrawAmount){
                System.out.println("Insufficient Transfer amount.");
                Control(account, bank);
            }
            else{
                Transaction.makeWithdrawal(account, withdrawAmount);

                System.out.println("Withdraw "+ withdrawAmount +" from your account is successful.\n");
                System.out.println("Account balances after transactions:");
                System.out.println("Username: " + account.getUsername() + ", Balance: " + account.getBalance());
                writeTransactionToFile(account , "withdraw");
                Logout(account, bank);
            }

        }catch(NumberFormatException exception){
            System.out.println("Withdraw amount cannot contains characters.");
            System_Deposite(account , bank);
        }

    }

    public static void System_Deposite(Account account , Bank bank){

        System.out.print("Enter the amount you want to deposit\t: ");

            try{
                double depositeAmount = scanner.nextDouble();
                if(depositeAmount <= 0){
                    System.out.println("Invalid amount.\t***Amount should be greater than 0.***\n");
                    Control(account, bank);
                }
                else{
                    Transaction.makeDeposit(account, depositeAmount);
                    System.out.println("Deposit "+ depositeAmount +" to your account is successful.\n");
                    System.out.println("Account balances after transactions:");
                    System.out.println("Username: " + account.getUsername() + ", Balance: " + account.getBalance());
                    writeTransactionToFile(account , "Deposite");
                    Logout(account, bank);
                }
                
            }catch(NumberFormatException exception){
                System.out.println("Deposit amount cannot contains characters.");
                System_Deposite(account , bank);
            }
    }

    public static void Logout(Account account , Bank bank){
        System.out.println("Do you want to Logout?\n 1.Yes\n 2.No \n Choose 1 or 2 : ");
            int chooseThree = scanner.nextInt();
            if(chooseThree == 1 ){
                System.out.println("Successfully Logout.\n");
                SignUp_Or_SignIn(bank);
            }else if (chooseThree == 2){
                Control(account, bank);
            }else{
                System.out.println("Invalid input.\tFailed to Logout.");
                Control(account, bank);
            }
    }

    public static void writeTransactionToFile(Account account , String text) {
        String filename = "transaction_details.txt";
        Date currentTime = new Date();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename , true))) {
            writer.write("\nTransaction Details:\n");
            writer.write("Account Number: " + account.getUsername() +  " , After "+ text +" Balance: " + account.getBalance() + "Time: " + currentTime + "\n");
            
            System.out.println();
            // System.out.println("Transaction details written to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}