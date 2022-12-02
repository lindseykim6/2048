import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import javax.swing.border.Border;

/**
 * 2048 game interface
 *
 * @author Lindsey Kim
 */
public class TwentyFortyEightGameInterface extends JFrame{
    protected int width = 800;					// the size of the drawing window
    protected int height = 600;
    private TwentyFortyEight game;
    private boolean showedMessage = false;
    private JPanel gamePanel;
    private JComponent canvas;
    private JButton newGameButton;
    public TwentyFortyEightGameInterface() {
        super("2048");
        game = new TwentyFortyEight(); // new Game instance
        game.startGame(); // adds random tile
        game.boardStatusToString(); //prints to string
        JComponent canvas = setupCanvas(); //add GUI canvas

        Container cp = this.getContentPane();
        setSize(width, height);
        cp.add(canvas);

        // Usual initialization
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    /**
     * Paints the game interface on the screen
     */
    public void paintInterface() {
        int DIM = 4;
        JPanel[][] gameGrid = new JPanel[DIM][DIM]; // JPanel grid
        for (int row = 0; row < DIM; row++) {
            for (int col = 0; col < DIM; col++) {
                int number =game.getNumber(col, row);
                //sets the number on JPanel tile if there is a number
                if(number !=0) {
                    JPanel tile = new JPanel();
                    tile.setLayout(new GridBagLayout());
                    JLabel numberLabel= new JLabel(""+number, SwingConstants.CENTER);
                    tile.add(numberLabel);
                    numberLabel.setFont(new Font("Serif", Font.PLAIN, 48));
                    numberLabel.setForeground(new Color(84, 80, 75));
                    numberLabel.setPreferredSize(new Dimension(128,128));
                    gameGrid[row][col]= tile;
                }
                //otherwise create empty tile
                else {
                    JPanel tile = new JPanel();
                    gameGrid[row][col]= tile;
                }
                //set border color based on number
                Border border = BorderFactory.createLineBorder(new Color(119, 110, 101), 3);
                gameGrid[row][col].setBorder(border);
                Color background = switch (number) {
                    case 0 -> new Color(204, 192, 179);
                    case 2 -> new Color(238, 228, 218);
                    case 4 -> new Color(233, 200, 195);
                    case 8 -> new Color(242, 177, 121);
                    case 16 -> new Color(245, 149, 99);
                    case 32 -> new Color(246, 124, 95);
                    case 64 -> new Color(246, 94, 59);
                    case 128 -> new Color(237, 207, 114);
                    case 256 -> new Color(237, 204, 97);
                    case 512 -> new Color(237, 200, 80);
                    case 1024 -> new Color(237, 197, 63);
                    case 2048 -> new Color(237, 194, 46);
                    default -> Color.BLACK;
                };
                gameGrid[row][col].setBackground(background);
            }
        }

        //creates the game panel to put the grid on
        gamePanel = new JPanel();
        gamePanel.setSize(new Dimension(3*width/4, 3*height/4));
        gamePanel.setLayout(new GridLayout(DIM, DIM));
        for (int row = 0; row < DIM; row++) {
            for (int col = 0; col < DIM; col++) {
                gamePanel.add(gameGrid[row][col]);
            }
        }

        //adds new game button
        newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new TwentyFortyEight();
                game.startGame();
                resetGUI(); //reset the game
                revalidate();
                JOptionPane.getRootFrame().dispose();
            }
        });

        // adds OK button with lost game
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showedMessage = true;
                resetGUI(); //reset the gui with ok button
                revalidate();
                JOptionPane.getRootFrame().dispose();
            }
        });

        JButton[] options = { okButton, newGameButton };

        //creates game state dialog
        if(game.getGameState().equals(TwentyFortyEight.GameState.LOSE) && !showedMessage) {
            JOptionPane.showOptionDialog(null, "YOU LOST!", ":(",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[1]);
        } else if(game.getGameState().equals(TwentyFortyEight.GameState.WIN)&& !showedMessage) {
            JOptionPane.showOptionDialog(null, "YOU WON!", ":)",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[1]);
        }
    }

    /**
     * Sets up the canvas for painting
     */
    private JComponent setupCanvas(){
        canvas = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        newGameButton = new JButton("NEW GAME"); //initializes initial new game button
        newGameButton.setSize(30,30);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new TwentyFortyEight();
                game.startGame();
                resetGUI();

            }
        });

        // sets out layout for GUI
        buttons.add(newGameButton);
        canvas.add(buttons, BorderLayout.NORTH);
        paintInterface();
        canvas.add(gamePanel, BorderLayout.SOUTH);

        //sets the actions for each left, right, up, and down key stroke and resets afterwards
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");

        canvas.getActionMap().put("down", new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
//                System.out.println("down");
            if(game.getGameState().equals(TwentyFortyEight.GameState.IN_PROGRESS)) {
                game.swipeDown();
                game.boardStatusToString();
                resetGUI();
            }
        }
        });
        canvas.getActionMap().put("up", new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            if(game.getGameState().equals(TwentyFortyEight.GameState.IN_PROGRESS)) {
//                    System.out.println("up");
                game.swipeUp();
                game.boardStatusToString();
                resetGUI();
            }
            }
        });

        canvas.getActionMap().put("left", new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            if(game.getGameState().equals(TwentyFortyEight.GameState.IN_PROGRESS)) {
//                System.out.println("left");
                game.swipeLeft();
                game.boardStatusToString();
                resetGUI();
            }
        }
        });
        canvas.getActionMap().put("right", new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            if(game.getGameState().equals(TwentyFortyEight.GameState.IN_PROGRESS)) {
//                    System.out.println("right");
                game.swipeRight();
                game.boardStatusToString();
                resetGUI();
            }

        }
        });
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        return canvas;

    }

    /**
     * resets interface after a move
     */

    private void resetGUI() {
        gamePanel.setVisible(false);
        paintInterface();
        canvas.add(gamePanel, BorderLayout.SOUTH);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TwentyFortyEightGameInterface();

            }
        });
    }
}
