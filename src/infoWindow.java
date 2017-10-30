import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by User on 23/02/2016.
 */
public class infoWindow extends JDialog implements ActionListener {

    ImageIcon i;
    Dimension screen = Main.screen;
    Boolean start;
    Boolean end;

    infoWindow(String Message, Boolean s, Boolean e){
        super(Main.getWindow(),"Information");
        start = s;
        end = e;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (!end) {
                    Main.nextTurn();
                    //System.exit(0);
                } else {
                    Main.getWindow().dispose();
                    Main.createAndShowMainMenu();
                }
            }

        });
        setLayout(null);
        setBounds((int) (screen.getWidth() / 3), (int) (3*screen.getHeight() / 10), (int) (screen.getWidth()/4), (int) (3 * screen.getHeight() / 8));
        if (start) {
            JLabel img = new JLabel();
            img.setBounds(this.getWidth()/2 - Main.GameBoard.getWidth()/12, this.getHeight() / 2 - Main.GameBoard.getWidth()/12, Main.GameBoard.getWidth()/6, Main.GameBoard.getHeight()/6);
            int rn = (int) Math.round(Math.random());
            if (rn == 0) {
                i = new ImageIcon(Board.readImageFile("WhitePanel.gif"));
                Main.currentTurn = false;

                Main.Player = new Player(true, "Player", "White");
                Main.Computer = new Computer(false, "Computer", "Black");
                //Initiates starting pieces.
                Board.pieces[3][3] = new Piece(3, 3, true);
                Board.pieces[4][4] = new Piece(4, 4, true);
                Board.pieces[3][4] = new Piece(3, 4, false);
                Board.pieces[4][3] = new Piece(4, 3, false);
            } else {
                i = new ImageIcon(Board.readImageFile("BlackPanel.gif"));
                Main.currentTurn = true;

                Main.Player = new Player(true, "Player", "Black");
                Main.Computer = new Computer(false, "Computer", "White");
                //Initiates starting pieces.
                Board.pieces[3][3] = new Piece(3, 3, false);
                Board.pieces[4][4] = new Piece(4, 4, false);
                Board.pieces[3][4] = new Piece(3, 4, true);
                Board.pieces[4][3] = new Piece(4, 3, true);
            }

            img.setIcon(i);
            add(img);
            Main.noMoveCount = 0;
        }
        else if(end) {
            JLabel img = new JLabel();
            if (Main.Player.myCount > Main.Computer.myCount) {
                img.setIcon(new ImageIcon(Board.readImageFile(Main.Player.colourString + "Panel.gif")));
            } else if (Main.Player.myCount < Main.Computer.myCount) {
                img.setIcon(new ImageIcon(Board.readImageFile(Main.Computer.colourString + "Panel.gif")));
            } else {
                img.setIcon(new ImageIcon(Board.readImageFile("MiniBoard.gif")));
            }
            img.setBounds(this.getWidth()/2 - Main.GameBoard.getWidth()/12, this.getHeight() / 2 - Main.GameBoard.getWidth()/12, Main.GameBoard.getWidth()/6, Main.GameBoard.getHeight()/6);
            add(img);
        }


        JTextArea info = new JTextArea(Message);
        info.setFocusable(false);
        info.setEditable(false);
        info.setOpaque(false);
        info.setFont(new Font("Serif", Font.BOLD, (int) screen.getHeight()/30));
        info.setBounds(this.getWidth()/10,this.getHeight()/10,4*this.getWidth()/5,2*this.getHeight()/5);

        JButton ex = new JButton();
        ex.addActionListener(this);
        ex.setText("EXIT");
        ex.setFont(new Font("Serif", Font.ITALIC, (int) screen.getHeight()/30));
        ex.setBounds(getWidth()/10,3*getHeight()/4,4*getWidth()/5,getHeight()/10);

        add(info);
        add(ex);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
