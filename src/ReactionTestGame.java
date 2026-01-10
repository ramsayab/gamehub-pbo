import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ReactionTestGame extends BaseGameWindow {
    
    private JPanel panel;
    private JLabel text;
    private long startTime;
    private boolean isGreen = false;
    private Timer waitTimer;

    public ReactionTestGame(Player player) {
        super("Reaction Test", player, 400, 400);
        
        panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setLayout(new BorderLayout());
        
        text = new JLabel("Tunggu layar HIJAU, lalu klik!", SwingConstants.CENTER);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(text);
        
        // Menambahkan listener mouse ke panel
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleClick();
            }
        });
        
        add(panel);
        startSequence();
        setVisible(true);
    }
    
    private void startSequence() {
        int delay = new Random().nextInt(3000) + 1000; // Random 1-4 detik
        waitTimer = new Timer(delay, e -> {
            panel.setBackground(Color.GREEN);
            text.setText("KLIK SEKARANG!");
            isGreen = true;
            startTime = System.currentTimeMillis();
            waitTimer.stop();
        });
        waitTimer.start();
    }
    
    private void handleClick() {
        if (isGreen) {
            long reactionTime = System.currentTimeMillis() - startTime;
            finishGame(10, "Refleks Kamu: " + reactionTime + " ms\nLuar Biasa!");
        } else {
            waitTimer.stop();
            JOptionPane.showMessageDialog(this, "Terlalu Cepat! Tunggu warna hijau.");
            dispose();
        }
    }
}