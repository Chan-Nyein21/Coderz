class Transaction {
    public static void makeDeposit(Account account, double amount) {
        account.deposit(amount);
    }

    public static void makeWithdrawal(Account account, double amount) {
        try {
            account.withdraw(amount);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
