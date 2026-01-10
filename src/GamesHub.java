import javax.swing.*;
import java.awt.*;

public class GamesHub {
    static Player player = new Player();
    static JLabel scoreLabel;

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Games Hub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 500));
        frame.setLocationRelativeTo(null);

        frame.setLayout(new GridLayout(4, 2, 5, 5));

        JLabel title = new JLabel("GAMES HUB", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        
        scoreLabel = new JLabel("Total Score: 0", SwingConstants.CENTER);
        
        JButton btnAkun = new JButton("1. Daftar Akun");
        JButton btnGuess = new JButton("2. Guessing Number");
        JButton btnSnake = new JButton("3. Snake Game");
        JButton btnAim = new JButton("4. Aim Trainer");
        JButton btnReact = new JButton("5. Reaction Test");
        JButton btnExit = new JButton("0. Keluar");
        
        // 1. Daftar Akun
        btnAkun.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Masukkan ID:");
            if (id != null && !id.trim().isEmpty()) {
                player.setID(id);
                JOptionPane.showMessageDialog(frame, "Selamat Datang, " + id + "!");
            }
        });

        // 2. Guessing Game
        btnGuess.addActionListener(e -> {
            if (cekAkun(frame)) {
                new GuessingGame(player).startGame();
            }
        });
        
        // 3. Snake Game
        btnSnake.addActionListener(e -> {
            if (cekAkun(frame)) {
                new SnakeGame(player).startGame();
            }
        });

        // 4. Aim Trainer
        btnAim.addActionListener(e -> {
            if (cekAkun(frame)) {
                new AimTrainer(player).startGame();
            }
        });

        // 5. Reaction Test
        btnReact.addActionListener(e -> {
            if (cekAkun(frame)) {
                new ReactionTest(player).startGame();
            }
        });

        // 0. Keluar
        btnExit.addActionListener(e -> System.exit(0));

        // Tambahkan komponen ke Frame
        frame.add(title);
        frame.add(scoreLabel);
        frame.add(btnAkun);
        frame.add(btnGuess);
        frame.add(btnSnake);
        frame.add(btnAim);
        frame.add(btnReact);
        frame.add(btnExit);

        // Timer Background untuk update skor di menu utama secara realtime
        Timer uiUpdater = new Timer(1000, e -> {
            scoreLabel.setText("Total Score: " + player.getScore());
            if (player.getID() != null) {
                frame.setTitle("Games Hub - Player: " + player.getID());
            }
        });
        uiUpdater.start();

        frame.setVisible(true);
    }

    // Helper Method: Validasi apakah user sudah login
    static boolean cekAkun(JFrame parent) {
        if (player.getID() == null) {
            JOptionPane.showMessageDialog(parent, "Silahkan buat akun terlebih dahulu!", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}