package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.AlreadyTakenNicknameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ControllerTest {

    @Test
    public void test1() {
        LobbyController lobbyController = new LobbyController(10000);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        ClientTest ema = new ClientTest();
        ClientTest adem = new ClientTest();
        try{
            controllerDispatcher.createGame(tommy, "tommy", 4);
        } catch(Exception e) {

        }

        State model = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        try{
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {

        }

        try{
            controllerDispatcher.joinGame(ema, "ema");

        }catch(Exception e) {

        }

        try{
            controllerDispatcher.joinGame(adem, "ema");
        }catch(Exception e) {

        }
        Assertions.assertEquals(model.getPlayers().size(), 3);
        try{
            controllerDispatcher.joinGame(adem, "adem");
        }catch(Exception e) {

        }


        List<ClientInterface> clients = new ArrayList<>();
        clients.add(tommy);
        clients.add(melanie);
        clients.add(ema);
        clients.add(adem);

        ClientInterface firstPlayer = model.getCurrentPlayer().getVirtualView();
        int firstPlayerIndex = clients.indexOf(firstPlayer);
        System.out.println("first player index = "+firstPlayerIndex);
        Collections.rotate(clients, -firstPlayerIndex);

        int currentPlayerIndex = 0;
        int lastPlayerIndex = -1;
        int cont = 0;
        while (lastPlayerIndex != currentPlayerIndex) {
            if(cont == 0) {
                controllerDispatcher.sentMessage(clients.get(currentPlayerIndex), "messaggio di prova", new String[]{"melanie"});
            }
            Player player = model.getCurrentPlayer();
            assertEquals(player, model.getPlayerFromView(clients.get(currentPlayerIndex)));
            BookShelf bookShelf = model.getPlayerFromView(clients.get(currentPlayerIndex)).getBookShelf();
            Board board = model.getBoard();
            int column = -1;
            for (int n = 3; n > 0; n--) {
                column = getDesired(bookShelf.getTileSubjectTaken(), n);
                if (column != -1) {
                    Coordinate[] coordinates = getCoordinatesAvailable(board, n);
                    if (coordinates != null) {
                        controllerDispatcher.dragTilesToBookShelf(clients.get(currentPlayerIndex), Arrays.asList(coordinates), column);
                        break;
                    }
                }
            }
            if(model.getGameState()==GameState.END) {
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % 4;
            cont++;
        }
    }


    @Test
    public void test2() {
        LobbyController lobbyController = new LobbyController(10000);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        try{
            controllerDispatcher.createGame(tommy, "tommy", 2);
        } catch(Exception e) {

        }

        State model = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        try{
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {

        }




        List<ClientInterface> clients = new ArrayList<>();
        clients.add(tommy);
        clients.add(melanie);

        ClientInterface firstPlayer = model.getCurrentPlayer().getVirtualView();
        int firstPlayerIndex = clients.indexOf(firstPlayer);
        Collections.rotate(clients, -firstPlayerIndex);

        int currentPlayerIndex = 0;
        int lastPlayerIndex = -1;
        while (true) {
            Player player = model.getCurrentPlayer();
            assertEquals(player, model.getPlayerFromView(clients.get(currentPlayerIndex)));
            BookShelf bookShelf = model.getPlayerFromView(clients.get(currentPlayerIndex)).getBookShelf();
            Board board = model.getBoard();
            int column = -1;
            for (int n = 3; n > 0; n--) {
                column = getDesired(bookShelf.getTileSubjectTaken(), 1);
                if (column != -1) {
                    Coordinate[] coordinates = getCoordinatesAvailable(board, 1);
                    if (coordinates != null) {
                        controllerDispatcher.dragTilesToBookShelf(clients.get(currentPlayerIndex), Arrays.asList(coordinates), column);
                        break;
                    }
                }
            }
            if(model.getGameState()==GameState.END) {
                System.out.println("ending");
                break;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        }
    }


    @Test
    public void test3() {
        LobbyController lobbyController = new LobbyController(10000);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        try{
            controllerDispatcher.createGame(tommy, "tommy", 2);
        } catch(Exception e) {

        }

        State model = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        try{
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {

        }

        lobbyController.onConnectionLost(tommy);

        assertEquals(model.getPlayerFromView(tommy).getPlayerState(), PlayerState.DISCONNECTED);

        ClientTest new_tommy = new ClientTest();
        try {
            controllerDispatcher.joinGame(new_tommy, "tommy");
        } catch(Exception e) {

        }

        assertEquals(model.getPlayerFromView(new_tommy).getPlayerState(), PlayerState.CONNECTED);

    }


    @Test
    public void test4() {
        LobbyController lobbyController = new LobbyController(10000);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        try{
            controllerDispatcher.createGame(tommy, "tommy", 2);
        } catch(Exception e) {

        }
        State model = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        lobbyController.onConnectionLost(tommy);
        assertEquals(model.getPlayerFromView(tommy).getPlayerState(), PlayerState.DISCONNECTED);
        ClientTest new_tommy = new ClientTest();
        try{
            controllerDispatcher.joinGame(new_tommy, "tommy");
        } catch(Exception e) {

        }
        assertEquals(model.getPlayerFromView(new_tommy).getPlayerState(), PlayerState.CONNECTED);
        try{
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {

        }




    }


    @Test
    public void test5() {
        LobbyController lobbyController = new LobbyController(10000);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        try{
            controllerDispatcher.joinGame(tommy, "tommy");
        } catch(Exception e) {

        }
        try{
            controllerDispatcher.createGame(melanie, "melanie", 2);
        } catch(Exception e) {}
        State model = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        assertEquals(model.getGameState(), GameState.MID);
        controllerDispatcher.quitGame(melanie);
        controllerDispatcher.nop();
    }

    @Test
    public void test6() {
        LobbyController lobbyController = new LobbyController(10000);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        ClientTest ema = new ClientTest();
        ClientTest adem = new ClientTest();
        ClientTest nico = new ClientTest();
        try {
            controllerDispatcher.createGame(tommy, "tommy", 3);
        } catch(Exception e) {}

        try {
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {}

        try {
            controllerDispatcher.joinGame(ema, "ema");
        } catch(Exception e) {}

        try {
            controllerDispatcher.createGame(adem, "adem", 2);
        } catch(Exception e) {}

        try {
            controllerDispatcher.joinGame(nico, "nico");
        } catch(Exception e) {}

        State model1 = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        State model2 = controllerDispatcher.getViewToControllerMap().get(adem).getState();

        assertNotEquals(model1, model2);

        assertEquals(model1.getGameState(), GameState.MID);
        assertEquals(model2.getGameState(), GameState.MID);
    }

    @Test
    public void test7() throws InterruptedException {
        LobbyController lobbyController = new LobbyController(500);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        ClientTest ema = new ClientTest();
        ClientTest adem = new ClientTest();
        ClientTest nico = new ClientTest();
        try {
            controllerDispatcher.createGame(tommy, "tommy", 3);
        } catch(Exception e) {}

        try {
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {}

        try {
            controllerDispatcher.joinGame(ema, "ema");
        } catch(Exception e) {}

        try {
            controllerDispatcher.createGame(adem, "adem", 2);
        } catch(Exception e) {}

        try {
            controllerDispatcher.joinGame(nico, "nico");
        } catch(Exception e) {}

        State model1 = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        State model2 = controllerDispatcher.getViewToControllerMap().get(adem).getState();

        assertNotEquals(model1, model2);

        ClientTest new_adem = new ClientTest();
        ClientTest new_nico = new ClientTest();
        if(model2.getCurrentPlayer().equals(model2.getPlayerFromView(adem))) {
            lobbyController.onConnectionLost(adem);
            lobbyController.onConnectionLost(nico);
            Thread.sleep(1200);
            assertEquals(model2.getGameState(), GameState.SUSPENDED);

            try {
                controllerDispatcher.joinGame(new_adem, "adem");
            } catch(Exception e) {}
            try {
                controllerDispatcher.joinGame(new_nico, "nico");
            } catch(Exception e) {}
            State model3 = controllerDispatcher.getViewToControllerMap().get(new_adem).getState();
            Controller c3 = controllerDispatcher.getViewToControllerMap().get(new_adem);
            assertEquals(model3.getGameState(), GameState.MID);
            assertEquals(true, c3.getTimingStateMachine().getTimingState() instanceof ConnectedTimingState);

        }
        else {
            lobbyController.onConnectionLost(nico);
            lobbyController.onConnectionLost(adem);
            Thread.sleep(1200);
            assertEquals(model2.getGameState(), GameState.SUSPENDED);

            try {
                controllerDispatcher.joinGame(new_nico, "nico");
            } catch(Exception e) {}
            try {
                controllerDispatcher.joinGame(new_adem, "adem");
            } catch(Exception e) {}
            State model3 = controllerDispatcher.getViewToControllerMap().get(new_adem).getState();
            Controller c3 = controllerDispatcher.getViewToControllerMap().get(new_adem);
            assertEquals(model3.getGameState(), GameState.MID);
            assertEquals(true, c3.getTimingStateMachine().getTimingState() instanceof ConnectedTimingState);

        }

        State model3 = controllerDispatcher.getViewToControllerMap().get(new_adem).getState();

    }


    @Test
    public void test8() {
        LobbyController lobbyController = new LobbyController(500);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        ClientTest new_tommy = new ClientTest();
        try {
            controllerDispatcher.createGame(tommy, "tommy", 3);
        } catch(Exception e) {}
        try {
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {}

        try {
            controllerDispatcher.createGame(new_tommy, "tommy", 3);
        } catch(Exception e) {}

        assertEquals(controllerDispatcher.getViewToControllerMap().get(new_tommy), null);

    }

    @Test
    public void test9() {
        LobbyController lobbyController = new LobbyController(500);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        ClientTest new_tommy = new ClientTest();
        try {
            controllerDispatcher.createGame(tommy, "tommy", 3);
        } catch(Exception e) {}
        try {
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {}

        lobbyController.onConnectionLost(tommy);
        try {
            controllerDispatcher.createGame(new_tommy, "tommy", 3);
        } catch(Exception e) {}

        assertEquals(controllerDispatcher.getViewToControllerMap().get(new_tommy), null);

    }

    @Test
    public void test10() {
        LobbyController lobbyController = new LobbyController(500);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        try {
            controllerDispatcher.createGame(tommy, "tommy", 3);
        } catch(Exception e) {}
        try {
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {}
        State state1 = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        lobbyController.onConnectionLost(tommy);
        try {
            controllerDispatcher.createGame(tommy, "tommy", 3);
        } catch(Exception e) {}
        State state2 = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        assertEquals(state1, state2);
        try {
            controllerDispatcher.joinGame(tommy, "tommy");
        } catch(Exception e) {}
        State state3 = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        assertEquals(state1, state3);
    }

    @Test
    public void test11() throws InterruptedException {
        LobbyController lobbyController = new LobbyController(500);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        ClientTest tommy = new ClientTest();
        ClientTest melanie = new ClientTest();
        try {
            controllerDispatcher.createGame(tommy, "tommy", 2);
        } catch(Exception e) {}
        try {
            controllerDispatcher.joinGame(melanie, "melanie");
        } catch(Exception e) {}
        State state = controllerDispatcher.getViewToControllerMap().get(tommy).getState();
        if(state.getCurrentPlayer().getNickName().equals("tommy")) {
            lobbyController.onConnectionLost(melanie);
            Thread.sleep(700);
            assertEquals(state.getGameState(), GameState.MID);
            assertEquals(state.getCurrentPlayer(),state.getPlayerFromView(melanie));
            ClientTest new_melanie = new ClientTest();
            try{
                controllerDispatcher.joinGame(new_melanie, "melanie");
                assertEquals(state.getGameState(), GameState.MID);
                assertEquals(state.getCurrentPlayer(),state.getPlayerFromView(new_melanie));
            } catch(Exception e) {}
        }
        else {
            lobbyController.onConnectionLost(tommy);
            Thread.sleep(700);
            assertEquals(state.getGameState(), GameState.MID);
            assertEquals(state.getCurrentPlayer(),state.getPlayerFromView(tommy));
            ClientTest new_tommy = new ClientTest();
            try{
                controllerDispatcher.joinGame(new_tommy, "tommy");
                assertEquals(state.getGameState(), GameState.MID);
                assertEquals(state.getCurrentPlayer(),state.getPlayerFromView(new_tommy));
            } catch(Exception e) {}

        }

    }

    private Coordinate[] getCoordinatesAvailable(Board board, int desired) {
        TileSubject[][] matrix = board.getBoard();
        Coordinate[] coordinates = new Coordinate[desired];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] != null) {
                    //upward
                    for(int cont = 0; cont < desired; cont++) {
                        if(i + cont < 8 && i + cont>=0 && matrix[i + cont][j] != null && isFree(new Coordinate(i + cont, j), matrix)) {
                            coordinates[cont] = new Coordinate(i + cont, j);
                            if(cont == desired - 1) {
                                return coordinates;
                            }
                        }
                        else {
                            coordinates = new Coordinate[desired];
                            break;
                        }
                    }

                    //rightward
                    for(int cont = 0; cont < desired; cont++) {
                        if(j + cont < 8 && j + cont>=0 && matrix[i][j + cont] != null && isFree(new Coordinate(i, j + cont), matrix)) {
                            coordinates[cont] = new Coordinate(i, j + cont);
                            if(cont == desired - 1) {
                                return coordinates;
                            }
                        }
                        else {
                            coordinates = new Coordinate[desired];
                            break;
                        }
                    }
                    //downward
                    for(int cont = 0; cont > -desired; cont--) {
                        if(i + cont < 8 && i + cont>=0 && matrix[i + cont][j] != null && isFree(new Coordinate(i + cont, j), matrix)) {
                            coordinates[-cont] = new Coordinate(i + cont, j);
                            if(cont == desired - 1) {
                                return coordinates;
                            }
                        }
                        else {
                            coordinates = new Coordinate[desired];
                            break;
                        }
                    }
                    //leftward
                    for(int cont = 0; cont > -desired; cont--) {
                        if(j + cont < 8 && j + cont>=0 && matrix[i][j + cont] != null && isFree(new Coordinate(i, j + cont), matrix)) {
                            coordinates[-cont] = new Coordinate(i, j + cont);
                            if(cont == desired - 1) {
                                return coordinates;
                            }
                        }
                        else {
                            coordinates = new Coordinate[desired];
                            break;
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isFree(Coordinate coordinate, TileSubject[][] board) {
        if(coordinate.getX() + 1 < 8 && board[coordinate.getX() + 1][coordinate.getY()] == null) {
            return true;
        }
        if(coordinate.getX() - 1 >= 0 && board[coordinate.getX() - 1][coordinate.getY()] == null) {
            return true;
        }

        if(coordinate.getY() + 1 < 8 && board[coordinate.getX()][coordinate.getY() + 1] == null) {
            return true;
        }
        if(coordinate.getY() - 1 >= 0 && board[coordinate.getX()][coordinate.getY() - 1] == null) {
            return true;
        }
        return false;
    }

    private int getDesired(TileSubject[][] bookShelf, int desired) {
        for(int j = 0; j < bookShelf[0].length; j++) {
            int i;
            for(i = 0; i < bookShelf.length;i++) {
                if(bookShelf[i][j] != null) {
                    break;
                }
                if(i + 1 == desired) {
                    return j;
                }

            }
        }
        return -1;
    }

}
