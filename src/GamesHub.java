import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GamesHub {
    
    static Player player = new Player();
    static JLabel scoreLabel;


    abstract static class GameFeature {
        abstract void play();
    }
    static void launchGame(GameFeature game, JFrame p) {
        if (player.getID() == null) {
            JOptionPane.showMessageDialog(p, "Daftar akun dulu!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            game.play();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Games Hub - UAS OOP");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(15, 30, 15, 30)); 
        
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel titleLabel = new JLabel(" MENU ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        
        JButton btnAkun = new JButton("1. Daftar Akun");
        JButton btnGuess = new JButton("2. Tebak Angka");
        JButton btnRush = new JButton("3. Number Rush");
        JButton btnAim = new JButton("4. Aim Trainer");
        JButton btnReact = new JButton("5. Reaction Test");
        JButton btnExit = new JButton("Keluar");

        btnAkun.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Masukkan Nama:");
            if (id != null && !id.isEmpty()) {
                player.setID(id);
                frame.setTitle("Player: " + player.getID());
            }
        });

        btnGuess.addActionListener(e -> launchGame(new GuessingGame(), frame));
        btnRush.addActionListener(e ->  launchGame(new NumberRush(), frame));
        btnAim.addActionListener(e ->   launchGame(new AimTrainer(), frame));
        btnReact.addActionListener(e -> launchGame(new ReactionTest(), frame));

        btnExit.addActionListener(e -> {
            System.out.println("memroi dibersihakn");
            System.gc();
            System.exit(0);
        });

        new Timer(1000, e -> scoreLabel.setText("Score: " + player.getScore())).start();

        mainPanel.add(titleLabel); mainPanel.add(scoreLabel);
        mainPanel.add(btnAkun); mainPanel.add(btnGuess);
        mainPanel.add(btnRush); mainPanel.add(btnAim);
        mainPanel.add(btnReact); mainPanel.add(btnExit);

        frame.add(mainPanel);
        frame.setVisible(true);
    }






    static class GuessingGame extends GameFeature {
        @Override
        void play() {
            int target = new Random().nextInt(10) + 1;
            int attempts = 4;
            try {
                while (attempts > 0) {
                    String input = JOptionPane.showInputDialog(null, "Tebak 1-10\nNyawa: " + attempts);
                    if (input == null) break;
                    
                    int guess = Integer.parseInt(input);
                    if (guess == target) {
                        JOptionPane.showMessageDialog(null, "Benar! (+10 Poin)");
                        player.addScore(10);
                        return;
                    } 
                    attempts--;
                    String hint = (guess < target) ? "Kekecilan" : "Kebesaran";
                    JOptionPane.showMessageDialog(null, hint + "! Sisa: " + attempts);
                }
                JOptionPane.showMessageDialog(null, "Gagal! Angka: " + target);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Masukkan Angka!");
            } finally { 
                System.gc(); 
            }
        }
    }


    static class NumberRush extends GameFeature {
        private JFrame frame;
        private ArrayList<Integer> numbers;
        private int currentTarget = 1;

        @Override
        void play() {
            numbers = new ArrayList<>();
            for (int i = 1; i <= 9; i++) numbers.add(i);
            Collections.shuffle(numbers);
            
            currentTarget = 1;
            frame = new JFrame("Urutkan 1 - 9");
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridLayout(3, 3, 5, 5));

            for (int num : numbers) {
                JButton btn = new JButton(String.valueOf(num));
                btn.setFont(new Font("Arial", Font.BOLD, 24));
                
                btn.addActionListener(e -> {
                    int val = Integer.parseInt(btn.getText());
                    if (val == currentTarget) {
                        btn.setEnabled(false); 
                        btn.setBackground(Color.GREEN);
                        currentTarget++;
                        
                        if (currentTarget > 9) {
                            JOptionPane.showMessageDialog(frame, "Selesai! (+15 Poin)");
                            player.addScore(15);
                            frame.dispose();
                            System.gc();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Salah! Cari angka " + currentTarget);
                    }
                });
                frame.add(btn);
            }
            frame.setVisible(true);
        }
    }


    static class AimTrainer extends GameFeature {
        private int score = 0;
        private int time = 10;
        private Timer timer;

        @Override
        void play() {
            score = 0;
            time = 10;

            JFrame frame = new JFrame("Aim Trainer");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);

            JLabel label = new JLabel("Waktu: " + time);
            label.setBounds(20, 10, 100, 20);
            frame.add(label);

            JButton target = new JButton();
            target.setBackground(Color.RED);
            target.setBounds(250, 150, 30, 30);
            target.setFocusable(false);
            
            target.addActionListener(e -> {
                if (time > 0) {
                    score++;
                    int x = new Random().nextInt(540);
                    int y = new Random().nextInt(320) + 30;
                    target.setLocation(x, y);
                }
            });
            frame.add(target);
            frame.setVisible(true);

            timer = new Timer(1000, e -> {
                time--;
                label.setText("Waktu: " + time);
                
                if (time <= 0) {
                    timer.stop();
                    target.setEnabled(false);
                    JOptionPane.showMessageDialog(frame, "Waktu Habis! Skor: " + score);
                    player.addScore(score);
                    frame.dispose();
                    System.gc();
                }
            });
            timer.start();
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { 
                    timer.stop(); 
                    System.gc(); 
                }
            });
        }
    }


    static class ReactionTest extends GameFeature {
        private long start; 
        private boolean green = false; 
        private Timer w;

        @Override
        void play() {
            JFrame f = new JFrame("Reaction"); 
            f.setSize(400, 400); 
            f.setLocationRelativeTo(null);

            JPanel p = new JPanel();
            p.setBackground(Color.RED);
            p.add(new JLabel("KLIK SAAT HIJAU!"));
            
            p.addMouseListener(new MouseAdapter() { 
                public void mousePressed(MouseEvent e) {
                    if (green) { 
                        long r = System.currentTimeMillis() - start; 
                        JOptionPane.showMessageDialog(f, r + " ms! (+10)"); 
                        player.addScore(10); 
                        f.dispose(); 
                    } else { 
                        if(w.isRunning()) w.stop(); 
                        JOptionPane.showMessageDialog(f, "Kecepatan!"); 
                        f.dispose(); 
                    }
                    System.gc();
                }
            });

            f.add(p); 
            f.setVisible(true);

            w = new Timer(new Random().nextInt(3000)+1000, e -> { 
                p.setBackground(Color.GREEN); 
                start = System.currentTimeMillis(); 
                green = true; 
                w.stop(); 
            }); 
            w.start();
            
            f.addWindowListener(new WindowAdapter() { 
                public void windowClosing(WindowEvent e) { 
                    if(w.isRunning()) w.stop(); 
                    System.gc(); 
                }
            });
        }
    }
}