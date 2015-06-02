/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author 0byte
 */
class Board extends JPanel implements ActionListener {

    public int gameScore = 0;
    public int eatedApples = 0;
    private final int WIDTH = 300;
    private final int HEIGHT = 300; //size of game field   
    private final int DOT_SIZE = 10;  //dot's size in px
    private final int ALL_DOTS = 900;//size of max game field
    private final int RAND_POS = 29;//random for calculating apple position      
    private int DELAY = 100;//game speed    
    private int x[] = new int[ALL_DOTS];//snake's posiition on a field
    private int y[] = new int[ALL_DOTS];
    private int dots;//length of snake
    private int apple_x;//apple position
    private int apple_y;
    private boolean left = false; //controlling keys
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;//game state
    private boolean bWin = false;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board() {
        addKeyListener(new TAdapter());

        setBackground(Color.BLACK);

        ImageIcon iiDot = new ImageIcon("res/greenBall.png");
        ImageIcon iia = new ImageIcon("res/cherry.png");
        ImageIcon iih = new ImageIcon("res/redBall.png");

        ball = iiDot.getImage();
        apple = iia.getImage();
        head = iih.getImage();

        setFocusable(true);
        initGame();
    }

    public void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (bWin) {
            youWin(g);
        } else if (inGame) {
            g.setColor(Color.GRAY);
            g.drawRect(1, 1, 310, 310);
            g.drawString(" Your score is : " + gameScore, 10, 345);



            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
            g.dispose();

        } else {
            gameOver(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    public void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    public void checkCollision() {
        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] > HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] > WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
    }

    public void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (left) {
            x[0] -= DOT_SIZE;
        }

        if (right) {
            x[0] += DOT_SIZE;
        }

        if (up) {
            y[0] -= DOT_SIZE;
        }

        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            eatedApples++;
            gameScore = eatedApples*10;
            
            if (eatedApples % 5 == 0) {//increase speed
                DELAY-=6;
                timer.setDelay(DELAY);
            }
            locateApple();
        }
    }

    public void youWin(Graphics g) {

        String msg = "You win";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        g.setColor(Color.GREEN);
        g.setFont(small);
        g.drawString(msg, (WIDTH - msg.length()) / 2, HEIGHT / 2);
        timer.stop();
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2,
                HEIGHT / 2);
        timer.stop();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_P)) {
                bWin = true;
                timer.setDelay(0);
            }
        }
    }
}
