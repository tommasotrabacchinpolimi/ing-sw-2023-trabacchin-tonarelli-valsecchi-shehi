package it.polimi.ingsw.view.tui.page;

import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

/**
 * Class that represents a chat page in the text-based user interface (TUI).
 * It displays the player's chat messages in the TUI.
 */
public class ChatPage extends Page{
    /**
     * Attribute that represents the output stream.
     */
    private PrintStream out;

    /**
     * Constructs a new ChatPage object.
     *
     * @param tui the TUI object associated with the page
     */
    public ChatPage(TUI tui) {
        super(tui);
        this.out = tui.getPrintStream();
    }

    /**
     * Displays the chat page in the TUI.
     */
    @Override
    public void show() {
        getModel().setUnreadMessages(false);
        System.out.print(colorize("                                                                     ", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        System.out.print(colorize("MY SHELFIE: CHAT", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        System.out.println(colorize("                                                                     ", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        List<Triple<String, List<String>, String>> messages = getModel().getMessages();
        if(messages == null){
            System.out.println("There aren't any messages yet.");
        } else {

            out.println("NUMBER OF MESSAGES: " + messages.size());

            for (Triple<String, List<String>, String> message : messages) {
                if (message.getSecond().contains(getModel().getThisPlayer()) || message.getFirst().equals(getModel().getThisPlayer())) {
                    String sender = message.getFirst();
                    String priv = privateOrPublicMessage(message.getSecond());
                    if (sender.equals(getModel().getThisPlayer())) {
                        sender = "You";
                        if (priv.equals("(private)")) {
                            priv = "(private with " + message.getSecond().get(0) + ") " ;
                        }
                    }
                    out.println(colorize(sender + " " + priv, MyShelfieAttribute.BLUE_TEXT()) + " " + message.getThird());
                }
            }
        }
        out.println(colorize("<--There aren't any more messages. If you want to return to the homepage, please type 'exit'.-->", MyShelfieAttribute.BOLD()));
    }

    /**
     * Determines if a message is private or public based on the number of receivers.
     *
     * @param receivers the list of receivers for the message
     * @return the string indicating if the message is private or public
     */
    private String privateOrPublicMessage(List<String> receivers){
        if(receivers.size() == 1) return "(private)" ;
        return "(public)" ;
    }

    /**
     * Callback method called when a new message is received.
     * This method displays the updated chat page in the TUI.
     */
    @Override
    public void onNewMessage() {
        this.show();
    }
}
