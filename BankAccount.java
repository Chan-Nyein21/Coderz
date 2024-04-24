/**
 * BankAccount
 */
public interface BankAccount {

    String getUsername();
    double getBalance();
    void deposit(double amount);
    void withdraw(double amount) throws InsufficientFundsException;
}