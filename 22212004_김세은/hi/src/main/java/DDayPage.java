    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.io.*;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.concurrent.TimeUnit;

    public class DDayPage extends JFrame {
        private DefaultListModel<JPanel> dDayListModel;
        private static final String FILE_NAME = "ddays.txt";

        public DDayPage() {
            setTitle("D-Day Page");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            dDayListModel = new DefaultListModel<>();
            JList<JPanel> dDayList = new JList<>(dDayListModel);
            dDayList.setCellRenderer(new PanelCellRenderer());

            add(new JScrollPane(dDayList), BorderLayout.CENTER);

            JButton addButton = new JButton("Add D-Day");
            add(addButton, BorderLayout.SOUTH);

            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog("Enter D-Day name:");
                    String dateStr = JOptionPane.showInputDialog("Enter D-Day date (yyyy-MM-dd):");

                    if (name != null && dateStr != null && !name.isEmpty() && !dateStr.isEmpty()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = sdf.parse(dateStr);
                            long daysDiff = calculateDDay(date);

                            String dDay = (daysDiff >= 0 ? "D-" : "D+") + Math.abs(daysDiff);

                            JPanel dDayPanel = createDDayPanel(name, dateStr, dDay);
                            dDayListModel.addElement(dDayPanel);
                            saveDDay(name, dateStr, dDay);
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(DDayPage.this, "Invalid date format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            loadDDays();
        }

        private long calculateDDay(Date targetDate) {
            Date currentDate = new Date();
            long diffInMillies = targetDate.getTime() - currentDate.getTime();
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1; // Add 1 to the calculated difference
        }

        private JPanel createDDayPanel(String name, String date, String dDay) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            panel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel dateLabel = new JLabel(date);
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel dDayLabel = new JLabel(dDay);
            dDayLabel.setFont(new Font("Arial", Font.BOLD, 24));
            dDayLabel.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(nameLabel, BorderLayout.NORTH);
            panel.add(dateLabel, BorderLayout.CENTER);
            panel.add(dDayLabel, BorderLayout.SOUTH);

            return panel;
        }

        private class PanelCellRenderer implements ListCellRenderer<JPanel> {
            @Override
            public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
                return value;
            }
        }

        private void saveDDay(String name, String date, String dDay) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write(name + "," + date + "," + dDay);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void loadDDays() {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = sdf.parse(parts[1]);
                                long daysDiff = calculateDDay(date);
                                String dDay = (daysDiff >= 0 ? "D-" : "D+") + Math.abs(daysDiff);

                                JPanel dDayPanel = createDDayPanel(parts[0], parts[1], dDay);
                                dDayListModel.addElement(dDayPanel);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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
                    new DDayPage().setVisible(true);
                }
            });
        }
    }
