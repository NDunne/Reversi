import java.awt.*;
import java.util.Random;

public class Computer extends Player {

    static String dif = "Hard";

    static int[] posX;
    static int[] posY;
    static int[] counts;
    float[] c;

    static String[] flips;

    static int p;

    Computer(boolean bool, String name, String colStr) {
        super(bool, name, colStr);
        panel.setBounds(3 * Main.getWindow().getWidth() / 4, 0, Main.getWindow().getWidth() / 4, Main.getWindow().getHeight());
        panel.setBackground(new Color(0, 0, 150));
    }

    public static void setDif(String newDif) {
        dif = newDif;
    }

    public static void newTurn() {
        posX = new int[60];
        posY = new int[60];
        counts = new int[60];
        flips = new String[60];

        p = 0;
    }

    public void addMove(int x, int y, int flipCount, String toFlip) {
        posX[p] = x;
        posY[p] = y;
        counts[p] = flipCount;
        flips[p] = toFlip;

        p += 1;
    }

    int hardTurn() {
        int best = 0;
        for (int i = 1; i < p; i++) {
            if (counts[i] > counts[best]) {
                best = i;
            }
        }
        return best;
    }

    int medTurn() {
        int total = 0;
        c = new float[counts.length];
        for (int i = 0;i<counts.length;i++) {
            System.out.println("Total: " +total + ", this: " + counts[i]);
            total += counts[i];
            //total flips of all moves, allowing normalisation
        }
/**/    System.out.println("Total counts: " + total);
        Random r = new Random();
        float f = r.nextFloat(); //random float between 0.0 and 1.0
/**/    System.out.println("Random Float: " + f);

        for (int i = 0; i < counts.length; i++) {
            float u = 0;
            for (int j = i; j>=0; j=j-1) {
            u += counts[j]; //total of all counts previous to this one
            }
/**/        System.out.println("Cumulative value for i=" + i + " : " + u/total);
            c[i] = u/total; //c for cumulative, divided by total to give max of 1.0
            if(i == 0) {
                if (f <= c[i]) {
                return 0;
                //catch for [-1] out of bounds
                }
            } else if (f > c[i - 1] && f <= c[i]) {
                return i;
            }
        }
        return -1;
        //should be impossible to reach here. -1 throws ArrayOutofBounds error.
        }

        int easyTurn() {
            Random r  = new Random();
            return r.nextInt(p);
        }

    public void takeTurn() {
        if (p > 0) {
            int point;
//        System.out.println("DIFFICULTY: " +  dif);
            if (dif.equals("hard")) {
//            System.out.println("hardTurn()");
                point = hardTurn();
            } else if (dif.equals("Medium")) {
//            System.out.println("medTurn()");
                point = medTurn();
            } else {
//            System.out.println("easyTurn()");
                point = easyTurn();
            }
//        System.out.println("Index chosen: " + point + ", flipping: " + counts[point]);
            if (counts[point] != 0) {
                Board.newPiece(posX[point], posY[point], false);
                new delay(1500 / (flips[point].split(",")).length, flips[point].split(","));
            }
        rem -= 1;
        }
    }
}