import java.util.*;

// Bank class implementing Singleton pattern
class Bank {
    private static Bank instance;
    private List<Account> accounts;

    private Bank() {
        accounts = new ArrayList<>();
    }
 
    public static synchronized Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
