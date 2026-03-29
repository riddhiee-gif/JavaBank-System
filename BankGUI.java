import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BankGUI extends JFrame {

    private Bank bank;
    private JTextArea outputArea;

    public BankGUI() {
        bank = new Bank();
        
        // Window settings
        setTitle("JavaBank System");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel(
            "🏦 JavaBank System",
            SwingConstants.CENTER);
        header.setFont(
            new Font("Arial", Font.BOLD, 26));
        header.setBackground(
            new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBorder(BorderFactory
            .createEmptyBorder(15, 0, 15, 0));
        add(header, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(
            new GridLayout(4, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory
            .createEmptyBorder(15, 15, 15, 15));
        buttonPanel.setBackground(
            new Color(240, 240, 240));
        buttonPanel.setPreferredSize(
            new Dimension(200, 0));

        // All buttons
        String[] buttons = {
            "Create Account",
            "Deposit",
            "Withdraw",
            "Transfer",
            "Transaction History",
            "Account Details",
            "All Accounts",
            "Clear Output"
        };

        for (String btnText : buttons) {
            buttonPanel.add(createButton(btnText));
        }

        add(buttonPanel, BorderLayout.WEST);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(
            new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(
            new Color(30, 30, 30));
        outputArea.setForeground(
            new Color(0, 255, 0));
        outputArea.setBorder(BorderFactory
            .createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = 
            new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory
            .createTitledBorder("  Output Console"));
        add(scrollPane, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
            "  JavaBank v1.0 | Built with Java Swing");
        footer.setFont(
            new Font("Arial", Font.ITALIC, 11));
        footer.setBorder(BorderFactory
            .createEmptyBorder(5, 10, 5, 0));
        add(footer, BorderLayout.SOUTH);

        printWelcome();
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(
            new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory
            .createEmptyBorder(10, 5, 10, 5));

        // Hover effect
        btn.addMouseListener(
            new java.awt.event.MouseAdapter() {
            public void mouseEntered(
                java.awt.event.MouseEvent e) {
                btn.setBackground(
                    new Color(0, 153, 255));
            }
            public void mouseExited(
                java.awt.event.MouseEvent e) {
                btn.setBackground(
                    new Color(0, 102, 204));
            }
        });

        btn.addActionListener(
            e -> handleButton(text));
        return btn;
    }

    private void handleButton(String action) {
        switch (action) {
            case "Create Account":
                showCreateAccount(); break;
            case "Deposit":
                showDeposit(); break;
            case "Withdraw":
                showWithdraw(); break;
            case "Transfer":
                showTransfer(); break;
            case "Transaction History":
                showHistory(); break;
            case "Account Details":
                showDetails(); break;
            case "All Accounts":
                showAllAccounts(); break;
            case "Clear Output":
                outputArea.setText("");
                printWelcome(); break;
        }
    }

    private void showCreateAccount() {
        JTextField nameField = new JTextField();
        JTextField depositField = new JTextField();

        Object[] fields = {
            "Account Holder Name:", nameField,
            "Initial Deposit (Rs.):", depositField
        };

        int result = JOptionPane.showConfirmDialog(
            this, fields,
            "Create New Account",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = 
                    nameField.getText().trim();
                double deposit = Double.parseDouble(
                    depositField.getText().trim());

                if (name.isEmpty()) {
                    showError("Name cannot be empty!");
                    return;
                }
                if (deposit <= 0) {
                    showError("Deposit must be positive!");
                    return;
                }

                BankAccount acc = 
                    bank.createAccount(name, deposit);
                appendOutput(
                    "✅ Account Created Successfully!");
                appendOutput("Account Number : " +
                    acc.getAccountNumber());
                appendOutput("Account Holder : " +
                    acc.getHolderName());
                appendOutput("Initial Balance: Rs." +
                    deposit);
                appendOutput(
                    "----------------------------");

            } catch (NumberFormatException ex) {
                showError("Enter a valid amount!");
            }
        }
    }

    private void showDeposit() {
        JTextField accField = new JTextField();
        JTextField amtField = new JTextField();

        Object[] fields = {
            "Account Number:", accField,
            "Deposit Amount (Rs.):", amtField
        };

        int result = JOptionPane.showConfirmDialog(
            this, fields,
            "Deposit Money",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                BankAccount acc = bank.findAccount(
                    accField.getText().trim());
                if (acc != null) {
                    double amt = Double.parseDouble(
                        amtField.getText().trim());
                    if (amt <= 0) {
                        showError(
                            "Amount must be positive!");
                        return;
                    }
                    acc.deposit(amt);
                    appendOutput(
                        "✅ Deposit Successful!");
                    appendOutput("Account  : " +
                        acc.getAccountNumber());
                    appendOutput("Deposited: Rs." + amt);
                    appendOutput("Balance  : Rs." +
                        acc.getBalance());
                    appendOutput(
                        "----------------------------");
                } else {
                    showError("Account not found!");
                }
            } catch (NumberFormatException ex) {
                showError("Enter a valid amount!");
            }
        }
    }

    private void showWithdraw() {
        JTextField accField = new JTextField();
        JTextField amtField = new JTextField();

        Object[] fields = {
            "Account Number:", accField,
            "Withdrawal Amount (Rs.):", amtField
        };

        int result = JOptionPane.showConfirmDialog(
            this, fields,
            "Withdraw Money",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                BankAccount acc = bank.findAccount(
                    accField.getText().trim());
                if (acc != null) {
                    double amt = Double.parseDouble(
                        amtField.getText().trim());
                    if (amt <= 0) {
                        showError(
                            "Amount must be positive!");
                        return;
                    }
                    if (amt > acc.getBalance()) {
                        showError(
                            "Insufficient Balance!\n" +
                            "Available: Rs." +
                            acc.getBalance());
                        return;
                    }
                    acc.withdraw(amt);
                    appendOutput(
                        "✅ Withdrawal Successful!");
                    appendOutput("Account  : " +
                        acc.getAccountNumber());
                    appendOutput("Withdrawn: Rs." + amt);
                    appendOutput("Balance  : Rs." +
                        acc.getBalance());
                    appendOutput(
                        "----------------------------");
                } else {
                    showError("Account not found!");
                }
            } catch (NumberFormatException ex) {
                showError("Enter a valid amount!");
            }
        }
    }

    private void showTransfer() {
        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();
        JTextField amtField = new JTextField();

        Object[] fields = {
            "From Account Number:", fromField,
            "To Account Number:", toField,
            "Transfer Amount (Rs.):", amtField
        };

        int result = JOptionPane.showConfirmDialog(
            this, fields,
            "Transfer Money",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                BankAccount from = bank.findAccount(
                    fromField.getText().trim());
                BankAccount to = bank.findAccount(
                    toField.getText().trim());

                if (from == null || to == null) {
                    showError(
                        "One or both accounts " +
                        "not found!");
                    return;
                }

                double amt = Double.parseDouble(
                    amtField.getText().trim());

                if (amt <= 0) {
                    showError(
                        "Amount must be positive!");
                    return;
                }

                if (amt > from.getBalance()) {
                    showError(
                        "Insufficient Balance!\n" +
                        "Available: Rs." +
                        from.getBalance());
                    return;
                }

                from.transfer(to, amt);
                appendOutput(
                    "✅ Transfer Successful!");
                appendOutput("From   : " +
                    from.getAccountNumber() +
                    " (" + from.getHolderName() + ")");
                appendOutput("To     : " +
                    to.getAccountNumber() +
                    " (" + to.getHolderName() + ")");
                appendOutput("Amount : Rs." + amt);
                appendOutput(
                    "----------------------------");

            } catch (NumberFormatException ex) {
                showError("Enter a valid amount!");
            }
        }
    }

    private void showHistory() {
        String accNo = JOptionPane.showInputDialog(
            this,
            "Enter Account Number:",
            "Transaction History",
            JOptionPane.PLAIN_MESSAGE);

        if (accNo != null && 
            !accNo.trim().isEmpty()) {
            BankAccount acc = bank.findAccount(
                accNo.trim());
            if (acc != null) {
                appendOutput("📋 Transaction History: " +
                    acc.getHolderName());
                for (Transaction t : 
                    acc.getTransactions()) {
                    appendOutput("  " + t.toString());
                }
                appendOutput("Current Balance: Rs." +
                    acc.getBalance());
                appendOutput(
                    "----------------------------");
            } else {
                showError("Account not found!");
            }
        }
    }

    private void showDetails() {
        String accNo = JOptionPane.showInputDialog(
            this,
            "Enter Account Number:",
            "Account Details",
            JOptionPane.PLAIN_MESSAGE);

        if (accNo != null && 
            !accNo.trim().isEmpty()) {
            BankAccount acc = bank.findAccount(
                accNo.trim());
            if (acc != null) {
                appendOutput("👤 Account Details");
                appendOutput("Account No : " +
                    acc.getAccountNumber());
                appendOutput("Name       : " +
                    acc.getHolderName());
                appendOutput("Balance    : Rs." +
                    acc.getBalance());
                appendOutput(
                    "----------------------------");
            } else {
                showError("Account not found!");
            }
        }
    }

    private void showAllAccounts() {
        ArrayList<BankAccount> all = 
            bank.getAllAccounts();
        if (all.isEmpty()) {
            appendOutput("❌ No accounts found!");
            appendOutput(
                "----------------------------");
            return;
        }
        appendOutput("📊 All Accounts:");
        for (BankAccount acc : all) {
            appendOutput(
                acc.getAccountNumber() + " | " +
                acc.getHolderName() + " | Rs." +
                acc.getBalance());
        }
        appendOutput(
            "----------------------------");
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(
            outputArea.getDocument().getLength());
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(
            this, msg, "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    private void printWelcome() {
        appendOutput(
            "================================");
        appendOutput(
            "   Welcome to JavaBank System   ");
        appendOutput(
            "================================");
        appendOutput(
            "Select an option from the menu.");
        appendOutput(
            "----------------------------");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> new BankGUI());
    }
}