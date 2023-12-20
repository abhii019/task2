import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private StringBuilder transactionHistory;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new StringBuilder();
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.append("Deposit: +$" + amount + "\n");
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.append("Withdrawal: -$" + amount + "\n");
        } else {
            transactionHistory.append("Withdrawal failed. Insufficient funds.\n");
        }
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }
}

public class ATMApplication {
    private JFrame frame;
    private Account account;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMApplication().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("ATM Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1));

        createLoginPanel();

        frame.setVisible(true);
    }

    private void createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel userIdLabel = new JLabel("User ID:");
        JTextField userIdField = new JTextField(10);

        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField(10);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String pin = new String(pinField.getPassword());

                // Perform authentication (In a real-world scenario, this would involve backend validation)
                if (userId.equals("user123") && pin.equals("1234")) {
                    frame.getContentPane().removeAll();
                    createATMPanel(userId);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
                }
            }
        });

        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(loginButton);

        frame.add(panel);
    }

    private void createATMPanel(String userId) {
        account = new Account(userId, "1234", 1000.0); // Sample account with an initial balance of $1000

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog("Enter deposit amount:");
                double amount = Double.parseDouble(amountString);
                account.deposit(amount);
                JOptionPane.showMessageDialog(null, "Deposit successful. New balance: $" + account.getBalance());
            }
        });

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog("Enter withdrawal amount:");
                double amount = Double.parseDouble(amountString);
                account.withdraw(amount);
                JOptionPane.showMessageDialog(null, "Withdrawal successful. New balance: $" + account.getBalance());
            }
        });

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Current Balance: $" + account.getBalance());
            }
        });

        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, account.getTransactionHistory());
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                createLoginPanel();
                frame.revalidate();
                frame.repaint();
            }
        });

        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(checkBalanceButton);
        panel.add(transactionHistoryButton);
        panel.add(logoutButton);

        frame.add(panel);
    }
}
