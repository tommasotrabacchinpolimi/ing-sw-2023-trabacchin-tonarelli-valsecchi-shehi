package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 23/04/2023
 */
public interface ControllerInterface {
    void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn);
    void quitGame(ClientInterface view);
    void sentMessage(ClientInterface view, String text,  String[] receiversNickname);
}
