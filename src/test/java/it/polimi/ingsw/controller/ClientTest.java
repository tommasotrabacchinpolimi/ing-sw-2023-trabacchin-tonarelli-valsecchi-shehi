package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;
import java.util.Map;

public class ClientTest implements ClientInterface{
    @Override
    public void nop() {

    }

    @Override
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {

    }

    @Override
    public void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {

    }

    @Override
    public void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {

    }

    @Override
    public void onAssignedCommonGoal(String description, int n, String id) {

    }

    @Override
    public void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {

    }

    @Override
    public void onBoardRefilled() {

    }

    @Override
    public void onBoardUpdated(TileSubject[][] tileSubjects) {

    }

    @Override
    public void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {

    }

    @Override
    public void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {

    }

    @Override
    public void onCurrentPlayerChangedListener(String nickname) {

    }

    @Override
    public void onException(String playerCause, Exception e) {

    }

    @Override
    public void onLastPlayerUpdated(String nicknameLastPlayer) {

    }

    @Override
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {

    }

    @Override
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {

    }

    @Override
    public void onPlayerStateChanged(String nickname, PlayerState playerState) {

    }

    @Override
    public void onPlayersListChanged(List<String> players) {

    }

    @Override
    public void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {

    }

    @Override
    public void onStateChanged(GameState gameState) {

    }

    @Override
    public void onWinnerChanged(String nickname) {

    }
}
