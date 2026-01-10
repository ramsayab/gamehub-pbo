import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ReactionTest {
    
    private Player player;
    private JFrame frame;
    private JPanel panel;
    private JLabel instructions;
    
    private long startTime;
    private boolean isGreen = false;
    private Timer waitTimer;

    public ReactionTest(Player player) {
        this.player = player;
    }

    public void startGame() {
        isGreen = false;

        frame = new JFrame("Reaction Test");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(Color.RED); // Warna awal Merah
        panel.setLayout(new BorderLayout());

        instructions = new JLabel("Tunggu layar HIJAU, lalu KLIK!", SwingConstants.CENTER);
        instructions.setForeground(Color.WHITE);
        instructions.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(instructions, BorderLayout.CENTER);

        // MATERI SLIDE 226: MouseListener
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleClick();
            }
        });

        frame.add(panel);
        frame.setVisible(true);

        // Mulai timer random (1 sampai 4 detik)
        int randomDelay = new Random().nextInt(3000) + 1000;
        
        waitTimer = new Timer(randomDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahKeHijau();
            }
        });
        waitTimer.setRepeats(false); // Hanya jalan sekali
        waitTimer.start();
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(waitTimer.isRunning()) waitTimer.stop();
            }
        });
    }

    private void ubahKeHijau() {
        panel.setBackground(Color.GREEN);
        instructions.setText("KLIK SEKARANG!!!");
        instructions.setForeground(Color.BLACK);
        startTime = System.currentTimeMillis(); // Catat waktu saat hijau muncul
        isGreen = true;
    }

    private void handleClick() {
        if (isGreen) {
            long reactionTime = System.currentTimeMillis() - startTime;
            JOptionPane.showMessageDialog(frame, "Refleks Kamu: " + reactionTime + " ms\n(+10 Poin)");
            player.addScore(10);
            frame.dispose();
        } else {
            // Logika False Start (Klik sebelum hijau)
            if (waitTimer.isRunning()) {
                waitTimer.stop();
                JOptionPane.showMessageDialog(frame, "Terlalu Cepat! Tunggu sampai hijau.", "Gagal", JOptionPane.WARNING_MESSAGE);
                frame.dispose();
            }
        }
    }
}