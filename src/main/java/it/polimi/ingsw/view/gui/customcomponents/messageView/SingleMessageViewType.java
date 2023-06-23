package it.polimi.ingsw.view.gui.customcomponents.messageView;

import javafx.geometry.Pos;

public enum SingleMessageViewType {
    SENT {
        @Override
        Pos getAlignment() {
            return Pos.CENTER_RIGHT;
        }
    },

    /**
     *
     */
    RECEIVED {
        @Override
        Pos getAlignment() {
            return Pos.CENTER_LEFT;
        }
    };

    abstract Pos getAlignment();
}
