
/* 
Program Name: MeatballMunchers.java
Names: Kyle Saric & Ben Brake
Date: 1/07/2024
Purpose: Create an intuitive and polished game for users to eat as many meatballs as possible and have fun
 */

// Java imports
import javax.swing.*;
import java.util.Collections;
import java.awt.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;    

public class MeatballMunchers extends JFrame {

    private GamePanel panel;

    public MeatballMunchers() {
        // Create a game panel to run game functions
        panel = new GamePanel();
        add(panel);

        // Set-up starting menu with 
        setTitle("Meatball Munchers");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { 
        MeatballMunchers frame = new MeatballMunchers(); 
        frame.setVisible(true);
        });
    }
}

// Abstract class for game states
abstract class GameState {
    public abstract void draw(Graphics g, GamePanel panel);
    public abstract void handleClick(MouseEvent e, GamePanel panel);
    public abstract void handleKeyPress(int keyCode, GamePanel panel);
}

// Concrete class for main menu
class MainMenuState extends GameState {
    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Meatball Munchers", 240, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(350, 235, 100, 50);
        g.drawString("Start", 370, 270);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(325, 305, 150, 50);
        g.drawString("How to Play", 335, 340);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(325, 375, 150, 50);
        g.drawString("Leaderboard", 333, 410);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(350, 445, 100, 50);
        g.drawString("Quit", 375, 480);
    }

    @Override
    // Start the game if the user clicks the start button
    public void handleClick(MouseEvent e, GamePanel panel) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int startButtonX = 350;
        int startButtonY = 235;
        int howToPlayButtonX = 350;
        int howToPlayButtonY = 305;
        int howToPlayButtonWidth = 150;
        int highScoreButtonX = 325;
        int highScoreButtonY = 375;
        int quitButtonX = 350;
        int quitButtonY = 445;

        int buttonWidth = 100;
        int buttonHeight = 50;

        if (mouseX >= startButtonX && mouseX <= startButtonX + buttonWidth && mouseY >= startButtonY && mouseY <= startButtonY + buttonHeight) {
            panel.setGameState(new GamePlayState());
        }
        if (mouseX >= howToPlayButtonX && mouseX <= howToPlayButtonX + howToPlayButtonWidth && mouseY >= howToPlayButtonY && mouseY <= howToPlayButtonY + buttonHeight) {
            panel.setGameState(new HowToPlayState());
        }
        if (mouseX >= highScoreButtonX && mouseX <= highScoreButtonX + howToPlayButtonWidth && mouseY >= highScoreButtonY && mouseY <= highScoreButtonY + buttonHeight) {
            panel.setGameState(new HighScoreState());
        }
        if (mouseX >= quitButtonX && mouseX <= quitButtonX + buttonWidth && mouseY >= quitButtonY && mouseY <= quitButtonY + buttonHeight) {
            System.exit(0);
        }
    }
    // Unused
    @Override
    public void handleKeyPress(int keyCode, GamePanel panel) {}
}

class HighScoreState extends GameState{
    TopScores topScores = new TopScores();

    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Leaderboard", 290, 200);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawRect(348, 225, 100, 170);
        g.drawString("1 - " + topScores.getScores().get(0), 368, 260);
        g.drawString("2 - " + topScores.getScores().get(1), 368,  290);
        g.drawString("3 - " + topScores.getScores().get(2), 368, 320);
        g.drawString("4 - " + topScores.getScores().get(3), 368, 350);
        g.drawString("5 - " + topScores.getScores().get(4), 368, 380);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(350, 410, 100, 50);
        g.drawString("Menu", 368, 445);
    }

    @Override
    // Start the game if the user clicks the start button
    public void handleClick(MouseEvent e, GamePanel panel) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int backToMenuButtonX = 350;
        int backToMenuButtonY = 410;

        int buttonWidth = 100;
        int buttonHeight = 50;

        if (mouseX >= backToMenuButtonX && mouseX <= backToMenuButtonX + buttonWidth && mouseY >= backToMenuButtonY && mouseY <= backToMenuButtonY + buttonHeight) {
            panel.setGameState(new MainMenuState());
        }
    }
    // Unused
    @Override
    public void handleKeyPress(int keyCode, GamePanel panel) {}
}

class HowToPlayState extends GameState{
    @Override
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Normal Meatball", 50, 50);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("collect to gain points", 35, 100);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Rotten Meatball", 545, 50);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("collect to lose points", 525, 100);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Spiked Meatball", 50, 400);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("kills player", 80, 450);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Speed Meatball", 540, 400);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("movement speed up", 520, 450);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(350, 250, 100, 50);
        g.drawString("Menu", 368, 285);
        
        g.setColor(new Color(51, 204, 0)); // Green
        g.fillOval(600, 135, 75, 75);
        g.setColor(new Color(0, 102, 0)); // Dark green
        g.fillOval(600 + 75 / 4, 135 + 75 / 4, 75 / 2, 75 / 2);

        g.setColor(new Color(204, 51, 0)); // Red
        g.fillOval(95, 135, 75, 75);
        g.setColor(new Color(153, 0, 0)); // Dark red
        g.fillOval(95 + 75 / 4, 135 + 75 / 4, 75 / 2, 75 / 2);

        g.setColor(new Color(0,0,0));
        g.fillOval(95, 300, 75, 75);
        g.setColor(new Color(50,50,50));
        g.fillOval(95+75/4, 300+75/4, 75/2, 75/2);

        g.setColor(new Color(255, 255, 255));
        g.fillOval(600, 300, 75, 75);
        g.setColor(new Color(200, 200, 200));
        g.fillOval(600+75/4, 300+75/4, 75/2, 75/2);
    }

    @Override
    // Start the game if the user clicks the start button
    public void handleClick(MouseEvent e, GamePanel panel) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int backToMenuButtonX = 350;
        int backToMenuButtonY = 250;

        int buttonWidth = 100;
        int buttonHeight = 50;

        if (mouseX >= backToMenuButtonX && mouseX <= backToMenuButtonX + buttonWidth && mouseY >= backToMenuButtonY && mouseY <= backToMenuButtonY + buttonHeight) {
            panel.setGameState(new MainMenuState());
        }
    }
    // Unused
    @Override
    public void handleKeyPress(int keyCode, GamePanel panel) {}
}

// Class for passing clouds
class Cloud {
    private int x, y, diameter, speed;
    
    // Cloud constructor
    public Cloud(int y, int diameter) {
        this.y = y;
        this.x = 800;
        this.diameter = diameter;
        this.speed = 1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE); 
        // Cloud include rectangle base and several sized circular tops
        g.fillRect(x, (int) Math.round(y-diameter*.3), (int) Math.round(diameter*2.3), (int) Math.round(diameter/1.4));
        g.fillOval((int) Math.round(x-diameter*0.4), (int) Math.round(y-diameter*0.4), (int) Math.round(diameter*0.8), (int) Math.round(diameter*0.8));
        g.fillOval((int) Math.round(x+diameter*0.2), (int) Math.round(y-diameter*0.8), (int) Math.round(diameter*0.8), (int) Math.round(diameter*0.8));
        g.fillOval((int) Math.round(x+diameter*0.8), (int) Math.round(y-diameter*1.0), (int) Math.round(diameter*0.8), (int) Math.round(diameter*0.8));
        g.fillOval((int) Math.round(x+diameter*1.4), (int) Math.round(y-diameter*0.8), (int) Math.round(diameter*0.8), (int) Math.round(diameter*0.8));
        g.fillOval((int) Math.round(x+diameter*1.8), (int) Math.round(y-diameter*0.4), (int) Math.round(diameter*0.8), (int) Math.round(diameter*0.8));
    }

    // Give a cloud its speed
    public void updateSpeed() {
        x -= speed;
    }

    // Check if the cloud has fallen past the floor
    public boolean isOffScreen() {
        return x < diameter*-3;
    }
}

// Class for falling meatballs
class Meatball {
    private int x, y, diameter, speed;
    private int randType;

    // Meatball constructor
    public Meatball(int x, int diameter, int randType) {
        this.x = x;
        this.y = 0;
        this.diameter = diameter;
        this.speed = 5;
        this.randType = randType;
    }
    
    // Give a meatball its speed
    public void updateSpeed() {
        y += speed;
    }

    // Draw a meatball
    public void draw(Graphics g) {
        if (randType == 0) 
            g.setColor(new Color(51, 204, 0)); // Green
        else if (randType == 1 || randType == 2) {
            g.setColor(new Color(204, 51, 0)); // Red
        }
        else if (randType == 3){
            g.setColor(new Color(0,0,0));
        }
        else if (randType == 4){
            g.setColor(new Color(255, 255, 255));
        }
        g.fillOval(x, y, diameter, diameter);

        if (randType == 0) 
            g.setColor(new Color(0, 102, 0)); // Dark green
        else if (randType == 1 || randType == 2){
            g.setColor(new Color(153, 0, 0)); // Dark red
        }
        else if (randType == 3){
            g.setColor(new Color(50,50,50));
        }
        else if (randType == 4){
            g.setColor(new Color(200, 200, 200));
        }
        g.fillOval(x+diameter/4, y+diameter/4, diameter/2, diameter/2);
    }
    
    // Return the space the meatball is taking up (for collision detection)
    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    // Return size of meatball for score counting
    public int getDiam() {
        return diameter;
    }

    public int isPoison() {
        if (randType == 0){
            return -1;
        }
        else if (randType == 1 || randType == 2){
            return 1;
        }
        else if (randType == 3){
            return 2;
        }
        else if (randType == 4){
            return 3;
        }
        //if it somehow doesnt hit any of the if statements
        return 1;
    }

    // Check if the meatball has fallen past the floor
    public boolean isOffScreen() {
        return y > 600;
    }
}

// Class to record top 5 scores ever
class TopScores {
    private ArrayList<Integer> scores = new ArrayList<>();
    // Score list contructor, reads the scores on file
    public TopScores() {
        try (BufferedReader scoreReader = new BufferedReader(new FileReader("src\\scoreRecord.txt"))) {
            String line;
            while ((line = scoreReader.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line)); // Parse and add the score
                } catch (NumberFormatException e) {
                    // System.out.println("Invalid number format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred reading from file.");
            e.printStackTrace();
        }
        Collections.sort(scores, Collections.reverseOrder()); // Sort the scores from highest to lowest
    }
    // Method to add to the score record (if its a top 5 score)
    public void addScore(int score) {
        if (scores.size() < 5 || score > scores.get(scores.size()-1)) {
            scores.add(score);
            Collections.sort(scores, Collections.reverseOrder()); // Sort the scores from highest to lowest
            if (scores.size() > 5) {
                scores.remove(scores.size() - 1); // Remove the lowest score if more than 5
            }
            saveScores();
        }
    }

    // Method to check if the score is a record (if its a top 5 score)
    public boolean checkScore(int score) {
        if (scores.size() < 5 || score > scores.get(scores.size()-1)) {
            return true;
        }
        return false;
    }
    // Method to save the latest scores on file
    private void saveScores() {
        try {
            File record = new File("src\\scoreRecord.txt");
            FileWriter scoreWriter = new FileWriter(record);
            for (int score : scores) {
                scoreWriter.write("\n" + score);
            }
            scoreWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred writing to file.");
            e.printStackTrace();
        }
        
    }
    public ArrayList<Integer> getScores() {
        return scores;
    }
}

class DieState extends GameState {

    GamePlayState gamePlayState = new GamePlayState();

    @Override 
    // Draw the gameplay, updating several game components along the way
    public void draw(Graphics g, GamePanel panel) {
        gamePlayState.ended = true;
        g.setFont(new Font("Arial", Font.BOLD, 130));
        g.setColor(Color.BLACK);
        g.drawString("YOU", 265, 200);
        g.drawString("DIED", 245, 350);

        // Ben pls make good score display w/ top5 scores using TopScores.getScores();
        g.drawRect(350, 390, 100, 40);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Menu", 368, 420);
    }
    @Override
    public void handleClick(MouseEvent e, GamePanel panel) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int backToMenuButtonX = 350;
        int backToMenuButtonY = 390;

        int buttonWidth = 100;
        int buttonHeight = 40;

        if (mouseX >= backToMenuButtonX && mouseX <= backToMenuButtonX + buttonWidth && mouseY >= backToMenuButtonY && mouseY <= backToMenuButtonY + buttonHeight) {
            panel.setGameState(new MainMenuState());
        }
    }

    @Override
    // Controls to move the character left and right across the screen or enter the pause menu with p
    public void handleKeyPress(int keyCode, GamePanel panel) {
        if (keyCode == KeyEvent.VK_R && gamePlayState.ended) {
            panel.setGameState(new MainMenuState());
        }
    }
}

// Concrete class for game play
class GamePlayState extends GameState {

    // Character attributes
    TopScores topScores = new TopScores();
    public boolean ended;
    private boolean scoreEnded;
    private double bubbleCounterA = 36, bubbleCounterB = 35, bubbleCounterC = 35;
    private int raftChange, raftChangeCounter, sinkSpeed = 1, maxSink = 520; // make this 0 and have win clause change it to 520  
    private int spriteX = 350, spriteY = 455;
    public static int moveSpeed = 10;
    private final int spriteWidth = 50, spriteHeight = 50;
    private int counter = 0;
    
    private static int hasEaten, hasBarfed;

    // Direction values
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // List of meatballs and the meatball score value
    private ArrayList<Meatball> meatballs;
    private int score = 0;

    // List of clouds
    private ArrayList<Cloud> clouds;

    // Random to generate numbers
    private Random random;

    // Constructor
    public GamePlayState() {
        meatballs = new ArrayList<>();
        clouds = new ArrayList<>();
        random = new Random();
        counter = 100;
    }

    // Method to update variable to keep track of how many frames it has been since character has eaten
    public static void eats(int type, GamePanel panel) {
        if (type == 1)
            hasEaten = 10;
        else if (type == -1){
            hasBarfed = 10;
        }
        else if (type == 2){
            panel.setGameState(new DieState());
        }
        else if (type == 3){
            moveSpeed += 1;
        }
    }

    @Override 
    // Draw the gameplay, updating several game components along the way
    public void draw(Graphics g, GamePanel panel) {

        // Water
        g.setColor(Color.BLUE);
        g.fillRect(0, 550, 800, 50);
        g.setColor(new Color(101, 67, 33));

        // Barrel rims
        g.fillOval(22, 507+raftChange, 81, 81); 
        g.fillOval(107, 507+raftChange, 81, 81); 
        g.fillOval(191, 507+raftChange, 81, 81); 
        g.fillOval(275, 507+raftChange, 81, 81); 
        g.fillOval(359, 507+raftChange, 81, 81); 
        g.fillOval(443, 507+raftChange, 81, 81); 
        g.fillOval(527, 507+raftChange, 81, 81); 
        g.fillOval(611, 507+raftChange, 81, 81); 
        g.fillOval(697, 507+raftChange, 81, 81); 
        g.fillRect(25, 510+raftChange, 750, 10);
        
        // Barrels
        g.setColor(new Color(39, 97, 41)); // Dark, barrel green
        g.fillOval(25, 510+raftChange, 75, 75); 
        g.fillOval(110, 510+raftChange, 75, 75); 
        g.fillOval(194, 510+raftChange, 75, 75); 
        g.fillOval(278, 510+raftChange, 75, 75); 
        g.fillOval(362, 510+raftChange, 75, 75); 
        g.fillOval(446, 510+raftChange, 75, 75); 
        g.fillOval(530, 510+raftChange, 75, 75); 
        g.fillOval(614, 510+raftChange, 75, 75); 
        g.fillOval(700, 510+raftChange, 75, 75); 

        // Barrel caps
        g.setColor(new Color(153, 0, 0));
        g.fillOval(57, 520+raftChange, 10, 10);
        g.fillOval(142, 520+raftChange, 10, 10);
        g.fillOval(226, 520+raftChange, 10, 10);
        g.fillOval(311, 520+raftChange, 10, 10);
        g.fillOval(394, 520+raftChange, 10, 10);
        g.fillOval(479, 520+raftChange, 10, 10);
        g.fillOval(563, 520+raftChange, 10, 10);
        g.fillOval(647, 520+raftChange, 10, 10);
        g.fillOval(733, 520+raftChange, 10, 10);
        g.setColor(new Color(15, 39, 16));
        g.fillOval(55, 555+raftChange, 15, 15);
        g.fillOval(140, 555+raftChange, 15, 15);
        g.fillOval(224, 555+raftChange, 15, 15);
        g.fillOval(309, 555+raftChange, 15, 15);
        g.fillOval(392, 555+raftChange, 15, 15);
        g.fillOval(477, 555+raftChange, 15, 15);
        g.fillOval(561, 555+raftChange, 15, 15);
        g.fillOval(645, 555+raftChange, 15, 15);
        g.fillOval(731, 555+raftChange, 15, 15);

        // Draw the character
        spriteY = Math.min(455+raftChange, maxSink);
        g.setColor(Color.BLACK); 
        g.fillOval(spriteX-2, spriteY-2, spriteWidth+4, spriteHeight+4); // Body
        g.setColor(Color.BLUE); 
        g.fillOval(spriteX, spriteY, spriteWidth, spriteHeight); // Body
        g.setColor(Color.YELLOW);
        g.fillOval(spriteX-3, spriteY-35, spriteWidth+10, spriteHeight+10); // Head
        g.fillOval(spriteX+45, spriteY+15, 15, 15); // Hand
        g.fillOval(spriteX+15, spriteY+43, 25, 15); // Foot
        g.setColor(Color.BLACK); 
        g.fillOval(spriteX+5, spriteY-20, 5, 5); // Eye
        g.fillOval(spriteX+12, spriteY-30, 30, 10); // Mouth
        g.setColor(Color.RED);
        g.fillOval(spriteX+35, spriteY-29, 5, 8); // Tongue

        // Sink the raft
        raftChangeCounter++;
        if (raftChangeCounter > sinkSpeed) {
            raftChange++;
            raftChangeCounter = 0;
        }

        // Slow the character if in the water
        if (spriteY > 500) {
            moveSpeed = 5;
        }
        if (spriteY > 510) {
            moveSpeed = 2;
        }

        // If the character is about to sink, check whether they are to be saved or not
        if (spriteY >= 520) {
            scoreEnded = true;
            if (topScores.checkScore(score)) {
                // Run helicopter scene
                ended = true;
                topScores.addScore(score);
                System.out.println("hi");
            } else {
                maxSink = 600+spriteHeight;

                // Bubble animation
                if (spriteY > 591) {
                    g.setColor(Color.WHITE);
                    if (spriteY-(int)Math.round(bubbleCounterA) > 547) 
                        g.fillOval(spriteX+27, spriteY-(int)Math.round(bubbleCounterA), 6, 6);
                    bubbleCounterA += .75;
                }
                if (spriteY > 598) {
                    if (spriteY-(int)Math.round(bubbleCounterB) > 546) 
                        g.fillOval(spriteX+20, spriteY-(int)Math.round(bubbleCounterB), 8, 8);
                    bubbleCounterB += .75;
                }
                if (spriteY > 605) {
                    if (spriteY-(int)Math.round(bubbleCounterC) > 546) 
                        g.fillOval(spriteX+30, spriteY-(int)Math.round(bubbleCounterC), 7, 7);
                    bubbleCounterC += .75;
                }

                if (spriteY == 600+spriteHeight) { 
                    ended = true;
                    topScores.addScore(score);
                    g.setFont(new Font("Arial", Font.BOLD, 130));
                    g.setColor(Color.BLACK);
                    g.drawString("YOU", 265, 200);
                    g.drawString("DROWNED", 55, 350);

                    // Ben pls make good score display w/ top5 scores using TopScores.getScores();
                    g.drawRect(350, 390, 100, 40);
                    g.setFont(new Font("Arial", Font.BOLD, 24));
                    g.drawString("Menu", 368, 420);
                }
            }
        }
    

        // If the character has eaten within the last 10 frames, display an eating message
        if (hasEaten>0) {
            hasEaten--;
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("nOM", spriteX+50, spriteY-30);
        }

        // If the character has eaten a green meatball within the last 10 frames, display an barfing message
        if (hasBarfed>0) {
            hasBarfed--;
            g.setColor(new Color(51, 204, 0));
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("bARf", spriteX+50, spriteY-30);
        }

        // Draw all the meatballs in using a method within the meatball class (to access their diameter specifications)
        for (Meatball meatball : meatballs) {
            meatball.draw(g);
        }

        for (Cloud cloud : clouds) {
            cloud.draw(g);
        }

        // Draw the meatball counter in the top right hand corner
        if (!ended) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Meatball Score: " + score, 630, 30);
        }
        // Present the opening "Consume" message for the first 100 frames (~1.5 sec)
        if (counter>0) {
            counter--;
            g.setFont(new Font("Arial", Font.BOLD, 130));
            if (counter < 75)
                g.drawString("CONSUME", 60, 180);
            if (counter < 50)
                g.drawString("CONSUME", 60, 310);
            if (counter < 25)
                g.drawString("CONSUME", 60, 440);
        }
    
    }

    // Unused
    @Override
    public void handleClick(MouseEvent e, GamePanel panel) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int backToMenuButtonX = 350;
        int backToMenuButtonY = 390;

        int buttonWidth = 100;
        int buttonHeight = 40;

        if (mouseX >= backToMenuButtonX && mouseX <= backToMenuButtonX + buttonWidth && mouseY >= backToMenuButtonY && mouseY <= backToMenuButtonY + buttonHeight) {
            panel.setGameState(new MainMenuState());
        }
    }

    @Override
    // Controls to move the character left and right across the screen or enter the pause menu with p
    public void handleKeyPress(int keyCode, GamePanel panel) {
        if (keyCode == KeyEvent.VK_LEFT) 
            moveLeft = true;
        else if (keyCode == KeyEvent.VK_RIGHT) {
            moveRight = true;
        } else if (keyCode == KeyEvent.VK_P && !ended) {
            panel.setGameState(new PauseMenuState(this));
        } else if (keyCode == KeyEvent.VK_R && ended) {
            panel.setGameState(new MainMenuState());
        }
    }

    // Stops moving the character when the key is released
    public void handleKeyRelease(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) 
            moveLeft = false;
        else if (keyCode == KeyEvent.VK_RIGHT) {
            moveRight = false;
        }
    }
    
    // Regularly updates the game when actions are performed
    public void update(GamePanel panel) {                                                                                     

        // Moves character left and right at their speed, within the bounds of the panel
        if (moveLeft == true) 
            spriteX = Math.max(spriteX-moveSpeed, 25);
        if (moveRight == true) 
            spriteX = Math.min(spriteX+moveSpeed, 775-spriteWidth);

        // Randomly generate a meatball (1/25 chance every frame, 1 frame every 16 ms, avg 2.5 meatballs ever second)
        if (random.nextInt(25) == 0 && !ended) { 
            int x = random.nextInt(740);
            meatballs.add(new Meatball(x, (30+random.nextInt(10)), random.nextInt(3)+1));
        }

        // Randomly generate a cloud 
        if (random.nextInt(300) == 0 && !ended) { 
            int y = random.nextInt(200) + 25; 
            clouds.add(new Cloud(y, (40+random.nextInt(15))));
        }

        // Checks on the positions of the meatballs and reacts accordingly
        Iterator<Meatball> iterator = meatballs.iterator();
        Iterator<Cloud> cloudIterator = clouds.iterator();
        while (cloudIterator.hasNext()) {
            Cloud cloud = cloudIterator.next();
            cloud.updateSpeed();
            // Check if the cloud is on the screen still
            if (cloud.isOffScreen()) 
                cloudIterator.remove();
        }
        while (iterator.hasNext()) {
            Meatball meatball = iterator.next();
            meatball.updateSpeed();

            // Removes meatballs off-screen to prevent lagging
            if (meatball.isOffScreen())
                iterator.remove();

            // Removes meatballs the character has caught and adds to the counter (based on size of meatball) or removes if rotten
            else if (meatball.getBounds().intersects(new Rectangle(spriteX, spriteY-20 , (5+spriteWidth), (spriteHeight)))) {
                iterator.remove();
                if (!scoreEnded)
                    score += meatball.isPoison()*Math.round(meatball.getDiam()/5);
                if (score<0)
                    score = 0;
                // Variable shows the character has eaten (will display message next 10 frames)
                GamePlayState.eats(meatball.isPoison(), panel);
            }
        }
    }
}

// Concrete class for pause menu
class PauseMenuState extends GameState {
    private final GamePlayState previousState;

    // Constructor
    public PauseMenuState(GamePlayState previousState) {
        this.previousState = previousState;
    }

    @Override
    // Draws the pause menu, including a title, a resume button, and a restart button
    public void draw(Graphics g, GamePanel panel) {
        g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent background
        g.fillRect(200, 200, 400, 200);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Pause Menu", 330, 250);

        g.drawRect(300, 280, 200, 40);
        g.drawString("Resume", 360, 310);

        g.drawRect(300, 330, 200, 40);
        g.drawString("Restart", 360, 360);
    }

    @Override
    public void handleClick(MouseEvent e, GamePanel panel) {
        // Mouse position variables
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        // Button positional data
        int buttonX = 300;
        int resumeButtonY = 280;
        int restartButtonY = 330;
        int buttonWidth = 200;
        int buttonHeight = 40;
        
        // If user clicks resume, resume the game 
        if (mouseX >= buttonX && mouseX <= buttonX+buttonWidth && mouseY >= resumeButtonY && mouseY <= resumeButtonY+buttonHeight) {
            panel.setGameState(previousState);
        }

        // If the user clicks restart, return to the main menu
        if (mouseX >= buttonX && mouseX <= buttonX+buttonWidth && mouseY >= restartButtonY && mouseY <= restartButtonY+buttonHeight) {
            panel.setGameState(new MainMenuState());
        }
    }

    @Override
    // Add additional key press options for the menu
    public void handleKeyPress(int keyCode, GamePanel panel) {
        // If the user presses p again, resume the game
        if (keyCode == KeyEvent.VK_P) {
            panel.setGameState(previousState);
        }
        // If the user presses r, restart the game
        if (keyCode == KeyEvent.VK_R) {
            panel.setGameState(new MainMenuState());
        }
    }
}

// Class 
class GamePanel extends JPanel implements KeyListener, ActionListener {
    private GameState currentState;
    private Timer timer;

    // Constructor and set-up for the game
    public GamePanel() {
        setBackground(new Color(135, 206, 235)); // Sky Blue
        setFocusable(true);
        addKeyListener(this);

        // Mouse click detector
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentState.handleClick(e, GamePanel.this);
            }
        });
        
        // Start on main menu
        currentState = new MainMenuState();

        // Timer controls how often the game is updated, in this case 16ms
        timer = new Timer(16, this);
        timer.start();
    }

    // Set the current game state (main menu, pause menu, or game play)
    public void setGameState(GameState state) {
        currentState = state;
    }

    @Override
    // Assemble the current state
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentState.draw(g, this); 
    }
    
    @Override
    // Handle the key press using the current state
    public void keyPressed(KeyEvent e) {
        currentState.handleKeyPress(e.getKeyCode(), this);
    }

    @Override
    // Handle t he key release using the current state
    public void keyReleased(KeyEvent e) {
        if (currentState instanceof GamePlayState) {
            ((GamePlayState) currentState).handleKeyRelease(e.getKeyCode());
        }
    }

    // Unused
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    // Called by timer every 16ms (tied to timer via GamePanel)
    public void actionPerformed(ActionEvent e) {
        // If the current state is the game play, update the game
        if (currentState instanceof GamePlayState) {
            ((GamePlayState) currentState).update(this);
        }
        repaint();
    }
}
