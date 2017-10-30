import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); //screen dimensions in pixels
    GameWindow() {
        this.setSize(8*screen.height/5, 7*screen.height/8);
        this.setTitle("Reversi Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
