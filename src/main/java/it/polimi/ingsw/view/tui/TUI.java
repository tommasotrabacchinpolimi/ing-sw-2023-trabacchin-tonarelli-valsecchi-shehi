package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.LogicInterface;
import it.polimi.ingsw.view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TUI extends UI implements Runnable{
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final int DIMROW_BOOKSHELF = 6;
    private static final char EMPTY = '-';
    private final BufferedReader bufferedReader;
    private final PrintStream out = System.out;
    private TUIState state;
    private final Object lock = new Object();

    private enum TUIState{
        HOME,
        CHAT,
        OTHERS,
        LEGEND,
        END
    }

    public TUI() {
        state = TUIState.HOME;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void launch() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            welcome();
            home();
            out.println("Please, enter help to learn how to play!");
            while(true) {
                String input = bufferedReader.readLine();
                switch (input) {
                    case "help":
                        state = TUIState.LEGEND;
                        printLegendMoves();
                        break;
                    case "quit":
                        getLogicController().quitGame();
                        welcome();
                        break;
                    case "message":
                        out.println("Please, enter the following information:");
                        out.println("If you want the message to be private, enter the nickname of the player. Otherwise, enter type 'all' to send the message to all players");
                        String receivers = readLine();
                        while (!receivers.equals("all") && !getOtherPlayer().contains(receivers)) {
                            out.println("Something went wrong, the word you have typed is not right. Please enter it again...");
                            receivers = readLine();
                        }
                        out.println("Please, enter the text of the message:");
                        String text = readLine();
                        String[] rec = new String[4];
                        if (!receivers.equals("all")) {
                            rec[0] = receivers;
                        } else {
                            int i = 0;
                            for (String player : getModel().getPlayers()) {
                                rec[i] = player;
                                i++;
                            }
                        }
                        out.println("Sending the message...");
                        getLogicController().sentMessage(text, rec);
                        reset();
                        home();
                        break;
                    case "chat":
                        state = TUIState.CHAT;
                        reset();
                        home();
                        showChat();
                        break;
                    case "others":
                        state = TUIState.OTHERS;
                        reset();
                        home();
                        showBookshelves();
                        break;
                    case "exit":
                        if (state == TUIState.CHAT || state == TUIState.OTHERS || state == TUIState.LEGEND) {
                            state = TUIState.HOME;
                            reset();
                            home();
                        }
                        break;
                    case "play":
                        out.println("It's your turn to play! Please enter the coordinate of the tiles you want to take from the board, then type fine");
                        List<Coordinate> tiles = new ArrayList<>();
                        String coord = readLine();
                        while (!coord.equals("fine")) {
                            tiles.add(new Coordinate(coord.charAt(0) - 'A', coord.charAt(1)));
                        }
                        out.println("Now enter the number, between 1 and 5, of the column of your bookshelf where to insert the chosen tiles:");
                        String num = readLine();
                        while (!num.equals("1") && !num.equals("2") && !num.equals("3") && !num.equals("4") && !num.equals("5")) {
                            out.println("Something went wrong, please again the number:");
                            num = readLine();
                        }
                        int n = num.charAt(0);
                        out.println("Moving the tiles from the board to you bookshelf...");
                        getLogicController().dragTilesToBookShelf(tiles, n);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void reset(){
        out.println("\033[2J");
    }

    private String readLine() throws IOException {
        String input;
        synchronized (lock){
            input = bufferedReader.readLine();
        }
        return input;
    }

    private void welcome() throws IOException {
        synchronized (lock) {
            String choice;
            out.print("""
                          .         .                                                                                                                                   \s
                         ,8.       ,8.   `8.`8888.      ,8'              d888888o.   8 8888        8 8 8888888888   8 8888         8 8888888888    8 8888 8 8888888888  \s
                        ,888.     ,888.   `8.`8888.    ,8'             .`8888:' `88. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                       .`8888.   .`8888.   `8.`8888.  ,8'              8.`8888.   Y8 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                      ,8.`8888. ,8.`8888.   `8.`8888.,8'               `8.`8888.     8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                     ,8'8.`8888,8^8.`8888.   `8.`88888'                 `8.`8888.    8 8888        8 8 888888888888 8 8888         8 888888888888  8 8888 8 888888888888\s
                    ,8' `8.`8888' `8.`8888.   `8. 8888                   `8.`8888.   8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                   ,8'   `8.`88'   `8.`8888.   `8 8888                    `8.`8888.  8 8888888888888 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                  ,8'     `8.`'     `8.`8888.   8 8888                8b   `8.`8888. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                 ,8'       `8        `8.`8888.  8 8888                `8b.  ;8.`8888 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                ,8'         `         `8.`8888. 8 8888                 `Y8888P ,88P' 8 8888        8 8 888888888888 8 888888888888 8 8888          8 8888 8 888888888888\s
                """);
            out.println("Welcome to My Shelfie!");
            System.out.println("Choose one of the following protocols:");
            System.out.println("1) Socket");
            System.out.println("2) Remote Methode Invocation");
            String protocolChoice = bufferedReader.readLine();
            if(protocolChoice.equals("1")) {
                getLogicController().chosenSocket(0, null);
            }
            else if(protocolChoice.equals("2")) {
                getLogicController().chosenRMI(0, null);
            }

            out.println("Now insert your unique nickname: ");
            String nickname = readLine();
            out.println("Ok, now chose one of the following options: ");
            out.println("1) create a new game");
            out.println("2) join an existing game");
            out.println("3) rejoin an existing game after a disconnection");
            choice = readLine();
            switch (choice) {
                case "1" -> {
                    out.println("So you need to let us know with how many people you want to play with (it has to be a number between 1 and 3) :");
                    int numberOfPlayer = Integer.parseInt(readLine()) + 1;
                    getLogicController().createGame(nickname, numberOfPlayer);
                }
                case "2", "3" -> getLogicController().joinGame(nickname);
                default -> {
                }
            }
        }
    }

    private void home(){ //mostra le cose base
        state = TUIState.HOME;
        out.println("MY SHELFIE: HOME OF " + getModel().getThisPlayer());
        printGameState(getModel().getGameState());
        out.print("Here are all the players in the game: ");
        for(String name: getModel().getPlayers()){
            out.print(name);
            if(!Objects.equals(getModel().getPlayersState().get(name) , "CONNECTED")){
                out.print("(" + getModel().getPlayersState().get(name).toLowerCase() + ")");
            }
            out.print("   ");
        }
        out.println();
        out.println("Your points are: ");
        List<Integer> points = getModel().getPlayersPoints().get(getModel().getThisPlayer());
        out.println("Common Goal 1 = " + points.get(0) + printPointOrPoints(points.get(0)));
        out.println("Common Goal 2 = " + points.get(1) + printPointOrPoints(points.get(1)));
        out.println("End Game = " + points.get(2) + printPointOrPoints(points.get(2)));
        out.println("Personal Goal = " + points.get(3) + printPointOrPoints(points.get(3)));
        out.println("Adjacent Tiles = " + points.get(4) + printPointOrPoints(points.get(4)));
        int totalScore = getModel().getTotalPointByNickname(getModel().getThisPlayer());
        out.println("Total Points = " + totalScore + printPointOrPoints(totalScore));

        out.println();
        out.println("Common Goals are: ");
        out.println("Common Goal 1: " + getModel().getCommonGoals()[0] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(0));
        out.println("Common Goal 2: " + getModel().getCommonGoals()[1] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(1));

        printBoardBookShelfPersonalGoal(fromTileSubjectToChar(getModel().getBoard()),
                fromTileSubjectToChar(getModel().getBookShelfByNickname(getModel().getThisPlayer())),
                fromTileTypeToChar(getModel().getPersonalGoal()));

        out.println("Current Player is " + getModel().getCurrentPlayer());
    }

    private void showChat(){
        state = TUIState.CHAT;
        out.println("MY SHELFIE: CHAT");
        List<Triple<String, List<String>, String>> messages = getModel().getMessages();
        for(Triple<String, List<String>, String> message : messages){
            if(message.getSecond().contains(getModel().getThisPlayer())){
                String sender = message.getFirst();
                String priv = privateOrPublicMessage(message.getSecond());
                if(sender.equals(getModel().getThisPlayer())){
                    sender = "You";
                    if(priv.equals("(private)")){
                        priv = "(private with " + message.getSecond().get(0) + ")";
                    }
                }
                out.println(sender + priv + message.getThird());
            }
        }
        out.println("<--There aren't any more messages. If you want to return to the homepage, please type 'exit'.-->");
    }

    private void showBookshelves(){
        state = TUIState.OTHERS;
        out.println("MY SHELFIE: OTHER PLAYERS' BOOKSHELVES AND POINTS");
        List<String> nicknames = getOtherPlayer();
        List<char[][]> bookshelves = getOtherBookShelves();
        List<List<Integer>> pointPlayers = getOtherPoints();
        List<Integer> totalPoints = getOtherTotalPoints();
        printOthersBookShelf(nicknames.get(0), nicknames.get(1), nicknames.get(2), bookshelves.get(0), bookshelves.get(1), bookshelves.get(2));
        out.println();
        printOthersPoint(nicknames.get(0), nicknames.get(1), nicknames.get(2), pointPlayers.get(0), pointPlayers.get(1), pointPlayers.get(2), totalPoints.get(0), totalPoints.get(1), totalPoints.get(2));
    }

    private void showWinner(){
        // se game state è end
        out.println("MY SHELFIE: HOME OF " + getModel().getThisPlayer());
        printGameState(getModel().getGameState());
        out.println("The winner is..." + getModel().getWinnerPlayer() + "!");
        out.println("Here are the bookshelves and the point of all players:");
        //da finire

        char[][] bookshelf = fromTileSubjectToChar(getModel().getBookShelfByNickname(getModel().getThisPlayer()));
        List<Integer> pointPlayer = getModel().getPlayersPointsByNickname(getModel().getThisPlayer());
        int totalPoints = getModel().getTotalPointByNickname(getModel().getThisPlayer());

        List<String> nicknames = getOtherPlayer();
        List<char[][]> othersBookshelves = getOtherBookShelves();
        List<List<Integer>> othersPoints = getOtherPoints();
        List<Integer> othersTotalPoints = getOtherTotalPoints();


        out.println("              Your Bookshelf:              Your Points:");
        for(int i = 0; i < 6; i++){
            out.print("              ");
            printLineBookShelf(i,bookshelf);
            out.print("              ");
            if(i < 5) {
                printPoint(pointPlayer, i);
            } else {
                out.print("Total points = " + totalPoints + printPointOrPoints(totalPoints));
            }
        }

        printOthersBookShelf(nicknames.get(0), nicknames.get(1), nicknames.get(2), othersBookshelves.get(0), othersBookshelves.get(1), othersBookshelves.get(2));
        printOthersPoint(nicknames.get(0), nicknames.get(1), nicknames.get(2), othersPoints.get(0), othersPoints.get(1), othersPoints.get(2), othersTotalPoints.get(0), othersTotalPoints.get(1), othersTotalPoints.get(2));
    }

    private void printBoardBookShelfPersonalGoal(char[][] board, char[][] bookshelf, char[][] personalGoal){
        out.println("              Living Room Board:                           Your BookShelf:                   Your Personal Goal:");
        out.println( "     1   2   3   4   5   6   7   8   9  " );
        System.out.println(getDividerBoard(0) + "               " + getDividerBookShelf(0) + "               " + getDividerBookShelf(0));

        for( int i = 0; i < DIM_BOARD; ++i ){
            printLineBoard(i, board);

            out.print("               ");
            if(i < 6){
                printLineBookShelf(i,bookshelf);
                out.print("               ");
                printLineBookShelf(i,personalGoal);
            } else if (i==6){
                out.print("                                     ");
                out.print("Personal Goal Points:");
             } else if (i==7) {
                out.print("p = PLANT\t b = BOOK \t \t \t \t p 1 | 2 | 4 | 6 | 9 | 12");
            } else {
                out.print("t = TROPHY\t c = CAT \t \t \t \t p = points achieved with relative #");
            }
            out.println();

            if( i < DIM_BOARD - 1){
                if(i < 6)
                    out.println(getDividerBoard(i+1) + "               "+ getDividerBookShelf(i+1) +"               "+ getDividerBookShelf(i+1) );
                else if (i==6) {
                    out.println(getDividerBoard(i+1) + "               " + "Legend:         \t \t \t \t \t \t # 1 | 2 | 3 | 4 | 5 | 6");
                } else {
                    out.println(getDividerBoard(i + 1) + "               " + "f = FRAME\t g = GAME \t \t \t \t # = numbers of matched tiles");
                }
            }
        }

        out.println(getDividerBoard(DIM_BOARD));
    }

    private String getDividerBoard(int row) {
        switch(row) {
            case 0 -> {
                return "   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐";
            }
            case 1 -> {
                return "   ├───┼───┼───╠═══╬═══╬═══╗───┼───┼───┤";
            }
            case 2 -> {
                return "   ├───┼───╔═══╬═══╬═══╬═══╬═══╗───┼───┤";
            }
            case 3 -> {
                return "   ├───╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗";
            }
            case 4 -> {
                return "   ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣";
            }
            case 5 -> {
                return "   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝";
            }
            case 6 -> {
                return "   ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝───┤";
            }
            case 7 -> {
                return "   ├───┼───╚═══╬═══╬═══╬═══╬═══╝───┼───┤";
            }
            case 8 -> {
                return "   ├───┼───┼───╚═══╬═══╬═══╣───┼───┼───┤";
            }
            case 9 -> {
                return "   └───┴───┴───┴───╚═══╩═══╝───┴───┴───┘";
            }
            default -> {
                return null;
            }
        }
    }

    private String getDividerBookShelf(int row){
        switch (row){
            case 0 -> { return "╔═══╦═══╦═══╦═══╦═══╗"; }
            case 1, 2, 3, 4, 5 -> { return "╠═══╬═══╬═══╬═══╬═══╣"; }
            case 6 -> { return "╚═══╩═══╩═══╩═══╩═══╝"; }
            default -> { return ""; }
        }
    }

    private void printLineBoard(int r, char[][] board){
        int n; //number of print
        int c; //starting column
        int i;
        StringBuilder result = new StringBuilder("");

        result.append((char) (r + 'A')).append("  ");

        switch (r) {
            case 0, 1, 7 -> {
                result.append("│   │   │   ");
                c = 3;
            }
            case 2, 6 -> {
                result.append("│   │   ");
                c = 2;
            }
            case 3 -> {
                result.append("│   ");
                c = 1;
            }
            case 8 -> {
                result.append("│   │   │   │   ");
                c = 4;
            }
            default -> {
                result.append("");
                c = 0;
            }
        }

        switch (r) {
            case 0, 8 -> n = 2;
            case 1, 7 -> n = 3;
            case 2, 6 -> n = 5;
            case 3, 5 -> n = 8;
            default -> n = 9;
        }

        result.append( "║ " );

        for( i = 0; i < n; ++i ){
            result.append( board[r][c + i] );

            if( i < n - 1 )
                result.append( " ║ ");
        }

        result.append( " ║" );

        for( i += c ; i < DIM_BOARD; ++i ){
            result.append( "   │" );
        }

        out.print(result.toString());
    }

    private void printLineBookShelf(int row, char[][] matrix) {
        for(int j = 0; j < DIMCOL_BOOKSHELF; j++){
            if( j == 0 )
                out.print( "║ " + matrix[row][j] + " ║ ");
            else if( j < DIMCOL_BOOKSHELF - 1 )
                out.print( matrix[row][j] + " ║ " );
            else
                out.print( matrix[row][j] + " ║" );
        }
    }

    private void printOthersBookShelf(String nickname1, String nickname2, String nickname3, char[][] bookShelf1, char[][] bookShelf2, char[][] bookShelf3){
        out.println("                       " + nickname1 + ":                    " + nickname2 + ":                    " + nickname3 );
        out.println("                " + getDividerBookShelf(0) + "               " + getDividerBookShelf(0) + "               " + getDividerBookShelf(0));
        for(int i = 0; i < DIMROW_BOOKSHELF; i++){
            if(bookShelf1!=null){
                out.print("               ");
                printLineBookShelf(i, bookShelf1);
            }
            if(bookShelf2!=null){
                out.print("               ");
                printLineBookShelf(i, bookShelf2);
            }
            if(bookShelf3!=null){
                out.print("               ");
                printLineBookShelf(i, bookShelf3);
            }

            out.println();

            if(bookShelf1!=null){
                out.print("               " + getDividerBookShelf(i+1));
            }
            if(bookShelf2!=null){
                out.print("               " + getDividerBookShelf(i+1));
            }
            if(bookShelf3!=null){
                out.print("               " + getDividerBookShelf(i+1));
            }
            out.println();
        }
    }

    private void printOthersPoint(String nickname1, String nickname2, String nickname3, List<Integer> pointPlayer1, List<Integer> pointPlayer2, List<Integer> pointPlayer3, int total1, int total2, int total3){
        out.print("                 ");
        out.println(nickname1 + "        " + nickname2 + "        " + nickname3);
        //inizio
        out.print("Common Goal 1:  ");
        printPoint(pointPlayer1, 0);
        printPoint(pointPlayer2, 0);
        printPoint(pointPlayer3, 0);
        out.print("Common Goal 2:  ");
        printPoint(pointPlayer1, 1);
        printPoint(pointPlayer2, 1);
        printPoint(pointPlayer3, 1);
        out.print("End Game:       ");
        printPoint(pointPlayer1, 2);
        printPoint(pointPlayer2, 2);
        printPoint(pointPlayer3, 2);
        out.print("Personal goal:  ");
        printPoint(pointPlayer1, 3);
        printPoint(pointPlayer2, 3);
        printPoint(pointPlayer3, 3);
        out.print("Adjacent Tiles: ");
        printPoint(pointPlayer1, 4);
        printPoint(pointPlayer2, 4);
        printPoint(pointPlayer3, 4);
        out.println();
        out.println("Total points:   ");
        out.print(total1+ " " + printPointOrPoints(total1) + "        ");
        out.print(total2+ " " + printPointOrPoints(total2) + "        ");
        out.print(total3+ " " + printPointOrPoints(total3) + "        ");
        out.println();
    }

    private char[][] fromTileSubjectToChar(TileSubject[][] matrix){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                result[i][j] = getCharFromTileType(matrix[i][j].getTileType());
            }
        }
        return result;
    }

    private char[][] fromTileTypeToChar(TileType[][] matrix){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                result[i][j] = getCharFromTileType(matrix[i][j]);
            }
        }
        return result;
    }

    private char getCharFromTileType(TileType tileType){
        switch (tileType){
            case CAT -> { return 'c'; }
            case PLANT -> { return 'p'; }
            case FRAME -> { return 'f'; }
            case BOOK -> { return 'b'; }
            case TROPHY -> { return 't'; }
            case GAME -> { return 'g'; }
            default -> { return EMPTY; }
        }
    }

    private void printGameState(String gameState){
        switch(gameState){
            case "INIT" -> {
                out.println("The game is starting, please wait until the other players will join the game.");
            }
            case "MID" -> {
                out.println("The game is in progress.");
            }
            case "SUSPENDED" -> {
                out.println("The game is suspended because there is nobody left to play besides you.");
            }
            case "FINAL" -> {
                out.println("The game is ending, this is the last round of turns.");
            }
            case "END" -> {
                out.println("The game is over!");
            }
            default -> {
                out.println();
            }
        }
    }

    private String printPointOrPoints(int point){
        if(point == 0) return "point";
        if(point == -1) return "";
        return "points";
    }

    private void printPoint(List<Integer> point, int index){
        if(point != null)
            out.print(point.get(index) + " " + printPointOrPoints(point.get(index)) + "        ");
    }

    private String privateOrPublicMessage(List<String> receivers){
        if(receivers.size() == 1) return "(private)";
        return "(public)";
    }

    private List<String> getOtherPlayer(){
        List<String> nicknames = new ArrayList<>(getModel().getPlayers().stream().filter(n -> !n.equals(getModel().getThisPlayer())).toList());
        if(nicknames.size() <= 2){
            while(nicknames.size()-3 < 0){
                nicknames.add("");
            }
        }
        return nicknames;
    }

    private List<char[][]> getOtherBookShelves(){
        List<String> nicknames = getOtherPlayer();
        List<char[][]> bookshelves = new ArrayList<>();
        char[][] bookShelf1 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(0)));
        char[][] bookShelf2 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(1)));
        char[][] bookShelf3 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(2)));
        bookshelves.add(bookShelf1);
        bookshelves.add(bookShelf2);
        bookshelves.add(bookShelf3);
        return bookshelves;
    }

    private List<List<Integer>> getOtherPoints(){
        List<String> nicknames = getOtherPlayer();
        List<List<Integer>> points = new ArrayList<>();
        List<Integer> pointPlayer1 = getModel().getPlayersPoints().get(nicknames.get(0));
        List<Integer> pointPlayer2 = getModel().getPlayersPoints().get(nicknames.get(1));
        List<Integer> pointPlayer3 = getModel().getPlayersPoints().get(nicknames.get(2));
        points.add(pointPlayer1);
        points.add(pointPlayer2);
        points.add(pointPlayer3);
        return points;
    }

    private List<Integer> getOtherTotalPoints(){
        List<String> nicknames = getOtherPlayer();
        List<Integer> totals = new ArrayList<>();
        totals.add(getModel().getTotalPointByNickname(nicknames.get(0)));
        totals.add(getModel().getTotalPointByNickname(nicknames.get(1)));
        totals.add(getModel().getTotalPointByNickname(nicknames.get(2)));
        return totals;
    }

    private void printLegendMoves(){
        out.println("Legend:");
        out.println("Here are the actions you can make during the game:");
        out.println(" TYPE        DESCRIPTION");
        out.println(" play        use it during your turn in order to move tiles from the board to your bookshelf.");
        out.println(" message     use it to send messages to other players.");
        out.println(" chat        use it to see the chat.");
        out.println(" others      use it to see other players' bookshelves and points.");
        out.println(" exit        use this to leave chat, others visual or help legend.");
        out.println(" quit        use it to leave the game, you will be directed to the welcome page so you can play again.");
        out.println("<--- Please enter exit to return to the homepage. --->");
    }

}