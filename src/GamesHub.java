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

    private static ImageIcon loadIcon(String path, int height) {
        ImageIcon original = new ImageIcon(path);
        if (original.getIconWidth() == -1) {
            return null;
        }
        Image img = original.getImage();
        Image scaled = img.getScaledInstance(height, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private static JButton createMenuButton(String text, String icon) {
        Color normal = new Color(52, 152, 219);
        Color hover  = new Color(41, 128, 185);

        JButton b = new JButton();

        b.setLayout(new BorderLayout());

        JLabel lblText = new JLabel(text);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblText.setForeground(Color.WHITE);
        lblText.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel lblIcon = new JLabel(loadIcon("assets/" + icon, 28));
        lblIcon.setBorder(new EmptyBorder(0, 0, 0, 10));

        b.add(lblText, BorderLayout.WEST);
        b.add(lblIcon, BorderLayout.EAST);

        b.setBackground(normal);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        b.setBorder(new EmptyBorder(10, 10, 10, 10)); 

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(normal);
            }
        });

        return b;
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Games Hub - UAS OOP");
        frame.setSize(520, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                        0, 0, new Color(44, 62, 80),
                        0, getHeight(), new Color(52, 73, 94)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setLayout(new BorderLayout(15, 15));
        root.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);

        JLabel title = new JLabel("GAMES HUB");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Object Oriented Programming Project");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(200, 200, 200));

        header.add(title);
        header.add(subtitle);

        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 12, 12));
        menuPanel.setOpaque(false);

        JButton btnAkun   = createMenuButton("Daftar Akun", "user.png");
        JButton btnGuess  = createMenuButton("Tebak Angka", "guess.png");
        JButton btnRush   = createMenuButton("Number Rush", "rush.png");
        JButton btnAim    = createMenuButton("Aim Trainer", "aim.png");
        JButton btnReact  = createMenuButton("Reaction Test", "react.png");
        JButton btnExit   = createMenuButton("Keluar", "exit.png");

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel hint = new JLabel("Ready to play");
        hint.setForeground(new Color(180, 180, 180));

        footer.add(scoreLabel, BorderLayout.WEST);
        footer.add(hint, BorderLayout.EAST);

        btnAkun.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Masukkan Nama:");
            if (id != null && !id.isEmpty()) {
                player.setID(id);
                frame.setTitle("Player: " + player.getID());
            }
        });

        btnGuess.addActionListener(e -> launchGame(new GuessingGame(), frame));
        btnRush.addActionListener(e -> launchGame(new NumberRush(), frame));
        btnAim.addActionListener(e -> launchGame(new AimTrainer(), frame));
        btnReact.addActionListener(e -> launchGame(new ReactionTest(), frame));

        btnExit.addActionListener(e -> {
            System.gc();
            System.exit(0);
        });

        new Timer(1000, e -> scoreLabel.setText("Score: " + player.getScore())).start();

        menuPanel.add(btnAkun);
        menuPanel.add(btnGuess);
        menuPanel.add(btnRush);
        menuPanel.add(btnAim);
        menuPanel.add(btnReact);
        menuPanel.add(btnExit);

        root.add(header, BorderLayout.NORTH);
        root.add(menuPanel, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);

        frame.setContentPane(root);
        frame.setVisible(true);

    }


static class GuessingGame extends GameFeature {
        @Override
        void play() {
            UIManager.put("OptionPane.background", new Color(44, 62, 80));
            UIManager.put("Panel.background", new Color(44, 62, 80));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);

            int target = new Random().nextInt(10) + 1, attempts = 4;
            try {
                while (attempts > 0) {
                    JPanel p = new JPanel(new BorderLayout());
                    JTextField txt = new JTextField();
                    txt.setFont(new Font("Segoe UI", Font.BOLD, 24));
                    txt.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    p.add(new JLabel("<html><h2 style='color:#3498db'>Tebak 1-10</h2>Sisa Nyawa: " + attempts + "</html>", SwingConstants.CENTER), BorderLayout.NORTH);
                    p.add(txt, BorderLayout.CENTER);

                    if (JOptionPane.showConfirmDialog(null, p, "Game", JOptionPane.OK_CANCEL_OPTION, -1) != 0) break;

                    int guess = Integer.parseInt(txt.getText());
                    if (guess == target) {
                        JOptionPane.showMessageDialog(null, "🏆 BENAR! (+10 Poin)");
                        player.addScore(10); return;
                    }
                    JOptionPane.showMessageDialog(null, (guess < target ? "📈 Kekecilan" : "📉 Kebesaran") + "! Sisa: " + (--attempts));
                }
                JOptionPane.showMessageDialog(null, "💀 Kalah! Angka: " + target);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "⚠️ Masukkan Angka!");
            } finally { System.gc(); }
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
        Timer gameTimer, moveTimer;
        int score, time;

        @Override
        void play() {
            score = 0; time = 15;
            JFrame f = new JFrame("Aim Trainer");
            f.setSize(600, 450);
            f.setLocationRelativeTo(null);
            f.setLayout(null);
            f.getContentPane().setBackground(new Color(44, 62, 80));

            JLabel info = new JLabel("Score: 0  |  Time: " + time, SwingConstants.CENTER);
            info.setFont(new Font("Segoe UI", Font.BOLD, 20));
            info.setForeground(Color.WHITE);
            info.setBounds(0, 10, 580, 30);
            f.add(info);

            JButton tgt = new JButton();
            tgt.setBackground(new Color(231, 76, 60));
            tgt.setBounds(250, 200, 40, 40);
            tgt.setFocusable(false);
            tgt.setBorderPainted(false);
            f.add(tgt);

            ActionListener mover = e -> tgt.setLocation(new Random().nextInt(530), new Random().nextInt(340) + 50);
            moveTimer = new Timer(2000, mover);

            tgt.addActionListener(e -> {
                score++;
                info.setText("Score: " + score + "  |  Time: " + time);
                mover.actionPerformed(null);
                moveTimer.restart();
            });

            gameTimer = new Timer(1000, e -> {
                if (--time <= 0) {
                    gameTimer.stop(); moveTimer.stop(); f.dispose();
                    JOptionPane.showMessageDialog(null, "Time's up! Final Score: " + score);
                    player.addScore(score);
                    System.gc();
                } else info.setText("Score: " + score + "  |  Time: " + time);
            });

            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { gameTimer.stop(); moveTimer.stop(); System.gc(); }
            });

            f.setVisible(true);
            moveTimer.start();
            gameTimer.start();
        }
    }


static class ReactionTest extends GameFeature {
        private long start;
        private boolean green = false;
        private Timer w, s;

        @Override
        void play() {
            JFrame f = new JFrame("Reaction");
            f.setSize(400, 400);
            f.setLocationRelativeTo(null);

            JPanel p = new JPanel(new GridBagLayout());
            p.setBackground(Color.RED);
            
            JLabel l = new JLabel("KLIK SAAT HIJAU!");
            l.setFont(new Font("Segoe UI", Font.BOLD, 24));
            l.setForeground(Color.WHITE);
            p.add(l);

            s = new Timer(400, e -> {
                p.setBackground(new Color(new Random().nextInt(255), new Random().nextInt(150), new Random().nextInt(255)));
            });
            s.start();

            p.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (green) {
                        long r = System.currentTimeMillis() - start;
                        JOptionPane.showMessageDialog(f, r + " ms! (+10)");
                        player.addScore(10);
                        f.dispose();
                    } else {
                        if (w.isRunning()) w.stop();
                        if (s.isRunning()) s.stop();
                        JOptionPane.showMessageDialog(f, "Kecepatan!");
                        f.dispose();
                    }
                    System.gc();
                }
            });

            f.add(p);
            f.setVisible(true);

            w = new Timer(new Random().nextInt(3000) + 1000, e -> {
                s.stop();
                p.setBackground(Color.GREEN);
                start = System.currentTimeMillis();
                green = true;
                w.stop();
            });
            w.start();

            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    if (w.isRunning()) w.stop();
                    if (s.isRunning()) s.stop();
                    System.gc();
                }
            });
        }
    }
}