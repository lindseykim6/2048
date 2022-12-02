import java.awt.Point;
/**
 * 2048 game class
 *
 * @author Lindsey Kim
 */
public class TwentyFortyEight {
    public enum GameState {
        WIN, LOSE, IN_PROGRESS // if the game was won, lost, or if it's still in progress
    }
    private final int[][] boardStatus; // holds the current status of the game
    private GameState gameState;

    public TwentyFortyEight() {
        boardStatus = new int[4][4]; // a grid of size 4x4
        for (int row = 0; row < boardStatus.length; row++) {
            for (int col = 0; col < boardStatus[0].length; col++) {
                boardStatus[row][col] = 0; // initializes the board to 0
            }
        }
        gameState = GameState.IN_PROGRESS; // starts the game
    }

    /**
     * Starts the game by adding a random tile
     */
    public void startGame() {
        addRandom(); // adds random tile to the game
    }

    /**
     * Returns the number on the given position
     */
    public int getNumber(int x, int y) {
        return boardStatus[y][x]; // gets the number in position x, y on the board
    }

    /**
     * Prints the board as a string
     */
    public void boardStatusToString() {
        for (int[] status : boardStatus) { // for each row
            for (int tileX = 0; tileX < boardStatus[0].length; tileX++) { // for each tile
                System.out.print(status[tileX] + " "); //  print out the number in the tile
            }
            System.out.println();
        }
    }

    /**
     * Handles the left swipe in the game
     */
    public void swipeLeft() {
//        System.out.println("LEFT");
        boolean swiped = false; // checks to see if a swipe occurred or not
        for (int tileY = 0; tileY < boardStatus.length; tileY++) { // iterates through all the tiles in the board
            for (int tileX = 0; tileX < boardStatus[0].length; tileX++) {
                System.out.println(tileX);
                int number = boardStatus[tileY][tileX];
                if (number != 0) { // if the tile isn't empty
                    int original = tileX; // initializes  temporary variable to hold the tile's position
                    // move tiles while there is space for the tile to move
                    while ((original > 0) && (boardStatus[tileY][original - 1] == 0)) {
                        boardStatus[tileY][original - 1] = number;
                        boardStatus[tileY][original] = 0;
                        original = original - 1;
                        swiped = true;
                    }
                    // combines tiles if they are same number
                    if (original > 0 && boardStatus[tileY][original - 1] == number) {
                        boardStatus[tileY][original - 1] = (number + number);
                        boardStatus[tileY][original] = 0;
                        // updates the rest of the tiles in the row so that double combines don't happen
                        while(tileX < boardStatus.length) {
                            original = tileX;
                            number = boardStatus[tileY][original];
                            while ((original > 0) && (boardStatus[tileY][original - 1] == 0)) {
                                boardStatus[tileY][original - 1] = number;
                                boardStatus[tileY][original] = 0;
                                original = original - 1;
                            }
                            tileX++;
                            System.out.println(tileX);
                        }
                        swiped = true; // set swiped as true
                    }
                }
            }
        }
        if(swiped) {
            addRandom(); // add a random tile if a valid swipe occurred
        }
        checkLoss(); // check if game was lost
        checkWin(); // check if game was won
    }

    /**
     * Handles the right swipe in the game
     */
    public void swipeRight() {
//        System.out.println("RIGHT");
        boolean swiped = false; // checks to see if a swipe occurred or not
        for (int tileY = 0; tileY < boardStatus.length; tileY++) { // iterates through all the tiles in the board
            for (int tileX = boardStatus[0].length-1; tileX >= 0; tileX--) {
                int number = boardStatus[tileY][tileX];
                if (number != 0) { // if the tile isn't empty
                    int original = tileX; // initializes  temporary variable to hold the tile's position
                    // move tiles while there is space for the tile to move
                    while ((original < boardStatus[0].length-1) && (boardStatus[tileY][original + 1] == 0)) {
                        swiped=true;
                        boardStatus[tileY][original + 1] = number;
                        boardStatus[tileY][original] = 0;
                        original = original + 1;
                    }
                    // combines tiles if they are same number
                    if ((original < boardStatus[0].length-1) && boardStatus[tileY][original + 1] == number) {
                        boardStatus[tileY][original + 1] = (number + number);
                        boardStatus[tileY][original] = 0;
                        // updates the rest of the tiles in the row so that double combines don't happen
                        while(tileX > 0) {
                            original=tileX-1;
                            number = boardStatus[tileY][original];
                            while ((original < boardStatus[0].length - 1) && (boardStatus[tileY][original + 1] == 0)) {
                                boardStatus[tileY][original + 1] = number;
                                boardStatus[tileY][original] = 0;
                                original = original + 1;
                            }
                            tileX--;
                        }
                        swiped=true; // set swiped as true
                    }

                }
            }
        }
        if(swiped) {
            addRandom(); // add a random tile if a valid swipe occurred
        }
        checkLoss(); // check if game was lost
        checkWin(); // check if game was won
    }

    /**
     * Handles the down swipe in the game
     */
    public void swipeDown() {
//        System.out.println("DOWN");
        boolean swiped = false; // checks to see if a swipe occurred or not
        for (int tileX = 0; tileX < boardStatus[0].length; tileX++) { // iterates through all the tiles in the board
            for (int tileY = boardStatus.length-1; tileY >= 0; tileY--) {
                int number = boardStatus[tileY][tileX];
                if (number != 0) { // if the tile isn't empty
                    int original = tileY; // initializes  temporary variable to hold the tile's position
                    // move tiles while there is space for the tile to move
                    while ((original < boardStatus.length-1) && (boardStatus[original + 1][tileX] == 0)) {
                        swiped = true;
                        boardStatus[original + 1][tileX] = number;
                        boardStatus[original][tileX] = 0;
                        original = original + 1;
                    }
                    // combines tiles if they are same number
                    if ((original < boardStatus.length-1) && boardStatus[original + 1][tileX] == number) {
                        boardStatus[original + 1][tileX] = (number + number);
                        boardStatus[original][tileX] = 0;
                        // updates the rest of the tiles in the row so that double combines don't happen
                        while(tileY>0) {
                            original = tileY-1;
                            number = boardStatus[original][tileX];
                            while ((original < boardStatus.length - 1) && (boardStatus[original + 1][tileX] == 0)) {
                                boardStatus[original + 1][tileX] = number;
                                boardStatus[original][tileX] = 0;
                                original = original + 1;
                            }
                            tileY--;
                        }
                        swiped=true; // set swiped as true
                    }
                }
            }
        }
        if(swiped) {
            addRandom(); // add a random tile if a valid swipe occurred
        }
        checkLoss(); // check if game was lost
        checkWin(); // check if game was won
    }

    /**
     * Handles the up swipe in the game
     */
    public void swipeUp() {
//        System.out.println("UP");
        boolean swiped = false; // checks to see if a swipe occurred or not
        for (int tileX = 0; tileX < boardStatus[0].length; tileX++) { // iterates through all the tiles in the board
            for (int tileY = 0; tileY < boardStatus.length; tileY++) {
                int number = boardStatus[tileY][tileX];
                if (number != 0) { // if the tile isn't empty
                    int original = tileY;// initializes  temporary variable to hold the tile's position
                    // move tiles while there is space for the tile to move
                    while ((original > 0) && (boardStatus[original - 1][tileX] == 0)) {
                        swiped = true;
                        boardStatus[original - 1][tileX] = number;
                        boardStatus[original][tileX] = 0;
                        original = original - 1;
                    }
                    // combines tiles if they are same number
                    if ((original > 0) && boardStatus[original - 1][tileX] == number) {
                        boardStatus[original - 1][tileX] = (number + number);
                        boardStatus[original][tileX] = 0;
                        // updates the rest of the tiles in the row so that double combines don't happen
                        while(tileY< boardStatus.length-1) {
                            original = tileY+1;
                            number = boardStatus[original][tileX];
                            while ((original > 0) && (boardStatus[tileY][original - 1] == 0)) {
                                boardStatus[tileY][original - 1] = number;
                                boardStatus[tileY][original] = 0;
                                original = original - 1;
                            }
                            tileY++;
                        }
                        swiped = true; // set swiped as true
                    }
                }
            }
        }
        if(swiped) {
            addRandom(); // add a random tile if a valid swipe occurred
        }
        checkLoss(); // check if game was lost
        checkWin(); // check if game was won
    }

    /**
     * Adds a 2 tile to a random empty spot
     */
    public void addRandom() {
        Point[] emptyTiles = new Point[16]; // stores all the empty tiles
        int pos = 0;
        for (int tileY = 0; tileY < boardStatus.length; tileY++) { //iterates through all tiles
            for (int tileX = 0; tileX < boardStatus[0].length; tileX++) {
                if (boardStatus[tileY][tileX] == 0) { // adds empty spots to array
                    emptyTiles[pos] = new Point(tileX, tileY);
                    pos++;
                }
            }
        }
        if(pos!=0) { // if there is an empty spot
            int randomIndex = (int) Math.floor(Math.random() * pos); // get a random location
            Point randomPoint = emptyTiles[randomIndex];
            boardStatus[(int) randomPoint.getY()][(int) randomPoint.getX()] = 2; //set this location to 2
        }
    }

    /**
     * Checks for wins by finding if a 2048 tile exists
     */
    public void checkWin() {
        //iterates through all the tiles
        for (int[] status : boardStatus) {
            for (int tileX = 0; tileX < boardStatus[0].length; tileX++) {
                if (status[tileX] == 2048) { //if there is a 2048 tile then set the game state as win
                    gameState = GameState.WIN;
                    break;
                }
            }
        }
    }

    /**
     * Checks for losses
     */
    public void checkLoss() {
        // counts all the empty tiles
        int zeroCount = 0;
        for (int[] status : boardStatus) {
            for (int tileX = 0; tileX < boardStatus[0].length; tileX++) {
                if (status[tileX] == 0) {
                    zeroCount++;
                }
            }
        }
        if(zeroCount==0) { //if there are no empty tiles
            // not loss if the number on the tile equals the number on the tiles adjacent to it (inner tiles)
            for (int tileX = 1; tileX < boardStatus[0].length-1; tileX++) {
                for (int tileY = 1; tileY < boardStatus.length-1; tileY++) {
                    if(boardStatus[tileY][tileX] == boardStatus[tileY][tileX-1] ||
                            boardStatus[tileY][tileX] == boardStatus[tileY][tileX+1] ||
                            boardStatus[tileY][tileX] == boardStatus[tileY-1][tileX] ||
                            boardStatus[tileY][tileX] == boardStatus[tileY+1][tileX]) {
                        return;
                    }
                }
            }

            // not loss if the number on the tile equals the number on the tiles adjacent to it (top and bottom row tiles)
            for(int tileX =1;tileX < boardStatus[0].length-1; tileX++) {
                //top row
                if(boardStatus[0][tileX] == boardStatus[0][tileX-1] ||boardStatus[0][tileX] == boardStatus[0][tileX+1] ||
                        boardStatus[0][tileX] == boardStatus[1][tileX]) {
                    return;
                }
                //bottom row
                if(boardStatus[boardStatus.length-1][tileX] == boardStatus[boardStatus.length-1][tileX-1] ||
                boardStatus[boardStatus.length-1][tileX] == boardStatus[boardStatus.length-1][tileX+1] ||
                        boardStatus[boardStatus.length-1][tileX] == boardStatus[boardStatus.length-2][tileX]) {
                    return;
                }
            }

            // not loss if the number on the tile equals the number on the tiles adjacent to it (left and right column tiles)
            for (int tileY = 1; tileY < boardStatus.length-1; tileY++) {
                //left column
                if (boardStatus[tileY][0] == boardStatus[tileY - 1][0] || boardStatus[tileY][0] == boardStatus[tileY + 1][0] ||
                        boardStatus[tileY][0] == boardStatus[tileY][1]) {
                    return;
                }
                //right column
                if (boardStatus[tileY][boardStatus[0].length - 1] == boardStatus[tileY - 1][boardStatus[0].length - 1] ||
                        boardStatus[tileY][boardStatus[0].length - 1] == boardStatus[tileY + 1][boardStatus[0].length - 1] ||
                        boardStatus[tileY][boardStatus[0].length - 1] == boardStatus[tileY][boardStatus[0].length - 2]) {
                    return;
                }
            }
            //if corner pieces can be combined, then not lost
            if(boardStatus[0][0] == boardStatus[1][0] ||boardStatus[0][0] == boardStatus[0][1]) {
                return;
            }
            if(boardStatus[boardStatus.length-1][0] == boardStatus[boardStatus.length-1][1] ||boardStatus[boardStatus.length-1][0]
                    == boardStatus[boardStatus.length-2][0]) {
                return;
            }
            if(boardStatus[0][boardStatus[0].length-1] == boardStatus[0][boardStatus[0].length-2] ||
                    boardStatus[0][boardStatus.length-1]  == boardStatus[1][boardStatus.length-1]) {
                return;
            }
            if(boardStatus[boardStatus.length-1][boardStatus[0].length-1] == boardStatus[boardStatus.length-1][boardStatus[0].length-2] ||
                    boardStatus[boardStatus.length-1][boardStatus.length-1]  == boardStatus[boardStatus.length-2][boardStatus.length-1]) {
                return;
            }
            //otherwise game is lost
            gameState=GameState.LOSE;
            System.out.println("LOST");
        }
    }

    /**
     * Returns the state of the game
     */
    public GameState getGameState() {
        return gameState; // returns the game state
    }

}