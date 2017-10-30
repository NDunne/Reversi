import javax.swing.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

/**
 * Created by User on 14/02/2016.
 */
public class delay implements ActionListener {

    Timer T;
    int i = 0;
    String[] split;

    delay(int time, String[] flipP) {
        super();
        split = flipP;
        T = new Timer(time, this);
        T.start();
    }

    public void actionPerformed(ActionEvent evt) {

        if (i < split.length) {
//        System.out.println("FLIP PIECE");
            Main.GameBoard.pieces[Integer.parseInt(split[i])][Integer.parseInt(split[i + 1])].flip();
            i += 2;
        }
        else {
            ((Timer) evt.getSource()).stop();
            //This is called at the end of every valid turn, either by the button actionListener of the Computer Turn subroutine
            Main.nextTurn();
        }
    }
}
