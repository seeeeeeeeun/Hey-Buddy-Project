import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // JVM 기본 인코딩 설정
        System.setProperty("file.encoding", "UTF-8");  // 또는 "MS949"

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
    }
}
