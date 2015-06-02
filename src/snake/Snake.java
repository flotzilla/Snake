/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package snake;

import javax.swing.JFrame;

/**
 *
 * @author 0byte
 */
public class Snake extends JFrame {

    private final int SCREEN_WIFTH = 320;
    private final int SCREEN_HEIGTH = 400;

    public Snake() {
        add(new Board());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SCREEN_WIFTH, SCREEN_HEIGTH);
        setLocationRelativeTo(null);
        setTitle("Snake");

        setResizable(false);
        setVisible(true);
    }

    public int getSCREEN_HEIGTH() {
        return SCREEN_HEIGTH;
    }

    public int getSCREEN_WIFTH() {
        return SCREEN_WIFTH;
    }

    public static void main(String[] args) {
        Snake sn = new Snake();
    }
}
