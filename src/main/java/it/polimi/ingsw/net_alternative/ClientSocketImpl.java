package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.clientmessage.*;
import it.polimi.ingsw.utils.Coordinate;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSocketImpl implements ClientInterface, Runnable {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    private boolean OPEN;

    private final OnServerConnectionLostListener onConnectionLostListener;


    private final ServerDispatcherInterface serverDispatcher;

    public ClientSocketImpl(Socket socket, ServerDispatcherInterface serverDispatcher, OnServerConnectionLostListener onConnectionLostListener) throws IOException {
        this.oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        this.serverDispatcher = serverDispatcher;
        this.onConnectionLostListener = onConnectionLostListener;
        OPEN = true;
    }
    @Override
    public synchronized void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        AchievedCommonGoalNetMessage achievedCommonGoalNetMessage = new AchievedCommonGoalNetMessage(nicknamePlayer, tiles, numberCommonGoal);
        try{
            if(!OPEN) {
                return;
            }
            oos.writeObject(achievedCommonGoalNetMessage);
            oos.flush();
            oos.flush();
        }catch(IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles) {
        AchievedPersonalGoalNetMessage achievedPersonalGoalNetMessage = new AchievedPersonalGoalNetMessage(nickname, tiles);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(achievedPersonalGoalNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles) {
        AdjacentTilesUpdatedNetMessage adjacentTilesUpdatedNetMessage = new AdjacentTilesUpdatedNetMessage(nickname, tiles);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(adjacentTilesUpdatedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onAssignedCommonGoal(String description, int n) {
        AssignedCommonGoalNetMessage assignedCommonGoalNetMessage = new AssignedCommonGoalNetMessage(description, n);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(assignedCommonGoalNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        AssignedPersonalGoalNetMessage assignedPersonalGoalNetMessage = new AssignedPersonalGoalNetMessage(nickname, goalPattern, scoreMap);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(assignedPersonalGoalNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onBoardRefilled() {
        BoardRefilledNetMessage boardRefilledNetMessage = new BoardRefilledNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(boardRefilledNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onBoardUpdated(TileSubject[][] tileSubjects) {
        BoardUpdatedNetMessage boardUpdatedNetMessage = new BoardUpdatedNetMessage(tileSubjects);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(boardUpdatedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf) {
        BookShelfUpdatedNetMessage bookShelfUpdatedNetMessage = new BookShelfUpdatedNetMessage(nickname, bookShelf);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(bookShelfUpdatedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal) {
        ChangedCommonGoalAvailableScoreNetMessage changedCommonGoalAvailableScoreNetMessage = new ChangedCommonGoalAvailableScoreNetMessage(score, numberOfCommonGoal);
        try{
            if(!OPEN) {
                return;
            }
            oos.writeObject(changedCommonGoalAvailableScoreNetMessage);
        }catch(IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onCurrentPlayerChangedListener(String nickname) {
        CurrentPlayerChangedListenerNetMessage currentPlayerChangedListenerNetMessage = new CurrentPlayerChangedListenerNetMessage(nickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(currentPlayerChangedListenerNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onException(Exception e) {
        ExceptionNetMessage exceptionNetMessage = new ExceptionNetMessage(e);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(exceptionNetMessage);
        } catch (IOException ex) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onLastPlayerUpdated(String nicknameLastPlayer) {
        LastPlayerUpdatedNetMessage lastPlayerUpdatedNetMessage = new LastPlayerUpdatedNetMessage(nicknameLastPlayer);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(lastPlayerUpdatedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text) {
        MessageSentNetMessage messageSentNetMessage = new MessageSentNetMessage(nicknameSender, nicknameReceivers, text);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(messageSentNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        MessagesSentNetMessage messagesSentNetMessage = new MessagesSentNetMessage(senderNicknames, receiverNicknames, texts);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(messagesSentNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onPlayerStateChanged(String nickname, PlayerState playerState) {
        PlayerStateChangedNetMessage playerStateChangedNetMessage = new PlayerStateChangedNetMessage(nickname, playerState);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(playerStateChangedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onPlayersListChanged(List<String> players) {
        PlayersListChangedNetMessage playersListChangedNetMessage = new PlayersListChangedNetMessage(players);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(playersListChangedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal) {
        PointsUpdatedNetMessage pointsUpdatedNetMessage = new PointsUpdatedNetMessage(nickName, scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(pointsUpdatedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onStateChanged(GameState gameState) {
        StateChangedNetMessage stateChangedNetMessage = new StateChangedNetMessage(gameState);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(stateChangedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onWinnerChanged(String nickname) {
        WinnerChangedNetMessage winnerChangedNetMessage = new WinnerChangedNetMessage(nickname);
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(winnerChangedNetMessage);
        } catch (IOException e) {
            OPEN = false;
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }
    }

    @Override
    public void nop() {
        NopNetMessage nopNetMessage = new NopNetMessage();
        try {
            if(!OPEN) {
                return;
            }
            oos.writeObject(nopNetMessage);
        } catch (IOException e) {
            synchronized (this) {
                OPEN = false;
            }
            onConnectionLostListener.onConnectionLost(this);
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        while (true) {
            try {
                ServerMessage message = (ServerMessage) ois.readObject();
                System.out.println("messaged received");
                executorService.submit(()->message.dispatch(serverDispatcher, this));
            } catch (Exception e) {
                synchronized (this) {
                    OPEN = false;
                }
                onConnectionLostListener.onConnectionLost(this);
                e.printStackTrace();
                return;
            }
        }
    }


}
