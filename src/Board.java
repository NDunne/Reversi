import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Board extends JPanel {
    static Piece[][] pieces = new Piece[8][8];

    static String artStyle = "Traditional";
    static int[][][] coords = new int[8][8][2];
    static Image unscaled;

    static Image boardImage;
    static Image PlayerPiece;
    static Image ComputerPiece;
    static Image Empty;

    Board() {
        //Generates an array containing the pixel positions to draw pieces based on integer array positions
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                coords[i][j][0] = (i * Main.window.getWidth()/16) + 10;
                coords[i][j][1] = (j * Main.window.getWidth()/16) + 10;
            }
        }
        RP();
     }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//    System.out.println("REPAINT");
        g.drawImage(boardImage, 0, 0, null);
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                if (pieces[a][b] != null) {
                    if (pieces[a][b].controller == Main.Player.myBool) {
                        g.drawImage(PlayerPiece, coords[a][b][0], coords[a][b][1], null);
                    } else {
                        g.drawImage(ComputerPiece, coords[a][b][0], coords[a][b][1], null);
                    }

                }
            }

        }
    }

    public static void newPiece(int x, int y, boolean currentTurn) {
/**/    System.out.println("New Piece @ " + x + "," + y);
        pieces[x][y] = new Piece(x, y, currentTurn);
        Main.GameBoard.validate();
        Main.GameBoard.repaint();
    }

    public static Image readImageFile(String fileName) {
        try {
            unscaled = ImageIO.read(new File("../Assets/Assets_"+ artStyle.substring(0,2)+ "/" + fileName));
        } catch (Exception e) {
/**/        System.out.println("Unable to find file " + fileName);
            return null;
        }
        if (fileName.equals("GameBoardIMG.gif")) {
            return unscaled.getScaledInstance(Main.GameBoard.getWidth(), Main.GameBoard.getHeight(), Image.SCALE_SMOOTH);
        } else if (fileName.equals("WhitePanel.gif") || fileName.equals("BlackPanel.gif")) {
            return unscaled.getScaledInstance(Main.GameBoard.getWidth() / 6, Main.GameBoard.getHeight() / 6, Image.SCALE_SMOOTH);
        } else {
            return unscaled.getScaledInstance(Main.GameBoard.getWidth()/8 -20, Main.GameBoard.getHeight()/8 - 20, Image.SCALE_SMOOTH);
        }

    }

    public static void loadimages() {
        boardImage = readImageFile("GameBoardIMG.gif");
        PlayerPiece = readImageFile(Main.Player.colourString + "Counter.gif");
        ComputerPiece = readImageFile(Main.Computer.colourString + "Counter.gif");
        Empty = readImageFile("Empty.gif");
    }

    public static void setArtStyle(String newStyle) {
        artStyle = newStyle;
    }

    public static void RP() {pieces = new Piece[8][8];}

    public static Piece findPiece(int x, int y) { return pieces[x][y];}

 }