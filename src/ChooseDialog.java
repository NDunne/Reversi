import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseDialog extends JDialog implements ActionListener {

    Image playerPiece; //Image displayed in starting dialouge
    Dimension screen = Main.screen;
    public ChooseDialog(Frame window) {
        super(window, "player");
        this.setLayout(null);
        this.setBounds((int) (screen.getWidth()/3), (int)(3*screen.getHeight()/10), (int) (screen.getWidth()/3), (int) (7*screen.getHeight()/16));

        int rn = (int) Math.round(Math.random());
        if (rn == 0) {
            playerPiece = Board.readImageFile("WhitePanel.gif");
            Main.currentTurn = false;

            Main.Player = new Player(true, "Player","White");
            Main.Computer = new Computer(false, "Computer","Black");
        } else {
            playerPiece = Board.readImageFile("BlackPanel.gif");
            Main.currentTurn = true;

            Main.Player = new Player(true, "Player","Black");
            Main.Computer = new Computer(false, "Computer","White");
        }
        JLabel label = new JLabel ("You will play as:");
        label.setFont(new Font("Serif", Font.BOLD, (int) screen.getHeight() / 16));
        label.setBounds(this.getWidth()/10,0,this.getWidth(),3*this.getHeight()/10);

        JLabel pieceImg = new JLabel();
        pieceImg.setIcon(new ImageIcon(playerPiece));
        pieceImg.setBounds(this.getWidth()/2 - Main.GameBoard.getWidth()/8,this.getHeight()/2 - Main.GameBoard.getWidth()/8, Main.GameBoard.getWidth()/4, Main.GameBoard.getWidth()/4);

        JButton exit = new JButton();
        exit.setBounds(0,4*this.getHeight()/5, this.getWidth(), this.getHeight()/10);
        exit.setText("CONFIRM");
        exit.setFont(new Font("Serif", Font.ITALIC, 3*exit.getHeight()/4));
        exit.addActionListener(this);

        this.add(label);
        this.add(pieceImg);
        this.add(exit);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
        Main.nextTurn();
    }
}
