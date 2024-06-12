import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    public MainPage() {
        setTitle("Main Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        // Create buttons
        JButton dDayButton = new JButton("디데이");
        JButton calendarButton = new JButton("Calendar");
        JButton checkboxButton = new JButton("Checkbox");
        JButton progressButton = new JButton("Progress");

        // Set button font
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        dDayButton.setFont(buttonFont);
        calendarButton.setFont(buttonFont);
        checkboxButton.setFont(buttonFont);
        progressButton.setFont(buttonFont);

        // Add buttons to panel
        buttonPanel.add(dDayButton);
        buttonPanel.add(calendarButton);
        buttonPanel.add(checkboxButton);
        buttonPanel.add(progressButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        dDayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate to D-Day page
                new DDayPage().setVisible(true);
            }
        });

        calendarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate to Calendar page
                new CalendarFrame().setVisible(true);
            }
        });

        checkboxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate to Checkbox page
                new CheckboxPage().setVisible(true);
            }
        });

        progressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate to Progress page
                new ProgressPage().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }
}
