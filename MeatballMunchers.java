/* 
Program Name: MeatballMunchers.java
Names: Kyle Saric & Ben Brake
Date: 1/7/2024
Purpose: Create an intuitive and polished game for users to eat as many meatballs as possible and have fun
 */

// Java imports
import javax.swing.*;
import java.awt.*;
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
        g.drawString("Meatball Munchers", 240, 250);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(350, 285, 100, 50);
        g.drawString("Start", 370, 320);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(325, 355, 150, 50);
        g.drawString("How to Play", 335, 390);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawRect(350, 425, 100, 50);
        g.drawString("Quit", 370, 460);
    }

    @Override
    // Start the game if the user clicks the start button
    public void handleClick(MouseEvent e, GamePanel panel) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int startButtonX = 350;
        int startButtonY = 285;
        int howToPlayButtonX = 350;
        int howToPlayButtonY = 355;
        int howToPlayButtonWidth = 150;
        int quitButtonX = 350;
        int quitButtonY = 425;

        int buttonWidth = 100;
        int buttonHeight = 50;

        if (mouseX >= startButtonX && mouseX <= startButtonX + buttonWidth && mouseY >= startButtonY && mouseY <= startButtonY + buttonHeight) {
            panel.setGameState(new GamePlayState());
        }
        if (mouseX >= howToPlayButtonX && mouseX <= howToPlayButtonX + howToPlayButtonWidth && mouseY >= howToPlayButtonY && mouseY <= howToPlayButtonY + buttonHeight) {
            panel.setGameState(new HowToPlayState());
        }
        if (mouseX >= quitButtonX && mouseX <= quitButtonX + buttonWidth && mouseY >= quitButtonY && mouseY <= quitButtonY + buttonHeight) {
            System.exit(0);
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
        g.drawRect(330, 250, 100, 50);
        g.drawString("Menu", 350, 285);

        g.setColor(new Color(51, 204, 0)); // Green
        g.fillOval(600, 135, 75, 75);
        g.setColor(new Color(0, 102, 0)); // Dark green
        g.fillOval(600 + 75 / 4, 135 + 75 / 4, 75 / 2, 75 / 2);

        g.setColor(new Color(204, 51, 0)); // Red
        g.fillOval(95, 135, 75, 75);
        g.setColor(new Color(153, 0, 0)); // Dark red
        g.fillOval(95 + 75 / 4, 135 + 75 / 4, 75 / 2, 75 / 2);

        /*
         * ADD THE OTHER TWO MEATBALLS/OBJECTS
         */
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

// Class for falling meatballs
class Meatball {
    private int x, y, diameter, speed;
    private boolean isPoison; 

    // Meatball constructor
    public Meatball(int x, int diameter, boolean isPoison) {
        this.x = x;
        this.y = 0;
        this.diameter = diameter;
        this.speed = 5;
        this.isPoison = isPoison;
    }
    
    // Give a meatball its speed
    public void updateSpeed() {
        y += speed;
    }

    // Draw a meatball
    public void draw(Graphics g) {
        if (isPoison) 
            g.setColor(new Color(51, 204, 0)); // Green
        else {
            g.setColor(new Color(204, 51, 0)); // Red
        }
        g.fillOval(x, y, diameter, diameter);

        if (isPoison) 
            g.setColor(new Color(0, 102, 0)); // Dark green
        else {
            g.setColor(new Color(153, 0, 0)); // Dark red
        }
        g.fillOval(x + diameter / 4, y + diameter / 4, diameter / 2, diameter / 2);
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
        return isPoison?-1:1;
    }

    // Check if the meatball has fallen past the floor
    public boolean isOffScreen() {
        return y > 600;
    }
}

// Concrete class for game play
class GamePlayState extends GameState {

    // Character attributes
    private int spriteX = 350;
    private final int spriteWidth = 50;
    private final int spriteHeight = 50;
    private final int moveSpeed = 10;
    private String openMessage = "";
    private int counter = 0;
    private static int hasEaten, hasBarfed;

    // Direction values
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // List of meatballs and the meatball score value
    private ArrayList<Meatball> meatballs;
    private int score = 0;

    // Random to generate numbers
    private Random random;

    // Constructor
    public GamePlayState() {
        meatballs = new ArrayList<>();
        random = new Random();
        openMessage = "CONSUME";
        counter = 100;
    }

    // Method to update variable to keep track of how many frames it has been since character has eaten
    public static void eats(int type) {
        if (type == 1)
            hasEaten = 10;
        else {
            hasBarfed = 10;
        }
    }

    @Override
    public void draw(Graphics g, GamePanel panel) {
        // Draw the character
        int spriteY = 572 - spriteHeight;
        g.setColor(Color.BLUE);
        g.fillOval(spriteX, spriteY, spriteWidth, spriteHeight); // Body
        g.setColor(Color.YELLOW);
        g.fillOval(spriteX-3, spriteY-35, spriteWidth+10, spriteHeight+10); // Head
        g.fillOval(spriteX+45, spriteY+15, 15, 15); // Hand
        g.fillOval(spriteX+15, 564, 25, 15); // Foot
        g.setColor(Color.BLACK); 
        g.fillOval(spriteX+5, spriteY-20, 5, 5); // Eye
        g.fillOval(spriteX+12, spriteY-30, 30, 10); // Mouth
        g.setColor(Color.RED);
        g.fillOval(spriteX+35, spriteY-29, 5, 8); // Tongue

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

        // Draw the meatball counter in the top right hand corner
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Meatball Score: " + score, 630, 30);

        // Present the opening "Consume" message for the first 100 frames (~1.5 sec)
        if (counter>0) {
            counter--;
            g.setFont(new Font("Arial", Font.BOLD, 130));
            if (counter < 75)
                g.drawString(openMessage, 60, 180);
            if (counter < 50)
                g.drawString(openMessage, 60, 310);
            if (counter < 25)
                g.drawString(openMessage, 60, 440);
        }
    
    }

    // Unused
    @Override
    public void handleClick(MouseEvent e, GamePanel panel) {
    }

    @Override
    // Controls to move the character left and right across the screen or enter the pause menu with p
    public void handleKeyPress(int keyCode, GamePanel panel) {
        if (keyCode == KeyEvent.VK_LEFT) 
            moveLeft = true;
        else if (keyCode == KeyEvent.VK_RIGHT) {
            moveRight = true;
        } else if (keyCode == KeyEvent.VK_P) {
            panel.setGameState(new PauseMenuState(this));
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
            spriteX = Math.max(spriteX - moveSpeed, 0);
        if (moveRight == true) 
            spriteX = Math.min(spriteX + moveSpeed, 800 - spriteWidth);

        // Randomly generate a meatball (1/25 chance every frame, 1 frame every 16 ms, avg 2.5 meatballs ever second)
        if (random.nextInt(25) == 0) { 
            int x = random.nextInt(740);
            meatballs.add(new Meatball(x, (30+random.nextInt(30)), random.nextBoolean()));
        }

        // Checks on the positions of the meatballs and reacts accordingly
        Iterator<Meatball> iterator = meatballs.iterator();
        while (iterator.hasNext()) {
            Meatball meatball = iterator.next();
            meatball.updateSpeed();

            // Removes meatballs off-screen to prevent lagging
            if (meatball.isOffScreen())
                iterator.remove();
        
            // Removes meatballs the character has caught and adds to the counter (based on size of meatball)
            else if (meatball.getBounds().intersects(new Rectangle(spriteX, panel.getHeight() - (15+spriteHeight) - 10, (5+spriteWidth), (15+spriteHeight)))) {
                iterator.remove();
                score += meatball.isPoison()*Math.round(meatball.getDiam()/5);
                if (score<0)
                    score = 0;
                // Variable shows the character has eaten (will display message next 10 frames)
                GamePlayState.eats(meatball.isPoison());
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
        if (mouseX >= buttonX && mouseX <= buttonX+buttonWidth && mouseY >= restartButtonY && mouseY <= restartButtonY + buttonHeight) {
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
    // Handle the key release using the current state
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

