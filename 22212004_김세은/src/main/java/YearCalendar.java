import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class YearCalendar extends JFrame {

  private JPanel yearPanelContainer;
  private DefaultListModel<String> eventListModel;
  private Map<String, String> events;

  private int currentYear;
  private int currentMonth;

  public YearCalendar() {
    setTitle("Calendar");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    events = new TreeMap<>();
    loadEvents();

    JPanel mainPanel = new JPanel(new BorderLayout());
    add(mainPanel, BorderLayout.CENTER);

    JPanel controlPanel = new JPanel();
    JButton previousButton = new JButton("<<");
    JButton nextButton = new JButton(">>");
    JLabel monthLabel = new JLabel();

    previousButton.addActionListener(e -> showPreviousMonth());
    nextButton.addActionListener(e -> showNextMonth());

    controlPanel.add(previousButton);
    controlPanel.add(monthLabel);
    controlPanel.add(nextButton);

    mainPanel.add(controlPanel, BorderLayout.NORTH);

    yearPanelContainer = new JPanel(new BorderLayout());
    mainPanel.add(new JScrollPane(yearPanelContainer), BorderLayout.CENTER);

    eventListModel = new DefaultListModel<>();
    JList<String> eventList = new JList<>(eventListModel);
    eventList.addMouseListener(
      new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
          if (evt.getClickCount() == 2) {
            int index = eventList.locationToIndex(evt.getPoint());
            String selectedItem = eventListModel.getElementAt(index);
            String[] parts = selectedItem.split(": ");
            if (parts.length == 2) {
              String dateKey = parts[0];
              events.remove(dateKey);
              saveEvents();
              updateEventList();
            }
          }
        }
      }
    );
    mainPanel.add(new JScrollPane(eventList), BorderLayout.SOUTH);

    updateEventList();

    Calendar currentCalendar = Calendar.getInstance();
    currentYear = currentCalendar.get(Calendar.YEAR);
    currentMonth = currentCalendar.get(Calendar.MONTH);

    showCurrentMonth();

    pack();
  }

  private void showPreviousMonth() {
    currentMonth--;
    if (currentMonth < Calendar.JANUARY) {
      currentMonth = Calendar.DECEMBER;
      currentYear--;
    }
    showCurrentMonth();
  }

  private void showNextMonth() {
    currentMonth++;
    if (currentMonth > Calendar.DECEMBER) {
      currentMonth = Calendar.JANUARY;
      currentYear++;
    }
    showCurrentMonth();
  }

  private void showCurrentMonth() {
    yearPanelContainer.removeAll();
    yearPanelContainer.add(
      createMonthPanel(currentYear, currentMonth),
      BorderLayout.CENTER
    );
    yearPanelContainer.revalidate();

    String monthName = new GregorianCalendar(currentYear, currentMonth, 1)
      .getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    setTitle(monthName + " " + currentYear);

    updateEventList();
  }

  private JPanel createMonthPanel(int year, int month) {
    Calendar calendar = new GregorianCalendar(year, month, 1);
    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    String monthName = calendar.getDisplayName(
      Calendar.MONTH,
      Calendar.LONG,
      Locale.getDefault()
    );

    JPanel monthPanel = new JPanel(new BorderLayout());
    monthPanel.setBorder(BorderFactory.createTitledBorder(monthName));

    JPanel calendarPanel = new JPanel(new GridLayout(0, 7));
    monthPanel.add(calendarPanel, BorderLayout.CENTER);

    for (int i = 1; i <= daysInMonth; i++) {
      String day = String.valueOf(i);
      JLabel dayLabel = new JLabel(day);
      dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
      dayLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
      dayLabel.setToolTipText(getEventForDate(year, month, i));
      dayLabel.addMouseListener(
        new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
              String event = JOptionPane.showInputDialog(
                monthName + " " + day + "일의 일정 이름을 입력하세요. :"
              );
              if (event != null && !event.isEmpty()) {
                events.put(
                  getDateKey(year, month, Integer.parseInt(day)),
                  event
                );
                saveEvents();
                dayLabel.setToolTipText(event);
                updateEventList();
              }
            }
          }
        }
      );
      calendarPanel.add(dayLabel);
    }

    return monthPanel;
  }

  private String getEventForDate(int year, int month, int day) {
    return events.getOrDefault(getDateKey(year, month, day), "");
  }

  private String getDateKey(int year, int month, int day) {
    return String.format("%04d-%02d-%02d", year, month + 1, day);
  }

  private void updateEventList() {
    eventListModel.clear();
    for (Map.Entry<String, String> entry : events.entrySet()) {
      eventListModel.addElement(entry.getKey() + ": " + entry.getValue());
    }
  }

  private void saveEvents() {
    try (
      BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt"))
    ) {
      for (Map.Entry<String, String> entry : events.entrySet()) {
        writer.write(entry.getKey() + "=" + entry.getValue());
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadEvents() {
    File file = new File("events.txt");
    if (file.exists()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] parts = line.split("=", 2);
          if (parts.length == 2) {
            events.put(parts[0], parts[1]);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          new YearCalendar().setVisible(true);
        }
      }
    );
  }
}
