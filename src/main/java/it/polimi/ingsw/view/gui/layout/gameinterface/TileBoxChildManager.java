package it.polimi.ingsw.view.gui.layout.gameinterface;

import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieShadowType;
import it.polimi.ingsw.view.gui.layout.board.BoardViewController;
import it.polimi.ingsw.view.gui.layout.bookshelf.PersonalBookshelfController;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static it.polimi.ingsw.utils.color.MyShelfieColor.*;

/**
 * @version 2.0
 * @since 13/06/2023
 */
class TileBoxChildManager implements ListChangeListener<Node> {

    private final HBox selectedTilesBox;

    private final PersonalBookshelfController gameBookshelfCommandViewController;

    private final BoardViewController boardViewController;

    private final List<TileSubjectView> tilesOnBoard;

    private final List<Pair<TileSubjectView, StackPane>> tileContainers = new ArrayList<>();

    private final List<Pair<EventHandler<MouseEvent>, TileSubjectView>> orderedSelectedTiles = new ArrayList<>();

    public TileBoxChildManager(HBox selectedTilesBox,
                               PersonalBookshelfController gameBookshelfCommandViewController,
                               @NotNull BoardViewController boardViewController) {

        this.selectedTilesBox = selectedTilesBox;
        this.gameBookshelfCommandViewController = gameBookshelfCommandViewController;
        this.boardViewController = boardViewController;
        this.tilesOnBoard = boardViewController.getTilesOnBoard();
    }

    @Override
    public void onChanged(@NotNull Change<? extends Node> change) {
        while (change.next()) {

            if (change.wasAdded())
                handleAddedChild(change);

            if (change.wasRemoved()) {
                if (selectedTilesBox.getChildren().size() == 0) {
                    selectedTilesBox.setStyle("-fx-padding: 0em;");
                    gameBookshelfCommandViewController.disableAllButtons();
                    boardViewController.setActiveTilesOnBoardNoneSelected();
                }
            }
        }
    }

    private void handleAddedChild(@NotNull Change<? extends Node> change) {
        try {
            change.getAddedSubList()
                    .stream()
                    .map(node -> (TileSubjectView) node)
                    .forEach(this::addInStackPaneContainer);

            selectedTilesBox.setStyle("-fx-padding: 0.5em;");
            gameBookshelfCommandViewController.enableAllButtons();
            tilesOnBoard.forEach(TileSubjectView::disable);
        } catch (ClassCastException ignored) {
            //the tile subject view added is putted inside a container that is added in the selectedTilesBox
        }
    }

    private void addInStackPaneContainer(@NotNull TileSubjectView tile) {
        StackPane tileContainer = new StackPane();

        tile.setClickable();
        addInOrderTileLogic(tile);

        tile.changeParent(tileContainer, 0.0, 0.0);

        addContainerChildrenListener(tileContainer);

        selectedTilesBox.getChildren().add(tileContainer);

        tileContainers.add(new Pair<>(tile, tileContainer));
    }

    private void addInOrderTileLogic(TileSubjectView tile) {
        EventHandler<MouseEvent> eventHandler = event -> {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if (tile.isClicked()) {
                    tile.setClicked(false);
                    Collections.swap(orderedSelectedTiles, getFirstNotSelected(), getHandlerTilePairIndex(tile));
                    addTextHover(tile);
                    tile.setClicked(true);
                } else {
                    orderedSelectedTiles.add(orderedSelectedTiles.remove(getHandlerTilePairIndex(tile)));
                    removeHoverText(tile);
                    updatedTextHover();
                }
            }
        };

        orderedSelectedTiles.add(new Pair<>(eventHandler, tile));

        tile.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    private void addTextHover(TileSubjectView tile) {
        Label textHover = new Label();

        textHover.setText(getTilesOrderValue(tile));

        textHover.setLabelFor(getContainerFromTile(tile));

        textHover.setId("textHover" + tile);

        textHover.setMouseTransparent(true);

        textHover.setStyle("-fx-text-fill:" + CHARLESTON.getRGBAStyleSheet(0.70) + ";" +
                "-fx-font-family: 'Special Elite Regular';" +
                "-fx-wrap-text: true;" +
                "-fx-font-size: 1.2em;" +
                "-fx-label-padding: 0.2em;" +
                "-fx-effect: dropshadow(gaussian, " + KOBE.getDarkenRGBStyleSheet() + ", " +
                MyShelfieShadowType.NORMAL.getRadius() + ", 0.0, 0.0, 0.0) " +
                "dropshadow(one-pass-box, " + KOBE.getRGBStyleSheet() + ", " +
                MyShelfieShadowType.NORMAL.getRadius() + ", 1.0, 0.0, 0.0)");

        getContainerFromTile(tile).getChildren().add(textHover);

        StackPane.setAlignment(textHover, Pos.TOP_LEFT);
    }

    private String getTilesOrderValue(TileSubjectView tile) {
        return String.valueOf((orderedSelectedTiles.indexOf(getHandlerTilePair(tile)) + 1));
    }

    private void addContainerChildrenListener(@NotNull StackPane tileContainer) {
        tileContainer.getChildren().addListener((ListChangeListener<? super Node>) containerChange -> {
            while (containerChange.next()) {
                if (containerChange.wasRemoved()) {
                    try {
                        TileSubjectView removedTile = (TileSubjectView) containerChange.getRemoved().stream().findFirst().orElseThrow();

                        removeHandlerTilePair(removedTile);
                        cleanContainers();

                    } catch (ClassCastException ignored) {
                        //the contained element was a Label
                        Label removed = (Label) containerChange.getRemoved().stream().findFirst().orElseThrow();
                        removed.setVisible(false);
                    } catch (NoSuchElementException ignored) {
                        //No tiles in Container that can never happen
                    }
                }
            }
        });
    }

    private StackPane getContainerFromTile(TileSubjectView tile) {
        return getTileContainerPairFromTile(tile).getValue();
    }

    private Pair<TileSubjectView, StackPane> getTileContainerPairFromTile(TileSubjectView tile) {
        return tileContainers.stream()
                .filter(p -> p.getKey().equals(tile))
                .findFirst()
                .orElseThrow();
    }

    private void removeHoverText(TileSubjectView tile) {
        getTextHoverNode(tile).ifPresent(l -> getContainerFromTile(tile).getChildren().remove(l));
    }

    private Optional<Label> getTextHoverNode(TileSubjectView tile) {
        return Optional.ofNullable((Label) getContainerFromTile(tile).lookup("#textHover" + tile));
    }

    private void updatedTextHover() {
        tileContainers.stream()
                .map(Pair::getKey)
                .forEach(tile -> {
                    getTextHoverNode(tile).ifPresent(l -> l.setText(getTilesOrderValue(tile)));
                });
    }

    private int getFirstNotSelected() {
        return orderedSelectedTiles.indexOf(
                orderedSelectedTiles.stream()
                        .filter(pair -> !pair.getValue().isClicked())
                        .findFirst().orElseThrow());
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


    protected List<TileSubjectView> getSelectedTilesFromContainer() {
        return getAllTilesFromBox()
                .stream()
                .filter(TileSubjectView::isClicked)
                .toList();
    }

    protected List<TileSubjectView> getAllTilesFromBox() {
        return tileContainers
                .stream()
                .map(Pair::getKey)
                .toList();
    }

    protected void cleanContainers() {
        tileContainers.stream()
                .map(Pair::getValue)
                .forEach(container -> {
                    selectedTilesBox.getChildren().remove(container);
                });

        tileContainers.clear();
    }
}
