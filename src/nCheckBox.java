import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class nCheckBox extends JCheckBox implements ActionListener {

    nCheckBox(String text) {
        super(" " + text);
        addActionListener(this);
        try {
            setIcon(new ImageIcon(ImageIO.read(new File("Assets/Untick.png")).getScaledInstance((Main.Options.getWidth()/40),(Main.Options.getWidth()/40), Image.SCALE_SMOOTH)));
            setSelectedIcon(new ImageIcon(ImageIO.read(new File("Assets/tick.png")).getScaledInstance((Main.Options.getWidth()/40),(Main.Options.getWidth()/40), Image.SCALE_SMOOTH)));
        } catch (IOException e){
/**/        System.out.println("IMAGE LOAD FAILED");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Main.setShowTips(isSelected());
    }
}
