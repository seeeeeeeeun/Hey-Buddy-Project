import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ProgressPage extends JFrame {
    public ProgressPage() {
        setTitle("Progress Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Example folder path
        String folderPath = "your_folder_path";  // 여기에 폴더 경로를 입력하세요
        File folder = new File(folderPath);

        // 파일 목록을 불러옴
        File[] files = folder.listFiles();
        if (files == null) {
            files = new File[0];
        }

        // 파일 이름과 진도율을 저장할 배열
        String[] todoItems = new String[files.length];
        int[] progressValues = new int[files.length];

        // 파일 목록을 기반으로 데이터 설정
        for (int i = 0; i < files.length; i++) {
            todoItems[i] = files[i].getName();
            progressValues[i] = calculateProgress(files[i]);  // 파일 기반의 진도율 계산 함수 호출
        }

        // 진도율 표시를 위한 패널 생성
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new GridLayout(todoItems.length, 1, 10, 10));

        // 각 파일에 대한 진도율 바 생성
        for (int i = 0; i < todoItems.length; i++) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            JLabel itemLabel = new JLabel(todoItems[i]);
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(progressValues[i]);
            progressBar.setStringPainted(true);

            itemPanel.add(itemLabel, BorderLayout.WEST);
            itemPanel.add(progressBar, BorderLayout.CENTER);

            progressPanel.add(itemPanel);
        }

        add(progressPanel, BorderLayout.CENTER);
    }

    // 진도율을 계산하는 메소드
    private int calculateProgress(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            // 예시로 파일의 줄 수를 기준으로 진도율을 계산 (임의의 기준 사용)
            if (lines.isEmpty()) {
                return 0;
            }
            int totalLines = lines.size();
            int completedLines = (int) lines.stream().filter(line -> line.trim().length() > 0).count();
            return (int) ((completedLines / (double) totalLines) * 100);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProgressPage().setVisible(true);
            }
        });
    }
}
