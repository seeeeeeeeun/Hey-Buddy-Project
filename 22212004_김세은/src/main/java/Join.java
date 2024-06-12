import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

public class Join extends JFrame {

  private JTextField nameField; // 이름 입력 필드
  private JTextField usernameField; // 아이디 입력 필드
  private JPasswordField passwordField; // 비밀번호 입력 필드
  private JPasswordField confirmPasswordField; // 비밀번호 확인 입력 필드
  private JButton signupButton;

  public Join() {
    setTitle("회원가입 페이지");
    setSize(300, 230); // 높이를 늘렸습니다.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5, 2)); // 그리드 레이아웃을 변경했습니다.

    panel.add(new JLabel("이름:")); // 이름 레이블
    nameField = new JTextField();
    panel.add(nameField);

    panel.add(new JLabel("아이디:")); // 아이디 레이블
    usernameField = new JTextField();
    panel.add(usernameField);

    panel.add(new JLabel("비밀번호:")); // 비밀번호 레이블
    passwordField = new JPasswordField();
    panel.add(passwordField);

    panel.add(new JLabel("비밀번호 확인:")); // 비밀번호 확인 레이블
    confirmPasswordField = new JPasswordField();
    panel.add(confirmPasswordField);

    signupButton = new JButton("가입하기");
    panel.add(signupButton);

    add(panel);

    signupButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String name = nameField.getText(); // 이름
          String username = usernameField.getText(); // 아이디
          String password = new String(passwordField.getPassword()); // 비밀번호
          String confirmPassword = new String(
            confirmPasswordField.getPassword()
          ); // 확인용 비밀번호

          // 입력 값 검증
          if (
            name.isEmpty() ||
            username.isEmpty() ||
            password.isEmpty() ||
            confirmPassword.isEmpty()
          ) {
            JOptionPane.showMessageDialog(
              null,
              "이름, 아이디, 비밀번호, 비밀번호 확인은 필수 입력 사항입니다."
            );
            return;
          }

          if (password.length() < 6) {
            JOptionPane.showMessageDialog(
              null,
              "비밀번호는 최소 6자 이상이어야 합니다."
            );
            return;
          }

          if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
              null,
              "비밀번호가 일치하지 않습니다."
            );
            return;
          }

          // 회원가입 로직
          if (registerUser(name, username, password)) {
            JOptionPane.showMessageDialog(null, "회원가입을 환영합니다!");
            new Login().setVisible(true);
            dispose();
          } else {
            JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.");
          }
        }
      }
    );
  }

  private boolean registerUser(String name, String username, String password) {
    Set<String> users = new HashSet<>();

    try (
      BufferedReader reader = new BufferedReader(new FileReader("users.txt"))
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        users.add(line.split(":")[1]);
      }
    } catch (IOException e) {}

    if (users.contains(username)) {
      return false;
    }

    try (
      BufferedWriter writer = new BufferedWriter(
        new FileWriter("users.txt", true)
      )
    ) {
      writer.write(name + ":" + username + ":" + password);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          new Join().setVisible(true);
        }
      }
    );
  }
}
