package it.polimi.ingsw.view.gui.customcomponents.messageview;

import javafx.geometry.Pos;

import java.util.NoSuchElementException;

/**
 * The `SingleMessageViewType` enum represents different types of single message views in the message view component of the GUI.
 * Each type corresponds to a specific alignment for the message view, which can be either sent or received.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public enum SingleMessageViewType {

    /**
     * Type representing a sent message view aligned to the center-right.
     */
    SENT {
        @Override
        Pos getAlignment() {
            return Pos.CENTER_RIGHT;
        }
    },

    /**
     * Type representing a received message view aligned to the center-left.
     */
    RECEIVED {
        @Override
        Pos getAlignment() {
            return Pos.CENTER_LEFT;
        }
    };

    /**
     * The privacy type of the message view.
     *
     * @see SingleMessageViewPrivacyType
     */
    private SingleMessageViewPrivacyType messageViewPrivacyType;

    /**
     * Constructs a `SingleMessageViewType` with the default privacy type of PUBLIC.
     */
    SingleMessageViewType() {
        this(SingleMessageViewPrivacyType.PUBLIC);
    }

    /**
     * Constructs a `SingleMessageViewType` with the specified privacy type.
     *
     * @param messageViewPrivacyType The privacy type of the message view.
     */
    SingleMessageViewType(SingleMessageViewPrivacyType messageViewPrivacyType) {
        this.messageViewPrivacyType = messageViewPrivacyType;
    }

    /**
     * Returns the alignment of the message view based on the type.
     *
     * @return The alignment of the message view.
     */
    abstract Pos getAlignment();

    /**
     * Sets the privacy type of the message view.
     *
     * @param messageViewPrivacyType The privacy type of the message view.
     */
    public void setMessageViewPrivacyType(SingleMessageViewPrivacyType messageViewPrivacyType) {
        this.messageViewPrivacyType = messageViewPrivacyType;
    }

    /**
     * Returns the privacy type of the message view.
     *
     * @return The privacy type of the message view.
     * @throws NoSuchElementException if the privacy type is not set.
     */
    public SingleMessageViewPrivacyType getMessageViewPrivacyType() throws NoSuchElementException {

        if (messageViewPrivacyType == null)
            throw new NoSuchElementException();

        return this.messageViewPrivacyType;
    }
}
