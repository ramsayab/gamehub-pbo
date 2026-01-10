import javax.swing.*;
import java.awt.*;

public class GamesHub extends JFrame {
    
    private Player player;

    public GamesHub() {
        player = new Player(); // Inisialisasi Data Pemain
        
        setTitle("Games Hub - UAS OOP");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initMenu();
    }

    private void initMenu() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("=== MAIN MENU ===", SwingConstants.CENTER);
        JLabel lblScore = new JLabel("Total Score: 0", SwingConstants.CENTER);
        
        JButton btnAkun = new JButton("1. Daftar Akun");
        JButton btnGuess = new JButton("2. Guessing Number");
        JButton btnSnake = new JButton("3. Snake Game");
        JButton btnAim   = new JButton("4. Aim Trainer");
        JButton btnReact = new JButton("5. Reaction Test");

        // Event Listeners
        btnAkun.addActionListener(e -> {
            String nama = JOptionPane.showInputDialog(this, "Masukkan Nama:");
            if (nama != null && !nama.isEmpty()) {
                player.setID(nama);
                JOptionPane.showMessageDialog(this, "Selamat datang, " + nama);
            }
        });

        // Logika tombol: Cek dulu apakah sudah punya akun
        btnGuess.addActionListener(e -> openGame(new GuessingGame(player)));
        btnSnake.addActionListener(e -> openGame(new SnakeGame(player)));
        btnAim.addActionListener(e -> openGame(new AimTrainerGame(player)));
        btnReact.addActionListener(e -> openGame(new ReactionTestGame(player)));

        // Timer refresh score di menu utama
        new Timer(1000, e -> lblScore.setText("Total Score: " + player.getScore())).start();

        panel.add(lblTitle);
        panel.add(lblScore);
        panel.add(btnAkun);
        panel.add(btnGuess);
        panel.add(btnSnake);
        panel.add(btnAim);
        panel.add(btnReact);

        add(panel);
    }
    
    // Method helper untuk membuka game
    // POLYMORPHISM: Parameter 'game' menerima semua subclass BaseGameWindow
    private void openGame(BaseGameWindow game) {
        if (player.getID() == null) {
            game.dispose(); // Tutup game yg terlanjur dibuat
            JOptionPane.showMessageDialog(this, "Daftar akun dulu!", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            // Game otomatis visible karena constructor BaseGameWindow
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GamesHub().setVisible(true));
    }
}