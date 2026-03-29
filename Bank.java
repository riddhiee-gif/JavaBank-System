import java.util.ArrayList;

public class Bank {
    private ArrayList<BankAccount> accounts;
    private int accountCounter;

    public Bank() {
        accounts = new ArrayList<>();
        accountCounter = 1001;
    }

    public BankAccount createAccount(String name,
                                     double deposit) {
        String accNo = "ACC" + accountCounter++;
        BankAccount account = new BankAccount(
            accNo, name, deposit);
        accounts.add(account);
        return account;
    }

    public BankAccount findAccount(String accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber()
                   .equals(accNo)) {
                return acc;
            }
        }
        return null;
    }

    public ArrayList<BankAccount> getAllAccounts() {
        return accounts;
    }
}