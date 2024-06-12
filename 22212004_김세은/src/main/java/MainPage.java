import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPage extends JFrame {

  public MainPage() {
    setTitle("Main Page");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3, 1, 0, 20)); // 3 rows, no horizontal gap, 20px vertical gap

    JButton dDayButton = new JButton("D-day");
    JButton calendarButton = new JButton("Calendar");
    JButton checkboxButton = new JButton("Subject");

    Font buttonFont = new Font("Arial", Font.BOLD, 24);
    dDayButton.setFont(buttonFont);
    calendarButton.setFont(buttonFont);
    checkboxButton.setFont(buttonFont);

    buttonPanel.add(dDayButton);
    buttonPanel.add(calendarButton);
    buttonPanel.add(checkboxButton);

    add(buttonPanel, BorderLayout.CENTER);

    dDayButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new dDay().setVisible(true);
        }
      }
    );

    calendarButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new YearCalendar().setVisible(true);
        }
      }
    );

    checkboxButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new Subject().setVisible(true);
        }
      }
    );
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          new MainPage().setVisible(true);
        }
      }
    );
  }
}
