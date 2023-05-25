package it.polimi.ingsw.view.tui;

import com.diogonunes.jcolor.Attribute;
import it.polimi.ingsw.controller.exceptions.AlreadyInGameException;
import it.polimi.ingsw.controller.exceptions.AlreadyTakenNicknameException;
import it.polimi.ingsw.model.BoardSquareType;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.diogonunes.jcolor.Ansi.colorize;
import static it.polimi.ingsw.model.BoardSquareType.*;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;

public class TUI extends UI implements Runnable{
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final int DIMROW_BOOKSHELF = 6;
    private static final char EMPTY = '-';
    private final BufferedReader bufferedReader;
    private final PrintStream out = System.out;
    private TUIState state;
    public static final BoardSquareType[][] INIT_MATRIX = {
            {null, null, null, THREE_DOTS, FOUR_DOTS, null, null, null, null},
            {null, null, null, NO_DOTS, NO_DOTS, FOUR_DOTS, null, null, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS},
            {FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS},
            {THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, null, null, FOUR_DOTS, NO_DOTS, NO_DOTS, null, null, null},
            {null, null, null, null, FOUR_DOTS, THREE_DOTS, null, null, null}
    };

    private final Lock lock = new ReentrantLock();


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
    public void launchUI() {
        new Thread(this).start();
    }

    @Override
    public void onNewMessage(String sender) {
        lock.lock();
        if(state != TUIState.CHAT) {
            if (!sender.equals(getModel().getThisPlayer())) {
                out.println("New Message from " + sender);
            }
        } else {
            refresh();
        }
        lock.unlock();
    }

    @Override
    public void onCurrentPlayerChanged(String newCurrentPlayer) {
        lock.lock();
        if(state != TUIState.HOME) {
            out.println("There is a new current player, please refresh the page...");
        } else {
            refresh();
        }
        lock.unlock();
    }

    @Override
    public void run() {
        try {
            lock.lock();
            welcome(true);
            Thread.sleep(1000);
            if(getModel().getPlayers().isEmpty()){
                lock.unlock();
                this.run();
            }
            home();
            while(true) {
                lock.unlock();
                while(!bufferedReader.ready()) {
                    Thread.sleep(50);
                }
                String input = bufferedReader.readLine();
                lock.lock();
                switch (input) {
                    case "help":
                        state = TUIState.LEGEND;
                        printLegendMoves();
                        break;
                    case "quit":
                        getLogicController().quitGame();
                        welcome(false);
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
                        refresh();
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
                        out.println("It's your turn to play! Please enter the coordinate of the tiles you want to take from the board, then type " + colorize("end", Attribute.BOLD()));
                        List<Coordinate> tiles = new ArrayList<>();
                        String coord = "";
                        while (true) {
                            coord = readLine();
                            if(coord.equals("end")){
                                break;
                            }
                            tiles.add(new Coordinate(coord.charAt(0) - 'A', Integer.parseInt(String.valueOf(coord.charAt(1)))-1 ));
                        }
                        out.println("Now enter the number, between 1 and 5, of the column of your bookshelf where to insert the chosen tiles:");
                        String num = readLine();
                        while (!num.equals("1") && !num.equals("2") && !num.equals("3") && !num.equals("4") && !num.equals("5")) {
                            out.println("Something went wrong, please enter again the number:");
                            num = readLine();
                        }
                        int n = Integer.parseInt(String.valueOf(num.charAt(0)))-1;
                        getLogicController().dragTilesToBookShelf(tiles, n);
                        out.println("Moving...");
                        refresh();
                        break;
                    default :
                        refresh();
                }
            }
        } catch (IOException | NotBoundException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void refresh(){
        reset();
        if(state.equals(TUIState.CHAT)){
            showChat();
        } else if (state.equals(TUIState.OTHERS)) {
            showBookshelves();
        } else if (state.equals(TUIState.LEGEND)) {
            printLegendMoves();
        } else if (state.equals(TUIState.END)) {
            showWinner();
        } else if (state.equals(TUIState.HOME)) {
            home();
        }
    }

    public void reset(){
        out.println("\033[2J");
    }

    private String readLine() throws IOException {
        String input;
        input = bufferedReader.readLine();
        return input;
    }

    public void onException(){
        lock.lock();
        out.println(getModel().getException());
        lock.unlock();
    }

    @Override
    public void onGameStateChanged() {
    }

    private void welcome(boolean fromTheBeginning) throws IOException, NotBoundException, ClassNotFoundException {
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
        if(fromTheBeginning) {
            System.out.println("Choose one of the following protocols:");
            System.out.println("1) Socket");
            System.out.println("2) Remote Methode Invocation");
            String protocolChoice = bufferedReader.readLine();
            System.out.println("Please enter the server address: ");
            String host = bufferedReader.readLine();
            System.out.println("Now enter the port number");
            int port = Integer.parseInt(bufferedReader.readLine());
            if(protocolChoice.equals("1")) {
                getLogicController().chosenSocket(port, host);
            }
            else if(protocolChoice.equals("2")) {
                getLogicController().chosenRMI(port, host);
            }
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
                out.println("So you need to choose how many people there will be in the game (it has to be a number between 2 and 4, including you) :");
                int numberOfPlayer = Integer.parseInt(readLine());
                getLogicController().createGame(nickname, numberOfPlayer);
            }
            case "2", "3" -> getLogicController().joinGame(nickname);
            default -> {
            }
        }
    }

    private void home(){ //mostra le cose base
        state = TUIState.HOME;
        out.print(colorize("                                                           ", Attribute.GREEN_BACK()));
        out.print(colorize("MY SHELFIE: HOME OF " + getModel().getThisPlayer(), Attribute.GREEN_BACK()));
        out.println(colorize("                                                           ", Attribute.GREEN_BACK()));

        printGameState(getModel().getGameState());

        out.print("Here are all the players in the game: ");
        for(String name: getModel().getPlayers()){
            if(getModel().getPlayersState().get(name)==null) {
                continue;
            }
            out.print(name);
            if(!Objects.equals(getModel().getPlayersState().get(name) , "CONNECTED")){
                out.print("(" + colorize(getModel().getPlayersState().get(name).toLowerCase(), Attribute.ITALIC()) + ")");
            }
            out.print("   ");
        }
        out.println();

        List<Integer> points = getModel().getPlayersPoints().get(getModel().getThisPlayer());
        if(points!=null) {
            out.println(colorize("Your points: ",Attribute.BOLD()));
            out.println("Adjacent Tiles = " + points.get(0) + printPointOrPoints(points.get(0)));
            out.println("Common Goal 1 =  " + points.get(1) + printPointOrPoints(points.get(1)));
            out.println("Common Goal 2 =  "+ points.get(2) + printPointOrPoints(points.get(2)));
            out.println("End Game =       " + points.get(3) + printPointOrPoints(points.get(3)));
            out.println("Personal Goal =  " + points.get(4) + printPointOrPoints(points.get(4)));
            int totalScore = getModel().getTotalPointByNickname(getModel().getThisPlayer());
            out.println(colorize("Total Points", Attribute.BOLD()) + " =   " + totalScore + printPointOrPoints(totalScore));
        }

        if(getModel().getCommonGoals()[0]!=null || getModel().getCommonGoals()[1]!=null) {
            out.println();
            out.println(colorize("Common Goal 1: ", Attribute.BOLD()) + getModel().getCommonGoals()[0] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(0));
            out.println(colorize("Common Goal 2: ", Attribute.BOLD()) + getModel().getCommonGoals()[1] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(1));
        }

        out.println();
        if(getModel().getBoard()!=null && getModel().getBookShelfByNickname(getModel().getThisPlayer())!=null && getModel().getPersonalGoal()!=null) {
            printBoardBookShelfPersonalGoal(fromTileSubjectToChar(getModel().getBoard(), true),
                    fromTileSubjectToChar(getModel().getBookShelfByNickname(getModel().getThisPlayer()), false),
                    fromTileTypeToChar(getModel().getPersonalGoal()));
        }

        if(getModel().getCurrentPlayer() != null && !getModel().getGameState().equals(GameState.SUSPENDED.toString()) && !getModel().getGameState().equals(GameState.END.toString())) {
            if(getModel().getCurrentPlayer().equals(getModel().getThisPlayer())){
                out.println(colorize("It's your turn to play!", Attribute.BOLD(), Attribute.GREEN_TEXT()));
            } else {
                out.println(colorize("Current Player is " + getModel().getCurrentPlayer(), Attribute.BOLD(), Attribute.GREEN_TEXT()));
            }
        }
        out.println(colorize("<--To learn how to play, please type 'help'.-->",Attribute.BOLD()));
    }

    private void showChat(){
        state = TUIState.CHAT;
        System.out.print(colorize("                                                                     ", Attribute.GREEN_BACK()));
        System.out.print(colorize("MY SHELFIE: CHAT", Attribute.GREEN_BACK()));
        System.out.println(colorize("                                                                     ", Attribute.GREEN_BACK()));
        List<Triple<String, List<String>, String>> messages = getModel().getMessages();
        if(messages == null){
            System.out.println("There aren't any messages yet.");
        } else {

            out.println("NUMBER OF MESSAGES: " + messages.size());

            for (Triple<String, List<String>, String> message : messages) {
                if (message.getSecond().contains(getModel().getThisPlayer()) || message.getFirst().equals(getModel().getThisPlayer())) {
                    String sender = message.getFirst();
                    String priv = privateOrPublicMessage(message.getSecond());
                    if (sender.equals(getModel().getThisPlayer())) {
                        sender = "You";
                        if (priv.equals("(private)")) {
                            priv = "(private with " + message.getSecond().get(0) + ") " ;
                        }
                    }
                    out.println(colorize(sender + " " + priv, Attribute.BLUE_TEXT()) + " " + message.getThird());
                }
            }
        }
        out.println(colorize("<--There aren't any more messages. If you want to return to the homepage, please type 'exit'.-->",Attribute.BOLD()));
    }

    private void showBookshelves(){
        state = TUIState.OTHERS;
        System.out.print(colorize("                                    ", Attribute.GREEN_BACK()));
        System.out.print(colorize("MY SHELFIE: OTHER PLAYERS' BOOKSHELVES AND POINTS", Attribute.GREEN_BACK()));
        System.out.println(colorize("                                    ", Attribute.GREEN_BACK()));
        List<String> nicknames = getOtherPlayer();
        List<char[][]> bookshelves = getOtherBookShelves();
        List<List<Integer>> pointPlayers = getOtherPoints();
        List<Integer> totalPoints = getOtherTotalPoints();
        printOthersBookShelf(nicknames.get(0), nicknames.get(1), nicknames.get(2), bookshelves.get(0), bookshelves.get(1), bookshelves.get(2));
        out.println();
        printOthersPoint(nicknames.get(0), nicknames.get(1), nicknames.get(2), pointPlayers.get(0), pointPlayers.get(1), pointPlayers.get(2), totalPoints.get(0), totalPoints.get(1), totalPoints.get(2));
        out.println(colorize("<--If you want to return to the homepage, please type 'exit'.-->",Attribute.BOLD()));
    }

    @Override
    public void showWinner(){
        state = TUIState.END;
        System.out.print(colorize("                                                           ", Attribute.GREEN_BACK()));
        System.out.print(colorize("MY SHELFIE: HOME OF " + getModel().getThisPlayer(), Attribute.GREEN_BACK()));
        System.out.println(colorize("                                                           ", Attribute.GREEN_BACK()));
        printGameState(getModel().getGameState());
        out.println("The winner is..." + colorize(getModel().getWinnerPlayer(), Attribute.BOLD(), Attribute.BRIGHT_YELLOW_TEXT()) + "!");
        out.println("Here are the bookshelves and the point of all players:");

        char[][] bookshelf = fromTileSubjectToChar(getModel().getBookShelfByNickname(getModel().getThisPlayer()), false);
        List<Integer> pointPlayer = getModel().getPlayersPointsByNickname(getModel().getThisPlayer());
        int totalScore = getModel().getTotalPointByNickname(getModel().getThisPlayer());

        List<String> nicknames = getOtherPlayer();
        List<char[][]> othersBookshelves = getOtherBookShelves();
        List<List<Integer>> othersPoints = getOtherPoints();
        List<Integer> othersTotalPoints = getOtherTotalPoints();
        out.println("              Your Bookshelf:              Your Points:");
        out.println("               " + getDividerBookShelf(0));
        for( int i = 0; i < DIMROW_BOOKSHELF; ++i ){
            out.print("               ");
            printLineBookShelf(i,bookshelf);
            out.print("               ");
            if(i < 5 ) {
                printPoint(pointPlayer, i);
            } else {
                out.print(colorize("Total Points", Attribute.BOLD()) + " =   " + totalScore + printPointOrPoints(totalScore));
            }
            out.println();
            out.println("               "+ getDividerBookShelf(i+1));
        }
        out.println();
        printOthersBookShelf(nicknames.get(0), nicknames.get(1), nicknames.get(2), othersBookshelves.get(0), othersBookshelves.get(1), othersBookshelves.get(2));
        printOthersPoint(nicknames.get(0), nicknames.get(1), nicknames.get(2), othersPoints.get(0), othersPoints.get(1), othersPoints.get(2), othersTotalPoints.get(0), othersTotalPoints.get(1), othersTotalPoints.get(2));
        out.println(colorize("<--- Enter 'quit' to leave this game and start a new one. --->", Attribute.BOLD()));
    }

    private void printBoardBookShelfPersonalGoal(char[][] board, char[][] bookshelf, char[][] personalGoal){
        out.println("             Living Room Board:                           Your BookShelf:                   Your Personal Goal:");
        out.println("     1   2   3   4   5   6   7   8   9                   1   2   3   4   5                  " );
        out.println(getDividerBoard(0) + "               " + getDividerBookShelf(0) + "               " + getDividerPersonalGoal(0));

        for( int i = 0; i < DIM_BOARD; ++i ){
            printLineBoard(i, board);

            out.print("               ");
            if(i < 6){
                printLineBookShelf(i,bookshelf);
                out.print("               ");
                printLinePersonalGoal(i,personalGoal);
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
                    out.println(getDividerBoard(i+1) + "               "+ getDividerBookShelf(i+1) +"               "+ getDividerPersonalGoal(i+1) );
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
                return colorize("   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐", Attribute.TEXT_COLOR(245,245,246));
            }
            case 1 -> {
                return colorize("   ├───┼───┼───╠═══╬═══╬═══╗───┼───┼───┤", Attribute.TEXT_COLOR(245,245,246));
            }
            case 2 -> {
                return colorize("   ├───┼───╔═══╬═══╬═══╬═══╬═══╗───┼───┤", Attribute.TEXT_COLOR(245,245,246));
            }
            case 3 -> {
                return colorize("   ├───╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗", Attribute.TEXT_COLOR(245,245,246));
            }
            case 4 -> {
                return colorize("   ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣", Attribute.TEXT_COLOR(245,245,246));
            }
            case 5 -> {
                return colorize("   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝", Attribute.TEXT_COLOR(245,245,246));
            }
            case 6 -> {
                return colorize("   ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝───┤", Attribute.TEXT_COLOR(245,245,246));
            }
            case 7 -> {
                return colorize("   ├───┼───╚═══╬═══╬═══╬═══╬═══╝───┼───┤", Attribute.TEXT_COLOR(245,245,246));
            }
            case 8 -> {
                return colorize("   ├───┼───┼───╚═══╬═══╬═══╣───┼───┼───┤", Attribute.TEXT_COLOR(245,245,246));
            }
            case 9 -> {
                return colorize("   └───┴───┴───┴───╚═══╩═══╝───┴───┴───┘", Attribute.TEXT_COLOR(245,245,246));
            }
            default -> {
                return null;
            }
        }
    }

    private String getDividerBookShelf(int row){
        switch (row){
            case 0 -> { return colorize("╔═══╦═══╦═══╦═══╦═══╗", Attribute.TEXT_COLOR(245,245,246)); }
            case 1, 2, 3, 4, 5 -> { return colorize("╠═══╬═══╬═══╬═══╬═══╣", Attribute.TEXT_COLOR(245,245,246)); }
            case 6 -> { return colorize("╚═══╩═══╩═══╩═══╩═══╝", Attribute.TEXT_COLOR(245,245,246)); }
            default -> { return ""; }
        }
    }

    private String getDividerPersonalGoal(int row){
        switch (row){
            case 0 -> { return colorize("┌───┬───┬───┬───┬───┐", Attribute.TEXT_COLOR(245,245,246)); }
            case 1, 2, 3, 4, 5 -> { return colorize("├───┼───┼───┼───┼───┤", Attribute.TEXT_COLOR(245,245,246)); }
            case 6 -> { return colorize("└───┴───┴───┴───┴───┘", Attribute.TEXT_COLOR(245,245,246)); }
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
                result.append(colorize("│   │   │   ", Attribute.TEXT_COLOR(245,245,246)));
                c = 3;
            }
            case 2, 6 -> {
                result.append(colorize("│   │   ", Attribute.TEXT_COLOR(245,245,246)));
                c = 2;
            }
            case 3 -> {
                result.append(colorize("│   ", Attribute.TEXT_COLOR(245,245,246)));
                c = 1;
            }
            case 8 -> {
                result.append(colorize("│   │   │   │   ", Attribute.TEXT_COLOR(245,245,246)));
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

        result.append( colorize("║", Attribute.TEXT_COLOR(245,245,246)) );

        for( i = 0; i < n; ++i ){
            result.append( toPrintChar(board[r][c + i]) );

            if( i < n - 1 )
                result.append(colorize("║", Attribute.TEXT_COLOR(245,245,246)));
        }

        result.append( colorize("║", Attribute.TEXT_COLOR(245,245,246)) );

        for( i += c ; i < DIM_BOARD; ++i ){
            result.append( colorize("   │", Attribute.TEXT_COLOR(245,245,246)) );
        }

        out.print(result.toString());
    }

    private void printLinePersonalGoal(int row, char[][] matrix){
        if(matrix!=null) {
            for (int j = 0; j < DIMCOL_BOOKSHELF; j++) {
                if (j == 0)
                    out.print(colorize("│", Attribute.TEXT_COLOR(245, 245, 246)) + toPrintChar(matrix[row][j]) + colorize("│", Attribute.TEXT_COLOR(245, 245, 246)));
                else if (j < DIMCOL_BOOKSHELF - 1)
                    out.print(toPrintChar(matrix[row][j]) + colorize("│", Attribute.TEXT_COLOR(245, 245, 246)));
                else
                    out.print(toPrintChar(matrix[row][j]) + colorize("│", Attribute.TEXT_COLOR(245, 245, 246)));
            }
        }
    }

    private void printLineBookShelf(int row, char[][] matrix) {
        if(matrix!=null) {
            for (int j = 0; j < DIMCOL_BOOKSHELF; j++) {
                if (j == 0)
                    out.print(colorize("║", Attribute.TEXT_COLOR(245, 245, 246)) + toPrintChar(matrix[row][j]) + colorize("║", Attribute.TEXT_COLOR(245, 245, 246)));
                else if (j < DIMCOL_BOOKSHELF - 1)
                    out.print(toPrintChar(matrix[row][j]) + colorize("║", Attribute.TEXT_COLOR(245, 245, 246)));
                else
                    out.print(toPrintChar(matrix[row][j]) + colorize("║", Attribute.TEXT_COLOR(245, 245, 246)));
            }
        }
    }

    private void printOthersBookShelf(String nickname1, String nickname2, String nickname3, char[][] bookShelf1, char[][] bookShelf2, char[][] bookShelf3){
        out.print("               " + nickname1);
        // 21 space for divider
        int l = 20;
        while( l - nickname1.length() >= 0){
            out.print(" ");
            l--;
        }
        out.print( "               " + nickname2);
        l = 20;
        while( l - nickname2.length() >= 0){
            out.print(" ");
            l--;
        }
        out.println("               " + nickname3);

        if(bookShelf1==null && bookShelf2==null && bookShelf3==null){
            out.println("There aren't other players yet.");
        }
        if(bookShelf1!=null) {
            out.print("               " + getDividerBookShelf(0) + "               ");
        }
        if(bookShelf2!=null){
            out.print(getDividerBookShelf(0) + "               ");
        }
        if(bookShelf3!=null) {
            out.print(getDividerBookShelf(0));
        }
        out.println();
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

    private void printOthersPoint(String nickname1, String nickname2, String nickname3, List<Integer> pointPlayer1, List<Integer> pointPlayer2, List<Integer> pointPlayer3, Integer total1, Integer total2, Integer total3){
        out.print("                 " + nickname1);
        int l = 9;
        while( l - nickname1.length() > 0){
            out.print(" ");
            l--;
        }
        out.print("               " + nickname2);
        l = 9;
        while( l - nickname2.length() > 0){
            out.print(" ");
            l--;
        }
        out.println("               " + nickname3);
        out.print("Adjacent Tiles:  ");
        printPoint(pointPlayer1, 0);
        printPoint(pointPlayer2, 0);
        printPoint(pointPlayer3, 0);
        out.println();
        out.print("Common Goal 1:   ");
        printPoint(pointPlayer1, 1);
        printPoint(pointPlayer2, 1);
        printPoint(pointPlayer3, 1);
        out.println();
        out.print("Common Goal 2:   ");
        printPoint(pointPlayer1, 2);
        printPoint(pointPlayer2, 2);
        printPoint(pointPlayer3, 2);
        out.println();
        out.print("End Game:        ");
        printPoint(pointPlayer1, 3);
        printPoint(pointPlayer2, 3);
        printPoint(pointPlayer3, 3);
        out.println();
        out.print("Personal goal:   ");
        printPoint(pointPlayer1, 4);
        printPoint(pointPlayer2, 4);
        printPoint(pointPlayer3, 4);
        out.println();
        out.print(colorize("Total points:    ", Attribute.BOLD()));
        if(total1 != null) {
            out.print(total1 + " " + printPointOrPoints(total1) + "               ");
        }
        if(total2 != null) {
            out.print(total2 + " " + printPointOrPoints(total2) + "               ");
        }
        if(total3 != null) {
            out.print(total3 + " " + printPointOrPoints(total3));
        }
        out.println();
    }

    private char[][] fromTileSubjectToChar(TileSubject[][] matrix, boolean board){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if (matrix[i][j] == null && INIT_MATRIX[i][j] == null && board) {
                    result[i][j] = ' ';
                } else if (matrix[i][j] == null && INIT_MATRIX[i][j] != null && board) {
                    result[i][j] = EMPTY;
                } else if (matrix[i][j] == null && !board) {
                    result[i][j] = EMPTY;
                } else {
                    result[i][j] = getCharFromTileType(matrix[i][j].getTileType());
                }
            }
        }
        return result;
    }

    private char[][] fromTileTypeToChar(TileType[][] matrix){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j]==null){
                    result[i][j]=EMPTY;
                } else {
                    result[i][j] = getCharFromTileType(matrix[i][j]);
                }
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
        if(point == 1) return " point ";
        if(point == -1) return "";
        return " points";
    }

    private void printPoint(List<Integer> point, int index){
        if(point != null)
            out.print(point.get(index) + " " + printPointOrPoints(point.get(index)) + "               ");
    }

    private String privateOrPublicMessage(List<String> receivers){
        if(receivers.size() == 1) return "(private)" ;
        return "(public)" ;
    }

    private List<String> getOtherPlayer(){
        List<String> nicknames = new ArrayList<>(getModel().getPlayers().stream().filter(n -> !n.equals(getModel().getThisPlayer())).toList());
        while(nicknames.size()-3 < 0){
            nicknames.add("");
        }
        return nicknames;
    }

    private List<char[][]> getOtherBookShelves(){
        List<String> nicknames = getOtherPlayer();
        List<char[][]> bookshelves = new ArrayList<>();
        char[][] bookShelf1 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(0)), false);
        char[][] bookShelf2 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(1)), false);
        char[][] bookShelf3 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(2)), false);
        bookshelves.add(bookShelf1);
        bookshelves.add(bookShelf2);
        bookshelves.add(bookShelf3);
        return bookshelves;
    }

    private List<List<Integer>> getOtherPoints(){
        List<String> nicknames = getOtherPlayer();
        List<List<Integer>> points = new ArrayList<>();
        List<Integer> pointPlayer1 = getModel().getPlayersPointsByNickname(nicknames.get(0));
        List<Integer> pointPlayer2 = getModel().getPlayersPointsByNickname(nicknames.get(1));
        List<Integer> pointPlayer3 = getModel().getPlayersPointsByNickname(nicknames.get(2));
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
        out.print(colorize("                                                                   ", Attribute.GREEN_BACK()));
        out.print(colorize("MY SHELFIE: LEGEND", Attribute.GREEN_BACK()));
        out.print(colorize("                                                                   ", Attribute.GREEN_BACK()));
        out.println(colorize("Here are the actions you can make during the game:", Attribute.ITALIC()));
        out.println(colorize(" TYPE        DESCRIPTION", Attribute.ITALIC()));
        out.println(" play        use it during your turn in order to move tiles from the board to your bookshelf.");
        out.println(" message     use it to send messages to other players.");
        out.println(" chat        use it to see the chat.");
        out.println(" others      use it to see other players' bookshelves and points.");
        out.println(" exit        use this to leave chat, others visual or help legend.");
        out.println(" quit        use it to leave the game, you will be directed to the welcome page so you can play again.");
        out.println(colorize("<--- Please enter exit to return to the homepage. --->", Attribute.BOLD()));
    }

    private String toPrintChar(char c){
       switch(c) {
           case EMPTY -> {
               return " " + c + " ";
           }
           case 't' -> {
               return colorize(" " + c + " ", Attribute.CYAN_BACK());
           }
           case 'f' -> {
               return colorize(" " + c + " ", Attribute.BLUE_BACK());
           }
           case 'c' -> {
               return colorize(" " + c + " ", Attribute.GREEN_BACK());
           }
           case 'g' -> {
               return colorize(" " + c + " ", Attribute.YELLOW_BACK());
           }
           case 'p' -> {
               return colorize(" " + c + " ", Attribute.MAGENTA_BACK());
           }
           case 'b' -> {
               return colorize(" " + c + " ", Attribute.WHITE_BACK());
           }
           default -> {
               return "   ";
           }
       }
    }
}
