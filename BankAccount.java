import java.util.ArrayList;
import java.time.LocalDate;

public class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;
    private ArrayList<Transaction> transactions;

    public BankAccount(String accountNumber,
                       String holderName,
                       double initialDeposit) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
        transactions.add(new Transaction(
            "Account Created + Initial Deposit",
            initialDeposit,
            LocalDate.now().toString()
        ));
    }

    public void deposit(double amount) {
        if (amount <= 0) return;
        balance += amount;
        transactions.add(new Transaction(
            "Deposit",
            amount,
            LocalDate.now().toString()
        ));
    }

    public void withdraw(double amount) {
        if (amount <= 0) return;
        if (amount > balance) return;
        balance -= amount;
        transactions.add(new Transaction(
            "Withdrawal",
            amount,
            LocalDate.now().toString()
        ));
    }

    public void transfer(BankAccount target,
                         double amount) {
        if (amount > balance) return;
        this.withdraw(amount);
        target.deposit(amount);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}