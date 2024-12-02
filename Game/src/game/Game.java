
package game;

import javax.swing.JFrame;

/**
 *
 * @author Narcisse
 */
public class Game {


    public static void main(String[] args) {
        
        JFrame obj = new JFrame();
        GamePlay gameplay = new GamePlay();
        obj.setBounds(10, 10, 700, 620);
        obj.setTitle("Ball Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
    
}
