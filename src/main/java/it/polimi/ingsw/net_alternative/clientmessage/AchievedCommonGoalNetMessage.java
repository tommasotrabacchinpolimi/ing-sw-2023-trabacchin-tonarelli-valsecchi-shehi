package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public class AchievedCommonGoalNetMessage implements ClientMessage {
    private String nicknamePlayer;
    private List<Coordinate> tiles;
    private int numberCommonGoal;

    public AchievedCommonGoalNetMessage(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        this.nicknamePlayer = nicknamePlayer;
        this.tiles = tiles;
        this.numberCommonGoal = numberCommonGoal;
    }

    public String getNicknamePlayer() {
        return nicknamePlayer;
    }

    public List<Coordinate> getTiles() {
        return tiles;
    }

    public int getNumberCommonGoal() {
        return numberCommonGoal;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}