class Account implements BankAccount{
    private String username;
    private double balance;
    private String password;

    public Account(String accountNumber, double initialBalance , String password) {
        this.username = accountNumber;
        this.balance = initialBalance;
        this.password = password;
    }

    public void setUsername(String accountNumber) {
        this.username = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }
@Override
    public void deposit(double amount) {
        balance += amount;
    }

 @Override   
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
    }

    

}

