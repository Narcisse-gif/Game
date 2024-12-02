
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GamePlay extends JPanel implements KeyListener, ActionListener{
    
    
    private boolean play = false;
    private int score = 0;
    
    private int totalBricks = 21;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerx = 310;
    
    private int ballposx = 120;
    private int ballposy = 350;
    private int ballxdlr = -1;
    private int ballydlr = -2;
    
    private Generator map;
    
    
    public GamePlay(){
        map = new Generator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        map.draw((Graphics2D)g);
        
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" +score, 590, 30);
        
        
        g.setColor(Color.green);
        g.fillRect(playerx, 550, 100, 8);
        
        g.setColor(Color.yellow);
        g.fillRect(ballposx, ballposy, 10, 10);
        
        if(totalBricks <= 0){
            play = false;
            ballxdlr = 0;
            ballydlr = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won! ", 260, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        
        if(ballposy > 570){
            play = false;
            ballxdlr = 0;
            ballydlr = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: ", 190, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
        
        g.dispose();
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
         
    }

    @Override
    public void keyPressed(KeyEvent e) {
         if(e.getKeyCode() == KeyEvent.VK_RIGHT){
             if(playerx >= 600){
                 playerx = 600;
             }else{
                 moveRight();
             }
         }
         if(e.getKeyCode() == KeyEvent.VK_LEFT){
             if(playerx < 10){
                 playerx = 10;
             }else{
                 moveLeft();
             }
        }
         if(e.getKeyCode() == KeyEvent.VK_LEFT){
             if(!play){
                 play = true;
                 ballposx = 120;
                 ballposy = 350;
                 ballxdlr = -1;
                 ballydlr = -2;
                 
                 playerx = 310;
                 score = 0;
                 totalBricks = 21;
                 map = new Generator(3,7);
                 
                 repaint();
                 
             }
         }
    }
    
    public void moveRight(){
        play = true;
        playerx +=20;
    }
    
    public void moveLeft(){
        play = true;
        playerx -=20;
    }


    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        
        if(play){
            
            if(new Rectangle(ballposx, ballposy, 10, 10).intersects(new Rectangle(playerx,550,100,8))){
                ballydlr = -ballydlr;
            }
            
            A:for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                   if(map.map[i][j] > 0){
                       int brickx = j * map.brickwidth + 80;
                       int bricky = i * map.brickwidth + 50;
                       int brickwidth = map.brickwidth;
                       int brickheight = map.brickheight;
                       
                       Rectangle rect = new Rectangle(brickx, bricky, brickwidth, brickheight);
                       Rectangle ballRect = new Rectangle(ballposx, ballposy, 10,10);
                       Rectangle brickRect = rect;
                       
                       if(ballRect.intersects(brickRect)){
                           map.setBrickValue(0, i, j);
                           totalBricks--;
                           score += 5;
                           
                        if(ballposx + 19 <= brickRect.x || ballposx + 1 >= brickRect.x + brickRect.width){
                          ballxdlr = -ballxdlr;
   
                        } else{
                            ballydlr = -ballydlr;
                        }
                        break A;
                       }
                       
                   } 
                }
            }
            
            ballposx += ballxdlr;
            ballposy += ballydlr;
            if(ballposx < 0){
               ballxdlr = -ballxdlr;
            }
            if(ballposy < 0){
                ballydlr = -ballydlr;
            }
            if(ballposx > 670){
                ballxdlr = -ballxdlr;
            }
        }
         
        repaint();
    }

    
    
    
}
