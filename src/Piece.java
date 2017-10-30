public class Piece {
    int x;
    int y;

    static boolean checked; //Previously checked flag
    static int[][] checkList = new int[60][2]; //Array for tiles checked that turn. Max size 60 as 4 starting pieces
    static int checkCount = 0; //Counter for how many tiles have been checked that turn

    boolean controller; //Boolean Controller: True = Player, False =  Computer
    Player activePlayer;// Player object reference

    Piece(int xPos, int yPos, boolean control) {
        //x and y co-ords are passed to the piece during instantiation
        x = xPos;
        y = yPos;
        controller = control;

        if (controller == true) {
            activePlayer = Main.Player;
        } else {
            activePlayer = Main.Computer;
        }

        resetChecked();
    }

    public void flip() {
        //swaps controller of the piece, called when contained in clicked buttons "toFlip" String
        controller = !controller;

        if (controller == true) {
            activePlayer = Main.Player;
        } else {
            activePlayer = Main.Computer;
        }

        Main.GameBoard.validate();
        Main.GameBoard.repaint();
    }

    public void findButtons(boolean currentTurn) {
        //finds all the tiles around itself that don't contain a piece

        for (int i = -1;i<2;i++) {
            for (int j = -1; j<2;j++) {
                //3x3 around target co-ordinate x,y
                if (((x + i) <= 7) && ((x + i) >= 0) && ((y + j) <= 7) && ((y + j) >= 0)) {
                    //Ensures Co-ordinate to be checked is on the Gameboard
                    if (Board.findPiece((x+i),(y+j)) == null) { //if null there is no piece so could be a button
/**/                    System.out.println(" -Potential Button @ " + (x + i) + "," + (y + j));
                        checked = false; //Reset for each tile that could have been checked before
                        for (int k = 0;k<60;k+=1) {
                            if (checkList[k][0] == x+i && checkList[k][1] == y+j ) {
/**/                            System.out.println("--Checked Before");
                                checked = true;
                                break;
                            }
                        }
                        if (checked == false) {
                            //Co-ordinates not yet stored in the checkList array have not been checked yet
                            checkList[checkCount][0] = x+i;
                            checkList[checkCount][1] = y+j;
                            //add co-ordinate to array
                            checkCount += 1;
                            //move pointer to next available space

                            countFlipped((x + i), (y + j), currentTurn);
                            //find how many can be flipped by a button at co-ords
                        }
                    }
                }
            }
        }
    }
    void countFlipped(int x, int y, boolean currentTurn) {
        boolean runFlag = false;
        int count = 0;
        int lastCount = 0;
        String toFlip = "";
        String oldToFlip = "";
        //iterate through directions same as before
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (((x + i) <= 7) && ((x + i) >= 0) && ((y + j) <= 7) && ((y + j) >= 0)) {
                    //must be an adjacent piece to be valid. finds a valid direction
                    if (Board.findPiece((x + i),(y + j)) != null) {
                        if (Board.findPiece((x + i),(y + j)).controller != currentTurn) {
                            //if there is an enemy piece in that direction it is potentially valid;
                            int iter = 1;
                            while (true) {
                                if ((x + (iter*i)== 8) || (x + (iter*i) == -1) || (y + (iter*j) == 8) || (y + (iter*j) == -1)) {
                                    //if the run reaches the end of the board then the run is not valid
                                    toFlip = oldToFlip;
                                    count = lastCount;
                                    break;
                                }
                                Piece current = Board.findPiece(x + (iter * i),y + (iter * j));
                                //save current target square on board to save confusion
                                if (current == null) {
                                    //if it is null, the path is not valid as the run does not end in an allied piece
                                    toFlip = oldToFlip;
                                    count = lastCount;
                                    break;
                                } else if (current.controller == currentTurn) {
                                    //if it is an allied piece, the run is valid
                                    if (runFlag == true) {
                                        oldToFlip = toFlip;
                                        lastCount = count;
                                        runFlag = false;
                                    } else {
                                        toFlip = oldToFlip;
                                        count = lastCount;
                                    }
                                    break;
                                } else {
                                    //if it is another enemy piece, carry on checking
                                    runFlag = true;
                                    toFlip += (x+(iter* i)) + "," + (y+(iter*j)) + ",";
                                    count += 1;
                                }
                                iter += 1; //increment the loop
                            }
                        }
                    }
                }

            }
        }
        Main.turnCount += count;
        //counts total pieces this turn, if 0 then current player has no valid moves
        if (count > 0) {
            //if the position will flip at least one enemy piece it is valid so button will be created
            if (currentTurn == true) {
//            System.out.println("   -BUTTON @ " + x + "," + y);
                Main.placeButton(x, y, count, toFlip);
            } else {
/**/            System.out.println("   -Potential move @ " + x + "," + y);
                Main.Computer.addMove(x, y, count, toFlip);
            }
        }
    }

    public static void resetChecked() {
        checkCount = 0;
        for (int i = 0;i<60;i++) {
            checkList[i][0] = -1;
            checkList[i][1] = -1;
        }
    }

}