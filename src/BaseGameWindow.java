import javax.swing.*;

// Abstract Class: Tidak bisa dibuat objeknya langsung, harus diturunkan.
// INHERITANCE: Mewarisi sifat JFrame
public abstract class BaseGameWindow extends JFrame {
    
    // Protected agar bisa diakses oleh class anak (Subclasses)
    protected Player player;

    public BaseGameWindow(String title, Player player, int width, int height) {
        this.player = player;
        
        // Setup dasar yang sama untuk semua game
        setTitle(title + " - Player: " + player.getID());
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Hanya tutup jendela game, bukan aplikasi
        setLocationRelativeTo(null); // Posisi tengah layar
        setResizable(false);
    }
    
    // Helper method untuk menambah skor dan menampilkan pesan
    protected void finishGame(int scoreEarned, String message) {
        player.addScore(scoreEarned);
        JOptionPane.showMessageDialog(this, message + "\nSkor didapat: " + scoreEarned);
        this.dispose(); // Tutup jendela game
    }
}