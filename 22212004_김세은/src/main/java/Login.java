import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class Login extends JFrame {

  private JTextField usernameField;
  private JPasswordField passwordField;
  private JButton loginButton;
  private JButton signupButton;

  public Login() {
    setTitle("로그인 페이지");
    setSize(300, 150);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 2));

    panel.add(new JLabel("아이디:"));
    usernameField = new JTextField();
    panel.add(usernameField);

    panel.add(new JLabel("비밀번호:"));
    passwordField = new JPasswordField();
    panel.add(passwordField);

    loginButton = new JButton("로그인");
    panel.add(loginButton);

    signupButton = new JButton("회원가입");
    panel.add(signupButton);

    add(panel);

    loginButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String username = usernameField.getText();
          String password = new String(passwordField.getPassword());

          if (authenticateUser(username, password)) {
            new MainPage().setVisible(true);
            dispose();
          } else {
            JOptionPane.showMessageDialog(null, "잘못된 정보입니다.");
          }
        }
      }
    );

    signupButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new Join().setVisible(true);
          dispose();
        }
      }
    );
  }

  private boolean authenticateUser(String username, String password) {
    try (
      BufferedReader reader = new BufferedReader(new FileReader("users.txt"))
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(":");
        if (parts.length == 3) {
          String storedUsername = parts[1];
          String storedPassword = parts[2];
          if (
            storedUsername.equals(username) && storedPassword.equals(password)
          ) {
            return true;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          new Login().setVisible(true);
        }
      }
    );
  }
}
