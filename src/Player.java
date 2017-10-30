import javax.swing.*;
import java.awt.*;

public class Player {

    public Boolean myBool;

    public String playerName;
    public String colourString;

    public int myCount = 2;
    JPanel panel = new JPanel();
    JLabel playerLabel;
    JLabel panOnBoard;
    JLabel pieceLabel;

    public static int rem = 60;

    public Player(boolean bool, String name, String colStr) {
/**/    System.out.println("new Player: " + name);
        myBool = bool;
        playerName = name;
        colourString = colStr;

        panel.setLayout(null);
        panel.setBounds(0,0,Main.getWindow().getWidth()/4, Main.getWindow().getHeight());
        panel.setBackground(new Color(150,0,0));

        playerLabel = new JLabel(playerName);
        playerLabel.setFont(new Font("Courier New", Font.BOLD, panel.getHeight()/25));
        playerLabel.setForeground(new Color(255, 255, 255));
        playerLabel.setBounds(10,15,panel.getWidth(),panel.getHeight()/24);

        panel.add(playerLabel);

        panOnBoard = new JLabel("02");
        panOnBoard.setFont(new Font("Courier New", Font.PLAIN, panel.getHeight()/5));
        panOnBoard.setForeground(new Color(255, 255, 255));
        panOnBoard.setBounds(30,250,panel.getWidth(),panel.getHeight()/4);

        panel.add(panOnBoard);

        pieceLabel = new JLabel();
        pieceLabel.setIcon(new ImageIcon(Board.readImageFile(colourString + "Panel.gif")));

        pieceLabel.setBounds(5*panel.getWidth()/16,7*panel.getHeight()/16, Main.GameBoard.getWidth()/6, Main.GameBoard.getHeight()/6);

        panel.add(pieceLabel);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void updatePanel() {
        myCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Board.pieces[i][j] != null) {
                    if (Board.pieces[i][j].controller == myBool) {
                        myCount += 1;
                    }
                }
            }
        }
        if (myCount <=9) {
            panOnBoard.setText("0" + Integer.toString(myCount));
        } else {
            panOnBoard.setText(Integer.toString(myCount));
        }
    }
}