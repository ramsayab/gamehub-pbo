import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessingGame {
    
    private Player player;
    private int targetNumber;
    private int attempts;
    private JFrame frame;

    public GuessingGame(Player player) {
        this.player = player;
    }

    public void startGame() {
        // Reset Logic
        targetNumber = new Random().nextInt(10) + 1;
        attempts = 3;

        // MATERI PPT: Membuat Instance JFrame (Komposisi)
        frame = new JFrame("Guessing Number");
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Layout Flow (Materi Slide 169)
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel infoLabel = new JLabel("Tebak Angka 1-10 (Nyawa: " + attempts + ")");
        JTextField inputField = new JTextField(10);
        JButton btnTebak = new JButton("Tebak!");

        frame.add(infoLabel);
        frame.add(inputField);
        frame.add(btnTebak);

        // Action Listener (Materi Slide 198)
        btnTebak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int tebakan = Integer.parseInt(inputField.getText());
                    
                    if (tebakan == targetNumber) {
                        JOptionPane.showMessageDialog(frame, "Benar! Angka: " + targetNumber + "\n(+10 Poin)");
                        player.addScore(10);
                        frame.dispose(); // Tutup game
                    } else {
                        attempts--;
                        if (attempts <= 0) {
                            JOptionPane.showMessageDialog(frame, "Game Over! Angkanya adalah: " + targetNumber);
                            frame.dispose();
                        } else {
                            String hint = (tebakan < targetNumber) ? "Terlalu Kecil" : "Terlalu Besar";
                            infoLabel.setText(hint + " (Sisa Nyawa: " + attempts + ")");
                            inputField.setText("");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Masukkan angka yang valid!");
                }
            }
        });

        frame.setVisible(true);
    }
}