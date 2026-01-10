import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends BaseGameWindow implements ActionListener, KeyListener {
    
    private final int TILE_SIZE = 25;
    private final int WIDTH = 500, HEIGHT = 500;
    private ArrayList<Point> body = new ArrayList<>();
    private Point food;
    private char direction = 'R'; // R, L, U, D
    private Timer timer;

    public SnakeGame(Player player) {
        super("Snake Game", player, 500, 500);
        
        addKeyListener(this);
        setFocusable(true);
        
        body.add(new Point(5, 5));
        spawnFood();
        
        // Timer menjalankan method actionPerformed setiap 100ms
        timer = new Timer(100, this);
        timer.start();
        
        setVisible(true);
    }

    private void spawnFood() {
        food = new Point(new Random().nextInt(WIDTH/TILE_SIZE - 2), new Random().nextInt(HEIGHT/TILE_SIZE - 2));
    }

    // POLYMORPHISM: Override method paint dari JFrame/Component
    @Override
    public void paint(Graphics g) {
        // Menggambar latar belakang (untuk membersihkan layar sebelumnya)
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Gambar Makanan
        g.setColor(Color.RED);
        g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Gambar Ular
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    // Logika Game Loop
    @Override
    public void actionPerformed(ActionEvent e) {
        Point head = body.get(0);
        Point newHead = new Point(head);

        if (direction == 'U') newHead.y--;
        if (direction == 'D') newHead.y++;
        if (direction == 'L') newHead.x--;
        if (direction == 'R') newHead.x++;

        // Cek Tabrakan
        if (newHead.x < 0 || newHead.x >= WIDTH/TILE_SIZE || newHead.y < 0 || newHead.y >= HEIGHT/TILE_SIZE || body.contains(newHead)) {
            timer.stop();
            finishGame(body.size() * 2, "Ular Mati!");
            return;
        }

        body.add(0, newHead);
        if (newHead.equals(food)) {
            spawnFood(); // Makan, jangan hapus ekor (tambah panjang)
        } else {
            body.remove(body.size() - 1); // Bergerak biasa
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP && direction != 'D') direction = 'U';
        if (key == KeyEvent.VK_DOWN && direction != 'U') direction = 'D';
        if (key == KeyEvent.VK_LEFT && direction != 'R') direction = 'L';
        if (key == KeyEvent.VK_RIGHT && direction != 'L') direction = 'R';
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}