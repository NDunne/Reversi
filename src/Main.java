import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); //screen dimensions in pixels

    //Create Objects from custom classes
    static Board GameBoard;
    static Player Player;
    static Computer Computer;

    static int turnCount; //total number of possible flips this turn. 0 if no move can be played
    static int noMoveCount = 0; //number of consecutive turns where no player can play

    static boolean currentTurn; //Boolean turn. Player = True, Computer = false
    static boolean endGame = false; //Boolean flag to signal that the game is over

    static JFrame MainMenu; //Home window
    static JFrame Options; //Options window
    static JFrame window; //Game window

    static Image u;
    static ImageIcon baseImg; //Button base Image
    static ImageIcon rollImg; //Button rollover image
    static JButton[][] buttons = new JButton[8][8]; //2D array of buttons

    static Boolean showTips = true;

    static Boolean loadFlag;

    public static JFrame getWindow() {
        return window;
    }

    public static void setLoadFlag(Boolean newF) {loadFlag = newF;}
    public static Boolean getLoadFlag() {return loadFlag;}

    public static void main(String[] args) {
        File outFile = new File("log.txt");
        PrintStream out;
         try {
            out = new PrintStream(new FileOutputStream(outFile));
        } catch(FileNotFoundException a) {
            out = null;
        }
        System.setOut(out);

        createAndShowMainMenu();
        //buttons in the main menu window trigger all subsequent actions
    }

    public static void createAndShowMainMenu() {
        //may be called from the options menu so can't just be in 'main' function
        MainMenu = new JFrame();
        MainMenu.setBounds(screen.width / 4, screen.height / 4, screen.width / 2, screen.height / 2);
        MainMenu.setTitle("Main Menu");//title at the top of the window
        MainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //action when the window is closed
        MainMenu.setLayout(null);
        //null layout allows absolute button placement

        JLabel title = new JLabel();
        title.setText("Reversi"); //Title text
        title.setBounds(3 * MainMenu.getWidth() / 8, 0, MainMenu.getWidth(), MainMenu.getHeight() / 4);
        //Title position and size
        title.setFont(new Font("Serif", Font.PLAIN, MainMenu.getHeight() / 8));

        Font bf = new Font("Serif", Font.PLAIN, MainMenu.getHeight() / 16);
        //bf = button font, same for all 3 buttons

        //New Game
        JButton newGameBut = new JButton();
        newGameBut.setBounds(MainMenu.getWidth() / 16, MainMenu.getHeight() / 4, 7 * MainMenu.getWidth() / 8, MainMenu.getHeight() / 8);
        newGameBut.setText("New Game");
        newGameBut.setFont(bf);
        newGameBut.addActionListener(new MenuActionListener()); //MenuActionListener applies to all Gui objects

        //Load Game
        JButton loadGameBut = new JButton();
        loadGameBut.setBounds(MainMenu.getWidth() / 16, MainMenu.getHeight() / 2, 7 * MainMenu.getWidth() / 8, MainMenu.getHeight() / 8);
        loadGameBut.setText("Load Game");
        loadGameBut.setFont(bf);
        loadGameBut.addActionListener(new MenuActionListener());

        //Options Menu
        JButton optionsBut = new JButton();
        optionsBut.setBounds(MainMenu.getWidth() / 16, 3 * MainMenu.getHeight() / 4, 7 * MainMenu.getWidth() / 8, MainMenu.getHeight() / 8);
        optionsBut.setText("Options");
        optionsBut.setFont(bf);
        optionsBut.addActionListener(new MenuActionListener());

        //Add all the elements to the MainMenu Frame
        MainMenu.add(title);
        MainMenu.add(newGameBut);
        MainMenu.add(loadGameBut);
        MainMenu.add(optionsBut);
        MainMenu.setVisible(true);
    }

    public static void createAndShowOptions() {
        Options = new JFrame();
        Options.setBounds(screen.width / 4, screen.height / 16, screen.width / 2, 13 * screen.height / 16); //Places the window in the centre
        Options.setTitle("Options");
        Options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Options.setLayout(new BorderLayout());

        JLabel title = new JLabel(); //Title
        title.setText("            Options");
        title.setBounds(3 * MainMenu.getWidth() / 8, 0, MainMenu.getWidth(), MainMenu.getHeight() / 4);
        title.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 8));

        JPanel Content = new JPanel(new GridLayout(3, 1)); //GUI formatting
        Content.setBorder(new EmptyBorder(Options.getHeight() / 200, Options.getWidth() / 150, 0, 0)); //top,left,bottom,right

        //--Computer difficulty ComboBox--//
        JPanel difPanel = new JPanel();
        difPanel.setLayout(new GridLayout(3, 1));

        //Computer difficulty title
        JLabel difLabel = new JLabel("Computer Difficulty");
        difLabel.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 16));

        String[] difficulties = {"Hard", "Medium", "Easy"};
        JComboBox difBox = new JComboBox(difficulties);
        difBox.addActionListener(new MenuActionListener());
        difBox.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 24));

        difPanel.add(difLabel);
        difPanel.add(difBox);
        Content.add(difPanel);

        //--Board Graphic Style--//
        JPanel stylePanel = new JPanel(new BorderLayout());

        //Panel for just radios//
        JPanel styleRadPanel = new JPanel(new FlowLayout());

        //Graphic Style title
        JLabel styleLabel = new JLabel("Graphics Style");
        styleLabel.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 16));

        //Three radios, one for each style//
        JRadioButton candy = new JRadioButton("Candy", loadOptionsThumb("/Assets_Ca/MiniBoard.gif", 0));
        candy.addActionListener(new MenuActionListener());
        candy.setSelectedIcon(loadOptionsThumb("/Assets_Ca/MiniBoardSel.gif", 4));
        candy.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 36));

        JRadioButton traditional = new JRadioButton("Traditional", loadOptionsThumb("/Assets_Tr/MiniBoard.gif", 0));
        traditional.addActionListener(new MenuActionListener());
        traditional.setSelectedIcon(loadOptionsThumb("/Assets_Tr/MiniBoardSel.gif", 4));
        traditional.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 36));
        traditional.setSelected(true);

        JRadioButton futuristic = new JRadioButton("Futuristic", loadOptionsThumb("/Assets_Fu/MiniBoard.gif", 0));
        futuristic.addActionListener(new MenuActionListener());
        futuristic.setSelectedIcon(loadOptionsThumb("/Assets_Fu/MiniBoardSel.gif", 4));
        futuristic.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 36));

        //add title and 3 radios to the panel, gridLayout
        styleRadPanel.add(candy);
        styleRadPanel.add(traditional);
        styleRadPanel.add(futuristic);

        stylePanel.add(styleLabel, BorderLayout.NORTH);
        stylePanel.add(styleRadPanel, BorderLayout.LINE_START);

        Content.add(stylePanel);

        //adds radios to a buttonGroup
        ButtonGroup boardStyle = new ButtonGroup();
        boardStyle.add(candy);
        boardStyle.add(traditional);
        boardStyle.add(futuristic);

        //-Beginner tips-//
        GridLayout tipsLayout = new GridLayout(4, 1); //extra row is added for spacing
        tipsLayout.setVgap(0);
        JPanel tipsPanel = new JPanel(tipsLayout);
        JLabel tipsLabel = new JLabel("How to Play");
        tipsLabel.setSize(Options.getWidth(), Options.getHeight() / 8);
        tipsLabel.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 16));
        nCheckBox beginner = new nCheckBox("Beginner tips");
        beginner.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 24));
        beginner.setSize(new Dimension(100, 100));
        beginner.setSelected(true);

        tipsPanel.add(tipsLabel);
        tipsPanel.add(beginner);
        Content.add(tipsPanel);

        JButton backToMain = new JButton();
        backToMain.setText("Main Menu");
        backToMain.setFont(new Font("Serif", Font.PLAIN, Options.getHeight() / 16));
        backToMain.setPreferredSize(new Dimension(Options.getWidth(), Options.getHeight() / 10));
        backToMain.addActionListener(new MenuActionListener());

        //Add all elements to the Frame
        Options.add(title, BorderLayout.NORTH);
        Options.add(Content, BorderLayout.LINE_START);
        Options.add(backToMain, BorderLayout.SOUTH);
        Options.setVisible(true);
    }

    static ImageIcon loadOptionsThumb(String filename, int plus) {
        try {
            u = ImageIO.read(new File("../Assets" + filename));
        } catch (IOException e) {
        }
        return new ImageIcon(u.getScaledInstance((Options.getWidth() / 10) + plus, (Options.getWidth() / 10) + plus, Image.SCALE_SMOOTH));
    }

    public static void createAndShowGameBoard() {

        System.out.println("LOADFLAG: " + loadFlag);
        window = new GameWindow(); //Main Window
        GameBoard = new Board(); //Custom panel with board image.
        GameBoard.setBounds(window.getWidth() / 4, 0, window.getWidth() / 2, window.getWidth() / 2);
        GameBoard.setLayout(null); //allows absolute button placement

        if (!loadFlag) {
            new infoWindow("You will play as:", true, false);
            if (showTips) {
                new rulesWindow(window);
            }
        } else {
            loadBoard();
        }
        GameBoard.loadimages();

        JButton backToMain = new JButton();
        backToMain.setText("Exit");
        backToMain.addActionListener(new MenuActionListener());
        backToMain.setFont(new Font("Serif", Font.PLAIN, window.getHeight() / 24));
        backToMain.setBounds((int)(7*window.getWidth()/16),GameBoard.getHeight(),window.getWidth()/8,(int)(window.getHeight() - (GameBoard.getHeight()*1.05)));

        window.setLayout(null);
        window.add(Player.getPanel());
        window.add(GameBoard);
        window.add(Computer.getPanel());
        window.add(backToMain);

        window.setVisible(true);
        if (loadFlag) {
            //this comparision must be done twice because of when certain elements are required to be instantiated
            nextTurn();
        }
    }

    static void saveBoard() {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(new File("boardFile.txt")));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (GameBoard.findPiece(j, i) == null) {
                        fw.write("E");
                        //"E" for empty tile
                    } else {
                        if (GameBoard.findPiece(j, i).controller) {
                            fw.write("P");
                            //"P" for player piece
                        } else {
                            fw.write("C");
                            //"C" for Computer piece
                        }
                    }
                }
                fw.write("\n");
                //line break divides each row for easier reading
            }
            fw.write(Boolean.toString(currentTurn)); //current turn must be known
            fw.write("\n" + Player.colourString);    //ensures players are re-assigned correct pieces
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadBoard() {
        Board.RP(); //resets the pieces array to an 8x8 of null objects
        try {
            Scanner s = new Scanner(new File("boardFile.txt"));
            for (int i = 0; i < 8; i++) {
                String next = s.next(); //reads next line into string variable
                for (int j = 0; j < 8; j++) {
                    System.out.print(next.substring(j, j + 1) + " ");
                    //takes a single character from the line, representing a single tile
                    if (next.substring(j, j + 1).equals("C")) {
                        Board.newPiece(j, i, false);
                    } else if (next.substring(j, j + 1).equals("P")) {
                        Board.newPiece(j, i, true);
                    }
                }
                System.out.print("\n");
            }
            currentTurn = Boolean.parseBoolean(s.next()); //next line is what the current turn was as a string

            //Re-assigning colours to ensure correct images are drawn for each player
            if (s.next().equals("White")) {
                Player = new Player(true, "Player", "White");
                Computer = new Computer(false, "Computer", "Black");
            } else {
                Player = new Player(true, "Player", "Black");
                Computer = new Computer(false, "Computer", "White");
            }
        } catch (FileNotFoundException f) {
            //if file "boardFile.txt" is not found, a new game is created instead
            loadFlag = false;
            createAndShowGameBoard();
        }
    }

    static void findEnemies() {
        //loops through whole pieces array
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (GameBoard.findPiece(i, j) != null) { //not null if there is a piece there
                    if (GameBoard.findPiece(i, j).controller != currentTurn) { //find all enemy pieces

/**/                    System.out.println("ENEMY PIECE @ " + i + ", " + j);
                        GameBoard.findPiece(i, j).findButtons(currentTurn);
                    }
                }
            }
        }
    }

    public static void setShowTips(Boolean Bool) {
        //Boolean flag for displaying beginner tips, called from menuActionListener
        showTips = Bool;
    }

    static void endGame() {
//    System.out.println("ENDGAME");
        if (Player.myCount > Computer.myCount) {
            new infoWindow("The Player has won!", false, true);
        } else if (Player.myCount < Computer.myCount) {
            new infoWindow("The computer won!", false, true);
        } else {
            new infoWindow("Its a Tie!", false, true);
        }
    }

    static void nextTurn() {
/**/
        System.out.println("------NEXT TURN-----");
        saveBoard(); //current state of the board is saved
        GameBoard.repaint();

        Player.updatePanel();
        Computer.updatePanel();
        //reset Computer values
        Computer.newTurn();

        Piece.resetChecked();

        turnCount = 0;

        System.out.println("currentTurn = " + !currentTurn);
        if (Player.rem == 0) {
            endGame();
        } else {
            //if the game isn't over, play the next turn
            currentTurn = !currentTurn;
            findEnemies();
            if (currentTurn == false) {
                try {
                    //slight delay on Computer move, so player can see what they did
                    Thread.sleep((long) 500);
                    //thread.sleep is used as GUI doesn't need to update in this time
                    Computer.takeTurn();
                } catch (InterruptedException i) {
                    //interrupt thread in case of error
                    Thread.currentThread().interrupt();
                }

            }

            System.out.println("This Turn: " + turnCount + " noMoveCount: " + noMoveCount);
            if (turnCount == 0) {
                //if turnCount = 0 then there were no possible moves this turn
                noMoveCount += 1; //the number of turns with no possible moves is incremented
                //UIManager.put("OptionPane.font", new FontUIResource(new Font("Serif", Font.PLAIN, GameBoard.getHeight() /2)));
                if (currentTurn == true) {
                    //display confirm box for user, so it is clear what has happened
                    new infoWindow("The Player has no valid\nmoves this turn", false, false);
                } else {
                    new infoWindow("The Computer has no\nvalid moves this turn", false, false);
                }
            } else {
                //Reset the count of turns with no moves.
                noMoveCount = 0;
            }
            if (noMoveCount == 2) {
                //If both players cannot take their turn, the game is over
                endGame();
            }

            GameBoard.repaint();
        }
    }

    public static void placeButton(final int x, final int y, int toolTip, String toFlip) {

//        System.out.println("   -CREATING BUTTON @ " + x + "," + y + "\r        tooltip: " + toolTip);
        if (currentTurn == true) {
            rollImg = new ImageIcon(Board.readImageFile(Player.colourString + "Counter2.gif"));
            baseImg = new ImageIcon(Board.readImageFile(Player.colourString + "Counter3.gif"));
        } else {
            rollImg = new ImageIcon(Board.readImageFile(Computer.colourString + "Counter2.gif"));
            baseImg = new ImageIcon(Board.readImageFile(Computer.colourString + "Counter3.gif"));
        }
        if (!showTips) {
            baseImg = new ImageIcon(Board.readImageFile("empty.gif"));
        }
        //If Beginner tips are disabled then the base image is an empty tile.
        //The rollover image remains either way.

        buttons[x][y] = new JButton(baseImg);
        buttons[x][y].setBounds(GameBoard.getWidth() / 8 * x + 10, GameBoard.getHeight() / 8 * y + 10, GameBoard.getWidth() / 8 - 20, GameBoard.getHeight() / 8 - 20);
        buttons[x][y].setActionCommand(toFlip);
        buttons[x][y].setBorderPainted(false);
        buttons[x][y].setFocusPainted(false);

        buttons[x][y].setToolTipText("<html><p><font size=\"20\">" + Integer.toString(toolTip) + "</font></p></html>");
        buttons[x][y].setRolloverIcon(rollImg);
        Main.GameBoard.add(buttons[x][y]);

        buttons[x][y].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toFlip = e.getActionCommand();

                Board.newPiece(x, y, currentTurn);
                buttons = new JButton[8][8];

                Main.GameBoard.removeAll();

                new delay(1000 / toFlip.split(",").length, toFlip.split(","));

                Player.rem -= 1;
            }

        });
    }
}