import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class CheckboxPage extends JFrame {
    private JPanel folderPanel;
    private ArrayList<FolderWithCheckboxes> folders;
    private static final String FILE_NAME = "checkboxes.txt";

    public CheckboxPage() {
        setTitle("Checkbox Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        folders = new ArrayList<>();
        folderPanel = new JPanel();
        folderPanel.setLayout(new BoxLayout(folderPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(folderPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton addButton = new JButton("Add Folder");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Folder");

        buttonPanel.add(deleteButton);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                if (!text.isEmpty()) {
                    if (!folders.isEmpty()) {
                        folderPanel.add(new JSeparator()); // 폴더 사이에 선 추가
                    }
                    FolderWithCheckboxes newFolder = new FolderWithCheckboxes(text, CheckboxPage.this);
                    folders.add(newFolder);
                    folderPanel.add(newFolder);
                    folderPanel.revalidate();
                    folderPanel.repaint();
                    inputField.setText("");
                    saveFoldersToFile();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!folders.isEmpty()) {
                    FolderWithCheckboxes lastFolder = folders.remove(folders.size() - 1);
                    folderPanel.remove(lastFolder);
                    if (folderPanel.getComponentCount() > 0) {
                        folderPanel.remove(folderPanel.getComponentCount() - 1); // 마지막 선 제거
                    }
                    folderPanel.revalidate();
                    folderPanel.repaint();
                    saveFoldersToFile();
                }
            }
        });

        loadFoldersFromFile();
    }

    public void saveFoldersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (FolderWithCheckboxes folder : folders) {
                writer.write(folder.getFolderName() + "\n");
                for (JCheckBox checkbox : folder.getCheckboxes()) {
                    writer.write("    " + checkbox.getText() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFoldersFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                FolderWithCheckboxes currentFolder = null;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("    ")) {
                        if (!folders.isEmpty()) {
                            folderPanel.add(new JSeparator());
                        }
                        currentFolder = new FolderWithCheckboxes(line.trim(), this);
                        folders.add(currentFolder);
                        folderPanel.add(currentFolder);
                    } else if (currentFolder != null) {
                        JCheckBox checkbox = new JCheckBox(line.trim());
                        currentFolder.addCheckbox(checkbox);
                    }
                }
                folderPanel.revalidate();
                folderPanel.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CheckboxPage().setVisible(true);
            }
        });
    }
}

class FolderWithCheckboxes extends JPanel {
    private JCheckBox folderCheckbox;
    private JPanel checkboxPanel;
    private ArrayList<JCheckBox> checkboxes;
    private CheckboxPage parentPage;

    public FolderWithCheckboxes(String folderName, CheckboxPage parentPage) {
        this.parentPage = parentPage;
        setLayout(new BorderLayout());
        folderCheckbox = new JCheckBox(folderName);
        checkboxes = new ArrayList<>();

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        JButton addCheckboxButton = new JButton("Add Checkbox");
        addCheckboxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String checkboxText = JOptionPane.showInputDialog("Enter checkbox text:");
                if (checkboxText != null && !checkboxText.isEmpty()) {
                    JCheckBox checkbox = new JCheckBox(checkboxText);
                    checkboxes.add(checkbox);
                    checkboxPanel.add(checkbox);
                    checkboxPanel.revalidate();
                    checkboxPanel.repaint();
                    parentPage.saveFoldersToFile();
                }
            }
        });

        JButton deleteCheckboxButton = new JButton("Delete Checkbox");
        deleteCheckboxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!checkboxes.isEmpty()) {
                    JCheckBox lastCheckbox = checkboxes.remove(checkboxes.size() - 1);
                    checkboxPanel.remove(lastCheckbox);
                    checkboxPanel.revalidate();
                    checkboxPanel.repaint();
                    parentPage.saveFoldersToFile();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addCheckboxButton);
        buttonPanel.add(deleteCheckboxButton);

        add(folderCheckbox, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(checkboxPanel, BorderLayout.SOUTH);
    }

    public String getFolderName() {
        return folderCheckbox.getText();
    }

    public ArrayList<JCheckBox> getCheckboxes() {
        return checkboxes;
    }

    public void addCheckbox(JCheckBox checkbox) {
        checkboxes.add(checkbox);
        checkboxPanel.add(checkbox);
        checkboxPanel.revalidate();
        checkboxPanel.repaint();
    }
}
