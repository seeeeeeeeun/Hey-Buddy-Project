import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    System.setProperty("file.encoding", "UTF-8");

    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          new Login().setVisible(true);
        }
      }
    );
  }
}
