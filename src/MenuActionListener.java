import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuActionListener implements ActionListener{
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JButton) {
            System.out.println(((JButton) e.getSource()).getText());
            if ((((JButton) e.getSource()).getText()) == "New Game") {
                Main.setLoadFlag(false);
                Main.MainMenu.dispose();
                Main.createAndShowGameBoard();
            } else if ((((JButton) e.getSource()).getText()) == "Load Game") {
                Main.setLoadFlag(true);
                Main.MainMenu.dispose();
                Main.createAndShowGameBoard();
            } else if ((((JButton) e.getSource()).getText()) == "Options") {
                Main.MainMenu.dispose();
                Main.createAndShowOptions();
            } else if ((((JButton) e.getSource()).getText()) == "Exit") {
                Main.getWindow().dispose();
                Main.createAndShowMainMenu();
            } else {
                Main.Options.dispose();
                Main.createAndShowMainMenu();
//            System.out.println("BACK TO MAIN");
            }
        } else if (e.getSource() instanceof JComboBox){
            Computer.setDif(((JComboBox) e.getSource()).getSelectedItem().toString());
        } else if (e.getSource() instanceof JRadioButton) {
            Board.setArtStyle(((JRadioButton) e.getSource()).getText());
        } else {
/**/        System.out.println("Item not supported yet");
        }
    }
}
