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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSocketImpl implements ClientInterface, Runnable, Closeable {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    private boolean OPEN;

    private final OnServerConnectionLostListener onConnectionLostListener;

    private final Timer timer;

    private TimerTask timerTask;
    private final ServerDispatcherInterface serverDispatcher;

    public ClientSocketImpl(Socket socket, ServerDispatcherInterface serverDispatcher, OnServerConnectionLostListener onConnectionLostListener) throws IOException {
        this.oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        this.serverDispatcher = serverDispatcher;
        this.onConnectionLostListener = onConnectionLostListener;
        OPEN = true;
        this.timer = new Timer();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
            oos.flush();
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
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                close();
            }
        };
        this.timer.schedule(timerTask, 5000);
        while (true) {
            synchronized(this) {
                if(!OPEN) {
                    break;
                }
            }
            try {
                ServerMessage message = (ServerMessage) ois.readObject();
                if(message instanceof NopNetMessage) {
                    this.timerTask.cancel();
                    this.timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            close();
                        }
                    };
                    timer.schedule(timerTask, 5000);
                }
                executorService.submit(()->message.dispatch(serverDispatcher, this));
            } catch (Exception e) {
                synchronized (this) {
                    if(OPEN) {
                        onConnectionLostListener.onConnectionLost(this);
                    }

                    OPEN = false;
                }

                //e.printStackTrace();
                return;
            }
        }
    }


    @Override
    public synchronized void close()  {
        if(OPEN) {
            OPEN = false;
            this.onConnectionLostListener.onConnectionLost(this);
        }
    }
}
