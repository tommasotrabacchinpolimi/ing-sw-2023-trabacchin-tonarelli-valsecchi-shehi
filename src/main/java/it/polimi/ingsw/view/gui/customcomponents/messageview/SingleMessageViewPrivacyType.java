package it.polimi.ingsw.view.gui.customcomponents.messageview;

public enum SingleMessageViewPrivacyType {
    PUBLIC {
        @Override
        String getHeaderMessageViewText() {
            //return SPECIAL_CHARACTER + "all";
            return "";
        }
    },
    PRIVATE {
        @Override
        String getHeaderMessageViewText() {
            return SPECIAL_CHARACTER + "privately to ";
        }
    };

    private static final String SPECIAL_CHARACTER = " • ";

    abstract String getHeaderMessageViewText();
}
