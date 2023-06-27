package it.polimi.ingsw.view.gui.customcomponents.messageview;

/**
 * The `SingleMessageViewPrivacyType` enum represents the privacy types of a single message view in a messaging component.
 * It defines whether a message is public or private.
 */
public enum SingleMessageViewPrivacyType {

    /**
     * Represents a public message, visible to all users.
     */
    PUBLIC {
        @Override
        String getHeaderMessageViewText() {
            //return SPECIAL_CHARACTER + "all";
            return "";
        }
    },

    /**
     * Represents a private message, visible only to specific users.
     */
    PRIVATE {
        @Override
        String getHeaderMessageViewText() {
            return SPECIAL_CHARACTER + "privately to ";
        }
    };

    private static final String SPECIAL_CHARACTER = " • ";

    /**
     * Returns the header message view text associated with the privacy type.
     *
     * @return The header message view text.
     */
    abstract String getHeaderMessageViewText();
}
