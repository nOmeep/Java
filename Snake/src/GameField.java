import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int GAME_DOTS = 400;
    private Image snake;
    private Image apple;
    private int appleX;
    private int appleY;
    private final int[] x = new int[GAME_DOTS];
    private final int[] y = new int[GAME_DOTS];
    private int dots;
    private boolean alive = true;

    public boolean goingLeft; // false
    public boolean goingRight = true;
    public boolean goingUp; //false
    public boolean goingDown; //false

    public GameField() {
        setBackground(Color.black);
        loadImages();
        startGame();
        addKeyListener(new SnakeKeyListener());
        setFocusable(true);
    }

    public void startGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        Timer timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iis = new ImageIcon("snake.png");
        snake =  iis.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (alive) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(snake, x[i], y[i], this);
            }
        }
        else {
            g.setColor(Color.white);
            g.drawString("Game Over", SIZE/2 - 40 , 100);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (goingLeft) {
            x[0] -= DOT_SIZE;
        }
        if (goingRight) {
            x[0] += DOT_SIZE;
        }
        if (goingUp) {
            y[0] -= DOT_SIZE;
        }
        if (goingDown) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                alive = false;
            }

            if (x[0] > SIZE) {
                alive = false;
            }
            if (x[0] < 0) {
                alive = false;
            }
            if (y[0] > SIZE) {
                alive = false;
            }
            if (y[0] < 0) {
                alive = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (alive) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class SnakeKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !goingRight) {
                goingLeft = true;
                goingUp = false;
                goingDown = false;
            }
            if (key == KeyEvent.VK_RIGHT && !goingLeft) {
                goingRight = true;
                goingUp = false;
                goingDown = false;
            }
            if (key == KeyEvent.VK_UP && !goingDown) {
                goingUp = true;
                goingLeft = false;
                goingRight = false;
            }
            if (key == KeyEvent.VK_DOWN && !goingUp) {
                goingDown = true;
                goingLeft = false;
                goingRight = false;
            }
        }
    }
}




