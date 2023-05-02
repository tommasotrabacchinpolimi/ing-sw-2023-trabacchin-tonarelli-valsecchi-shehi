package it.polimi.ingsw.view;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ViewData {
    private List<String> players;




    String thisPlayer;
    private String currentPlayer;
    private Map<String, String> playersState;
    private String gameState;

    private TileSubject[][] board;

    private Map<String, TileSubject[][]> bookShelves;

    private TileType[][] personalGoal;



    private String[] commonGoals;

    private Map<String, List<Integer>> playersPoints;



    private Map<Integer, Integer> availableScores;


    public ViewData(int BOARD_DIM, int BOOKSHELF_COL, int BOOKSHELF_ROW) {
        players = new LinkedList<>();
        playersState = new HashMap<>();
        gameState = "";
        board = new TileSubject[BOARD_DIM][BOARD_DIM];
        bookShelves = new HashMap<>();
        personalGoal = new TileType[BOOKSHELF_ROW][BOOKSHELF_COL];
        commonGoals = new String[2];
        playersPoints = new HashMap<>();
        availableScores = new HashMap<>();

    }
    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public Map<String, String> getPlayersState() {
        return playersState;
    }

    public void setPlayersState(Map<String, String> playersState) {
        this.playersState = playersState;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public TileSubject[][] getBoard() {
        return board;
    }

    public void setBoard(TileSubject[][] board) {
        this.board = board;
    }

    public Map<String, TileSubject[][]> getBookShelves() {
        return bookShelves;
    }

    public void setBookShelves(Map<String, TileSubject[][]> bookShelves) {
        this.bookShelves = bookShelves;
    }

    public TileType[][] getPersonalGoal() {
        return personalGoal;
    }

    public void setPersonalGoal(TileType[][] personalGoal) {
        this.personalGoal = personalGoal;
    }

    public Map<String, List<Integer>> getPlayersPoints() {
        return playersPoints;
    }

    public void setPlayersPoints(Map<String, List<Integer>> playersPoints) {
        this.playersPoints = playersPoints;
    }


    public String[] getCommonGoals() {
        return commonGoals;
    }

    public void setCommonGoals(String[] commonGoals) {
        this.commonGoals = commonGoals;
    }

    public Map<Integer, Integer> getAvailableScores() {
        return availableScores;
    }

    public void setAvailableScores(Map<Integer, Integer> availableScores) {
        this.availableScores = availableScores;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getThisPlayer() {
        return thisPlayer;
    }

    public void setThisPlayer(String thisPlayer) {
        this.thisPlayer = thisPlayer;
    }
}
