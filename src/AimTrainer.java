import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AimTrainer {
    
    private Player player;
    private int score = 0;
    private int timeLeft = 10; // Waktu permainan (detik)
    private Timer timer;
    private JFrame frame;
    private JButton targetBtn;
    private JLabel infoLabel;

    public AimTrainer(Player player) {
        this.player = player;
    }

    public void startGame() {
        score = 0;
        timeLeft = 10;

        frame = new JFrame("Aim Trainer (Click Fast!)");
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // MATERI: Layout Null (Absolute Positioning)
        frame.setLayout(null);

        infoLabel = new JLabel("Score: 0 | Time: " + timeLeft);
        infoLabel.setBounds(20, 10, 300, 30);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(infoLabel);

        targetBtn = new JButton("O");
        targetBtn.setBackground(Color.RED);
        targetBtn.setForeground(Color.WHITE);
        targetBtn.setFocusable(false);
        // Posisi awal
        targetBtn.setBounds(250, 200, 50, 50);
        frame.add(targetBtn);

        // Logic Klik Target (Materi ActionEvent)
        targetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    score++;
                    pindahTarget();
                    infoLabel.setText("Score: " + score + " | Time: " + timeLeft);
                }
            }
        });

        // Timer Hitung Mundur
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                infoLabel.setText("Score: " + score + " | Time: " + timeLeft);

                if (timeLeft <= 0) {
                    stopGame();
                }
            }
        });
        timer.start();
        
        // Stop timer jika window ditutup manual
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
            }
        });

        frame.setVisible(true);
    }

    private void pindahTarget() {
        Random rng = new Random();
        int x = rng.nextInt(500); // Batas lebar frame
        int y = rng.nextInt(300) + 50; // Batas tinggi frame (+50 biar ga kena header)
        targetBtn.setBounds(x, y, 50, 50);
    }

    private void stopGame() {
        timer.stop();
        targetBtn.setEnabled(false); // Matikan tombol
        JOptionPane.showMessageDialog(frame, "Waktu Habis! Skor Akhir: " + score);
        player.addScore(score);
        frame.dispose();
    }
}