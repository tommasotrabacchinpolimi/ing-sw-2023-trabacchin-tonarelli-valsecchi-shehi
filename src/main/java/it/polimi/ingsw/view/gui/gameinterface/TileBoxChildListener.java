package it.polimi.ingsw.view.gui.gameinterface;

import it.polimi.ingsw.view.gui.board.BoardView;
import it.polimi.ingsw.view.gui.board.BoardViewController;
import it.polimi.ingsw.view.gui.bookshelf.PersonalBookshelfController;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TileBoxChildListener implements ListChangeListener<Node> {

    private final HBox selectedTilesBox;

    private final PersonalBookshelfController gameBookshelfCommandViewController;

    private final BoardViewController boardViewController;

    private final List<TileSubjectView> tilesOnBoard;

    private final List<Pair<EventHandler<MouseEvent>, TileSubjectView>> orderedSelectedTiles = new ArrayList<>();

    public TileBoxChildListener(HBox selectedTilesBox, PersonalBookshelfController gameBookshelfCommandViewController, BoardViewController boardViewController) {
        this.selectedTilesBox = selectedTilesBox;
        this.gameBookshelfCommandViewController = gameBookshelfCommandViewController;
        this.boardViewController = boardViewController;
        this.tilesOnBoard = boardViewController.getTilesOnBoard();
    }

    @Override
    public void onChanged(Change<? extends Node> change) {
        while(change.next()) {
            if(change.wasAdded()){
                selectedTilesBox.setStyle("-fx-padding: 0.5em");
                gameBookshelfCommandViewController.enableAllButtons();

                change.getAddedSubList()
                        .stream()
                        .map(node -> (TileSubjectView) node)
                        .forEach( tile -> {
                            tile.setClickable();
                            addInOrder(tile);
                        });

                tilesOnBoard.forEach(TileSubjectView::disable);
            }

            if(change.wasRemoved()) {
                if(selectedTilesBox.getChildren().size() == 0) {
                    selectedTilesBox.setStyle("-fx-padding: 0em");
                    gameBookshelfCommandViewController.disableAllButtons();
                    boardViewController.setActiveTilesOnBoard();
                }

                change.getRemoved()
                        .stream()
                        .map(node -> (TileSubjectView) node)
                        .forEach(this::removeHandlerTilePair);
            }
        }
    }

    private void addInOrder(TileSubjectView tile) {
        EventHandler<MouseEvent> eventHandler = event -> {
            if(event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if(tile.isClicked()) {
                    tile.setClicked(false);
                    Collections.swap(orderedSelectedTiles, getFirstNotSelected(), getHandlerTilePairIndex(tile));
                    tile.setClicked(true);
                }else {
                    orderedSelectedTiles.add(orderedSelectedTiles.remove(getHandlerTilePairIndex(tile)));
                }
            }
        };

        orderedSelectedTiles.add(new Pair<>(eventHandler, tile));

        tile.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    private int getFirstNotSelected() {
        return orderedSelectedTiles.indexOf(
                orderedSelectedTiles.stream()
                        .filter( pair -> !pair.getValue().isClicked())
                        .findFirst().get());
    }

    private Pair<EventHandler<MouseEvent>, TileSubjectView> getHandlerTilePair(TileSubjectView tile) {
        return orderedSelectedTiles.stream()
                .filter(pair -> pair.getValue().equals(tile))
                .toList()
                .get(0);
    }

    private int getHandlerTilePairIndex(TileSubjectView tile) {

        return orderedSelectedTiles.indexOf(getHandlerTilePair(tile));
    }

    private void removeHandlerTilePair(TileSubjectView tile) {

        Pair<EventHandler<MouseEvent>, TileSubjectView> removed = getHandlerTilePair(tile);

        removed.getValue().removeEventHandler(MouseEvent.MOUSE_CLICKED, removed.getKey());

        orderedSelectedTiles.remove(getHandlerTilePair(tile));
    }

    public List<Pair<EventHandler<MouseEvent>, TileSubjectView>> getOrderedSelectedTiles() {
        return orderedSelectedTiles;
    }

    protected List<TileSubjectView> getOrderedTilesFromBox() {
        return orderedSelectedTiles.stream()
                .map(Pair::getValue)
                .toList();
    }
}
