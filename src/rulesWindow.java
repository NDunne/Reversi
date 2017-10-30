import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class rulesWindow extends JDialog implements ActionListener {

    Dimension screen = Main.screen;
    rulesWindow(Frame window) {
        super(window);
        setTitle("How to Play");
        setBounds((int) (screen.getWidth() / 6), (int) (screen.getHeight() / 12),(int) (2*screen.getWidth()/3), (int) (8*screen.getHeight()/9));
        setBackground(new Color(255,255,255));
        setLayout(new BorderLayout());
        JLabel title = new JLabel(" Instructions");
        title.setFont(new Font("Serif",Font.PLAIN,getHeight()/24));

        JLabel rules = new JLabel();
        try {
            rules.setIcon(new ImageIcon(ImageIO.read(new File("rules.PNG")).getScaledInstance((int)(2*screen.getWidth()/3),(int) (7*screen.getHeight()/9), Image.SCALE_SMOOTH)));
        } catch(IOException i) {
            System.out.println("File not found");
        }

        JTextArea endRules = new JTextArea("The game continues like this, with White and Black taking alternate turns" +
                "until the board is filled or neither player can play. If one player cannot play, the turn is handed" +
                "over to their opponent. Once the game has ended, the number of pieces on the board for each player" +
                "is counted, and the player with the most pieces wins the game.");

        JButton exit = new JButton();
        exit.setText("Confirm");
        exit.setFont(new Font ("Serif", Font.ITALIC,getHeight()/24));
        System.out.println("window: " + getHeight() + " button: " + screen.getHeight()/27);
        exit.addActionListener(this);

        add(title,BorderLayout.PAGE_START);
        add(rules,BorderLayout.CENTER);
        add(exit,BorderLayout.SOUTH);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        dispose();
    }
}
