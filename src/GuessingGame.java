import javax.swing.*;
import java.awt.*;
import java.util.Random;

// INHERITANCE: Mewarisi BaseGameWindow
public class GuessingGame extends BaseGameWindow {
    
    private int targetNumber;
    private int attempts = 3;
    private JTextField inputField;
    private JLabel infoLabel;

    public GuessingGame(Player player) {
        // Memanggil constructor induk (BaseGameWindow)
        super("Guessing Number", player, 300, 200);
        
        targetNumber = new Random().nextInt(10) + 1;
        initUI();
        setVisible(true);
    }

    private void initUI() {
        setLayout(new FlowLayout());

        infoLabel = new JLabel("Tebak Angka 1-10 (Nyawa: " + attempts + ")");
        add(infoLabel);

        inputField = new JTextField(10);
        add(inputField);

        JButton guessBtn = new JButton("Tebak");
        // POLYMORPHISM: Penerapan Event Listener (Interface)
        guessBtn.addActionListener(e -> checkGuess());
        add(guessBtn);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(inputField.getText());
            
            if (guess == targetNumber) {
                finishGame(10, "Selamat! Tebakan Benar.");
            } else {
                attempts--;
                if (attempts <= 0) {
                    finishGame(0, "Game Over! Angkanya adalah " + targetNumber);
                } else {
                    String hint = (guess > targetNumber) ? "Terlalu Besar!" : "Terlalu Kecil!";
                    infoLabel.setText(hint + " (Sisa Nyawa: " + attempts + ")");
                    inputField.setText("");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!");
        }
    }
}