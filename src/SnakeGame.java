import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame {
    
    private Player player;
    private JFrame frame;
    private JPanel gamePanel;
    
    // Logika Game
    private final int TILE_SIZE = 25;
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    
    private ArrayList<Point> body;
    private Point food;
    private char direction = 'R'; // U, D, L, R
    private boolean isRunning = false;
    private Timer gameLoop;

    public SnakeGame(Player player) {
        this.player = player;
    }

    public void startGame() {
        // 1. Inisialisasi State Awal
        body = new ArrayList<>();
        body.add(new Point(5, 5));
        spawnFood();
        direction = 'R';
        isRunning = true;

        // 2. Setup Frame (Tanpa Extends)
        frame = new JFrame("Snake Game");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 3. Setup Panel dengan Custom Graphics (Anonymous Inner Class)
        // Ini menggantikan "class SnakePanel extends JPanel"
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Bersihkan layar
                
                // Gambar Background
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                // Gambar Makanan
                g.setColor(Color.RED);
                g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                // Gambar Ular
                g.setColor(Color.GREEN);
                for (Point p : body) {
                    g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        };

        // Agar keyboard terbaca, panel harus focusable
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        // 4. Setup KeyListener (Input Keyboard - Materi Slide 254)
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                
                // Logika agar tidak bisa putar balik langsung
                if (key == KeyEvent.VK_UP && direction != 'D') direction = 'U';
                if (key == KeyEvent.VK_DOWN && direction != 'U') direction = 'D';
                if (key == KeyEvent.VK_LEFT && direction != 'R') direction = 'L';
                if (key == KeyEvent.VK_RIGHT && direction != 'L') direction = 'R';
            }
        });

        frame.add(gamePanel);
        frame.setVisible(true);
        gamePanel.requestFocus(); // Pastikan fokus setelah window muncul

        // 5. Setup Timer Game Loop (Materi Timer Logic)
        gameLoop = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    updateGame();
                    gamePanel.repaint(); // Gambar ulang (Materi Graphics)
                }
            }
        });
        gameLoop.start();
        
        // Matikan timer jika window ditutup
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gameLoop.stop();
            }
        });
    }

    private void spawnFood() {
        int maxX = (WIDTH / TILE_SIZE) - 2;
        int maxY = (HEIGHT / TILE_SIZE) - 3;
        food = new Point(new Random().nextInt(maxX), new Random().nextInt(maxY));
    }

    private void updateGame() {
        Point head = body.get(0);
        Point newHead = new Point(head);

        // Gerakkan kepala
        if (direction == 'U') newHead.y--;
        if (direction == 'D') newHead.y++;
        if (direction == 'L') newHead.x--;
        if (direction == 'R') newHead.x++;

        // Cek Tabrakan (Dinding atau Badan Sendiri)
        int maxGridX = (WIDTH / TILE_SIZE) - 1;
        int maxGridY = (HEIGHT / TILE_SIZE) - 2;

        if (newHead.x < 0 || newHead.x >= maxGridX || newHead.y < 0 || newHead.y >= maxGridY || body.contains(newHead)) {
            gameOver();
            return;
        }

        body.add(0, newHead); // Tambah kepala di depan
        
        if (newHead.equals(food)) {
            spawnFood(); // Makan (jangan hapus ekor)
        } else {
            body.remove(body.size() - 1); // Jalan (hapus ekor belakang)
        }
    }

    private void gameOver() {
        isRunning = false;
        gameLoop.stop();
        int score = body.size();
        JOptionPane.showMessageDialog(frame, "Game Over! Panjang Ular: " + score);
        player.addScore(score);
        frame.dispose();
    }
}