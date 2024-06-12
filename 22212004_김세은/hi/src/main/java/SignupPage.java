import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class SignupPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signupButton;

    public SignupPage() {
        setTitle("Sign Up Page");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        signupButton = new JButton("Sign Up");
        panel.add(signupButton);

        add(panel);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // 입력 값 검증
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
                    return;
                }

                if (password.length() < 6) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long.");
                    return;
                }

                // 회원가입 로직
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "User registered successfully!");
                    new LoginPage().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.");
                }
            }
        });
    }

    private boolean registerUser(String username, String password) {
        Set<String> users = new HashSet<>();

        // 기존 사용자 정보 로드
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line.split(":")[0]);
            }
        } catch (IOException e) {
            // 파일이 없으면 무시 (첫 사용자 등록)
        }

        // 중복 확인
        if (users.contains(username)) {
            return false;
        }

        // 사용자 정보 저장
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignupPage().setVisible(true);
            }
        });
    }
}
