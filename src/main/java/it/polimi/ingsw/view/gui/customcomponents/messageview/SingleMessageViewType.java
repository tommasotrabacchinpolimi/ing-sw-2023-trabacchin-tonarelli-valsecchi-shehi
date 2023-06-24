package it.polimi.ingsw.view.gui.customcomponents.messageview;

import javafx.geometry.Pos;

import java.util.NoSuchElementException;

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

    private SingleMessageViewPrivacyType messageViewPrivacyType;

    SingleMessageViewType() {
        this(SingleMessageViewPrivacyType.PUBLIC);
    }

    SingleMessageViewType(SingleMessageViewPrivacyType messageViewPrivacyType) {
        this.messageViewPrivacyType = messageViewPrivacyType;
    }

    abstract Pos getAlignment();

    public void setMessageViewPrivacyType(SingleMessageViewPrivacyType messageViewPrivacyType) {
        this.messageViewPrivacyType = messageViewPrivacyType;
    }

    public SingleMessageViewPrivacyType getMessageViewPrivacyType() throws NoSuchElementException {

        if(messageViewPrivacyType == null)
            throw  new NoSuchElementException();

        return this.messageViewPrivacyType;
    }
}
