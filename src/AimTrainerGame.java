import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AimTrainerGame extends BaseGameWindow {
    
    private int score = 0;
    private int timeLeft = 10;
    private JButton target;
    private JLabel infoLabel;
    private Timer gameTimer;

    public AimTrainerGame(Player player) {
        super("Aim Trainer", player, 600, 400);
        setLayout(null); // Layout bebas
        
        infoLabel = new JLabel("Score: 0 | Waktu: " + timeLeft);
        infoLabel.setBounds(10, 10, 200, 20);
        add(infoLabel);
        
        target = new JButton("O");
        target.setBackground(Color.RED);
        target.setForeground(Color.WHITE);
        target.setFocusable(false);
        // Logika saat target diklik
        target.addActionListener(e -> {
            score++;
            moveTarget();
            updateInfo();
        });
        add(target);
        
        moveTarget();
        
        // Timer hitung mundur detik
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            updateInfo();
            if (timeLeft <= 0) {
                gameTimer.stop();
                finishGame(score * 5, "Waktu Habis!");
            }
        });
        gameTimer.start();
        
        setVisible(true);
    }

    private void moveTarget() {
        Random r = new Random();
        int x = r.nextInt(500);
        int y = r.nextInt(300) + 40; // +40 biar gak ketutup header
        target.setBounds(x, y, 50, 50);
    }
    
    private void updateInfo() {
        infoLabel.setText("Score: " + score + " | Waktu: " + timeLeft);
    }
}