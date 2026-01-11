import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamesHub {
    static Player player = new Player();
    static JLabel scoreLabel;

    static boolean cekAkun(JFrame p) {
        if (player.getID() == null) {
            JOptionPane.showMessageDialog(p, "Daftar akun dulu!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    static void updateTitle(JFrame f) {
        f.setTitle("Player: " + player.getID());
    }


    static class GuessingGame {
        public void play() {
            int target = new Random().nextInt(10) + 1;
            int attempts = 4;
            try {
                while (attempts > 0) {
                    String input = JOptionPane.showInputDialog(null, 
                        "Tebak Angka 1-10\nNyawa: " + attempts);
                    
                    if (input == null) break;

                    int guess = Integer.parseInt(input);
                    
                    if (guess == target) {
                        JOptionPane.showMessageDialog(null, "Benar! +10 Poin");
                        player.addScore(10);
                        return; // Keluar method
                    } else {
                        attempts--;
                        String hint = "";
                        if (guess < target){
                            hint = "Kekecilan";
                        } else { hint = "Kebesaran"; }
                        JOptionPane.showMessageDialog(null, hint + "! Sisa: " + attempts);
                    }
                }
                JOptionPane.showMessageDialog(null, "Gagal! Angkanya: " + target);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Masukkan Angka!");
            } finally {
                System.gc();
            }
        }
    }

    static class SnakeGame {
        private JFrame frame;
        private JPanel panel;
        private final int TILE = 25;

        private ArrayList<Point> body = new ArrayList<>();

        private Point food;
        private char dir = 'R';
        private Timer timer;

        private void spawnFood() {
            food = new Point(new Random().nextInt(18), new Random().nextInt(18));
        }

        private void update() {
            Point head = body.get(0);
            Point newHead = new Point(head);
            
            if(dir=='U') newHead.y--;
            if(dir=='D') newHead.y++;
            if(dir=='L') newHead.x--;
            if(dir=='R') newHead.x++;

            if(newHead.x<0 || newHead.x>=19 || newHead.y<0 || newHead.y>=18 || body.contains(newHead)) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Game Over! Skor: " + body.size());
                player.addScore(body.size());
                frame.dispose();
                System.gc();
                return;
            }

            body.add(0, newHead);
            if(newHead.equals(food)) spawnFood();
            else body.remove(body.size()-1);

            panel.repaint();
        }

        public void play() {
            body.add(new Point(5, 5));
            spawnFood();

            frame = new JFrame("Snake");
            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            
            panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.BLACK);
                    g.fillRect(0,0,500,500);
                    
                    g.setColor(Color.RED);
                    g.fillOval(food.x*TILE, food.y*TILE, TILE, TILE);
                    
                    g.setColor(Color.GREEN);
                    for(Point p : body) g.fillRect(p.x*TILE, p.y*TILE, TILE, TILE);
                }
            };
            
            panel.setFocusable(true);
            panel.requestFocusInWindow();
            
            panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int k = e.getKeyCode();
                if(k==KeyEvent.VK_UP && dir!='D') dir='U';
                if(k==KeyEvent.VK_DOWN && dir!='U') dir='D';
                if(k==KeyEvent.VK_LEFT && dir!='R') dir='L';
                if(k==KeyEvent.VK_RIGHT && dir!='L') dir='R';
            }
            });

            frame.add(panel);
            frame.setVisible(true);

            timer = new Timer(100, e -> update());
            timer.start();

            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    timer.stop();
                    frame = null; // Hapus referensi
                    System.gc(); // Request pembersihan memori 
                }
            });
        }
    }

    // ==========================================
    // GAME 3: AIM TRAINER (MATERI SLIDE 166)
    // ==========================================
    static class AimTrainer {
        private int score = 0;
        private int time = 10;
        private Timer timer;

        public void play() {
            JFrame frame = new JFrame("Aim Trainer");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null); // Layout manual agar tombol bisa random

            JLabel info = new JLabel("Time: " + time);
            info.setBounds(10, 10, 100, 20);
            frame.add(info);

            JButton target = new JButton("X");
            target.setBackground(Color.RED);
            target.setForeground(Color.WHITE);
            target.setBounds(200, 200, 50, 50);
            
            // Logic Klik
            target.addActionListener(e -> {
                if (time > 0) {
                    score++;
                    int x = new Random().nextInt(500);
                    int y = new Random().nextInt(300) + 30;
                    target.setBounds(x, y, 50, 50);
                }
            });
            frame.add(target);

            frame.setVisible(true);

            timer = new Timer(1000, e -> {
                time--;
                info.setText("Time: " + time);
                if (time <= 0) {
                    timer.stop();
                    target.setEnabled(false);
                    JOptionPane.showMessageDialog(frame, "Skor: " + score);
                    player.addScore(score);
                    frame.dispose();
                    System.gc(); // Memory Management
                }
            });
            timer.start();
            
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { timer.stop(); System.gc(); }
            });
        }
    }

    // ==========================================
    // GAME 4: REACTION TEST (MATERI SLIDE 222)
    // ==========================================
    static class ReactionTest {
        private long start;
        private boolean green = false;
        private Timer wait;

        public void play() {
            JFrame frame = new JFrame("Reaction");
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);

            JPanel p = new JPanel();
            p.setBackground(Color.RED);
            p.add(new JLabel("TUNGGU HIJAU -> KLIK!"));
            
            // Mouse Listener
            p.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (green) {
                        long res = System.currentTimeMillis() - start;
                        JOptionPane.showMessageDialog(frame, res + " ms! (+10 Poin)");
                        player.addScore(10);
                        frame.dispose();
                    } else {
                        if(wait.isRunning()) wait.stop();
                        JOptionPane.showMessageDialog(frame, "Kecepatan! Gagal.");
                        frame.dispose();
                    }
                    System.gc(); // Bersihkan memori setelah game
                }
            });

            frame.add(p);
            frame.setVisible(true);

            int delay = new Random().nextInt(3000) + 1000;
            wait = new Timer(delay, e -> {
                p.setBackground(Color.GREEN);
                start = System.currentTimeMillis();
                green = true;
                wait.stop();
            });
            wait.start();
            
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { if(wait.isRunning()) wait.stop(); System.gc(); }
            });
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Games Hub");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel titleLabel = new JLabel(" MENU ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        
        JButton btnAkun = new JButton("1. Daftar Akun");
        JButton btnGuess = new JButton("2. Tebak Angka");
        JButton btnSnake = new JButton("3. Snake Game");
        JButton btnAim = new JButton("4. Aim Trainer");
        JButton btnReact = new JButton("5. Reaction Test");
        JButton btnExit = new JButton("Keluar");

        // === Event Handling (MATERI SLIDE 198) ===
        btnAkun.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Masukkan Nama:");
            if (id != null && !id.isEmpty()) {
                player.setID(id);
                updateTitle(frame);
            }
        });

        btnGuess.addActionListener(e -> { if(cekAkun(frame)) new GuessingGame().play(); });
        btnSnake.addActionListener(e -> { if(cekAkun(frame)) new SnakeGame().play(); });
        btnAim.addActionListener(e ->   { if(cekAkun(frame)) new AimTrainer().play(); });
        btnReact.addActionListener(e -> { if(cekAkun(frame)) new ReactionTest().play(); });
        
        btnExit.addActionListener(e -> {
            System.out.println("Aplikasi ditutup. Membersihkan memori...");
            System.gc();
            System.exit(0);
        });

        new Timer(1000, e -> scoreLabel.setText("Score: " + player.getScore())).start();

        mainPanel.add(titleLabel);
        mainPanel.add(scoreLabel);
        mainPanel.add(btnAkun);
        mainPanel.add(btnGuess);
        mainPanel.add(btnSnake);
        mainPanel.add(btnAim);
        mainPanel.add(btnReact);
        mainPanel.add(btnExit);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}