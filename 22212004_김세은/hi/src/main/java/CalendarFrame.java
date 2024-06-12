import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarFrame extends JFrame {
    private JTable calendarTable;
    private DefaultListModel<String> eventListModel;
    private Map<String, String> events;

    public CalendarFrame() {
        setTitle("Calendar");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        events = new HashMap<>();
        loadEvents();

        Calendar calendar = new GregorianCalendar();
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[][] dates = new String[6][7];

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = firstDayOfWeek, day = 1; day <= daysInMonth; i++) {
            dates[i / 7][i % 7] = String.valueOf(day++);
        }

        calendarTable = new JTable(dates, days);
        add(new JScrollPane(calendarTable), BorderLayout.CENTER);

        eventListModel = new DefaultListModel<>();
        JList<String> eventList = new JList<>(eventListModel);
        add(new JScrollPane(eventList), BorderLayout.SOUTH);

        updateEventList();

        calendarTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = calendarTable.rowAtPoint(e.getPoint());
                int col = calendarTable.columnAtPoint(e.getPoint());
                String day = (String) calendarTable.getValueAt(row, col);
                if (day != null) {
                    String event = JOptionPane.showInputDialog("Enter event for " + day + ":");
                    if (event != null && !event.isEmpty()) {
                        events.put(day, event);
                        saveEvents();
                        updateEventList();
                    }
                }
            }
        });
    }

    private void updateEventList() {
        eventListModel.clear();
        for (Map.Entry<String, String> entry : events.entrySet()) {
            eventListModel.addElement(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void saveEvents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt"))) {
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalendarFrame().setVisible(true);
            }
        });
    }
}
