import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class Subject extends JFrame {

  private JPanel folderPanel;
  private ArrayList<FolderWithCheckboxes> folders;
  private static final String FILE_NAME = "checkboxes.txt";

  public Subject() {
    setTitle("Subject");
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
    JButton addButton = new JButton("Add Subject");
    inputPanel.add(inputField, BorderLayout.CENTER);
    inputPanel.add(addButton, BorderLayout.EAST);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton deleteButton = new JButton("Delete Subject");
    buttonPanel.add(deleteButton);

    add(inputPanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);

    addButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String text = inputField.getText();
          if (!text.isEmpty()) {
            if (!folders.isEmpty()) {
              folderPanel.add(new JSeparator());
            }
            FolderWithCheckboxes newFolder = new FolderWithCheckboxes(
              text,
              Subject.this
            );
            folders.add(newFolder);
            folderPanel.add(newFolder);
            folderPanel.revalidate();
            folderPanel.repaint();
            inputField.setText("");
            saveFoldersToFile();
          }
        }
      }
    );

    deleteButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (!folders.isEmpty()) {
            FolderWithCheckboxes lastFolder = folders.remove(
              folders.size() - 1
            );
            folderPanel.remove(lastFolder);
            if (folderPanel.getComponentCount() > 0) {
              folderPanel.remove(folderPanel.getComponentCount() - 1);
            }
            folderPanel.revalidate();
            folderPanel.repaint();
            saveFoldersToFile();
          }
        }
      }
    );

    loadFoldersFromFile();
  }

  public void saveFoldersToFile() {
    try (
      BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))
    ) {
      for (FolderWithCheckboxes folder : folders) {
        writer.write(folder.getFolderName() + "\n");
        for (String checkbox : folder.getCheckboxes()) {
          writer.write("    " + checkbox + "\n");
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
          if (!line.trim().isEmpty()) {
            if (line.startsWith("    ")) {
              if (currentFolder != null) {
                currentFolder.addCheckbox(line.trim());
              }
            } else {
              if (!folders.isEmpty()) {
                folderPanel.add(new JSeparator());
              }
              currentFolder = new FolderWithCheckboxes(line.trim(), this);
              folders.add(currentFolder);
              folderPanel.add(currentFolder);
            }
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
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          new Subject().setVisible(true);
        }
      }
    );
  }
}

class FolderWithCheckboxes extends JPanel {

  private JLabel folderLabel;
  private JPanel checkboxPanel;
  private ArrayList<String> checkboxes;

  public FolderWithCheckboxes(String folderName, Subject parentPage) {
    setLayout(new BorderLayout());
    folderLabel = new JLabel(folderName);
    checkboxes = new ArrayList<>();

    checkboxPanel = new JPanel();
    checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

    JButton addCheckboxButton = new JButton("Add to-do");
    addCheckboxButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String checkboxText = JOptionPane.showInputDialog(
            "Enter to-do name:"
          );
          if (checkboxText != null && !checkboxText.isEmpty()) {
            addCheckbox(checkboxText);
            parentPage.saveFoldersToFile();
          }
        }
      }
    );

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(addCheckboxButton);

    add(folderLabel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.CENTER);
    add(checkboxPanel, BorderLayout.SOUTH);
  }

  public void addCheckbox(String checkboxText) {
    checkboxes.add(checkboxText);
    JCheckBox checkbox = new JCheckBox(checkboxText);
    JPanel checkboxRow = new JPanel(new BorderLayout());
    checkboxRow.add(checkbox, BorderLayout.WEST);
    checkboxPanel.add(checkboxRow);
    JButton deleteButton = new JButton("Delete");
    deleteButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          checkboxes.remove(checkboxText);
          checkboxPanel.remove(checkboxRow);
          checkboxPanel.revalidate();
          checkboxPanel.repaint();
          (
            (Subject) SwingUtilities.getWindowAncestor(
              FolderWithCheckboxes.this
            )
          ).saveFoldersToFile();
        }
      }
    );
    checkboxRow.add(deleteButton, BorderLayout.EAST);
    checkboxPanel.revalidate();
    checkboxPanel.repaint();
  }

  public String getFolderName() {
    return folderLabel.getText();
  }

  public ArrayList<String> getCheckboxes() {
    return checkboxes;
  }
}
