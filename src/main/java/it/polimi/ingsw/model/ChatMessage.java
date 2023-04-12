package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net.RemoteInterface;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
* ChatMessage is a class that represents a message sent from a {@link Player player} to another
 * or to all {@link Player players} in the game.
 *
 * @author Melanie Tonarelli
 * @version 1.0, 15/03/23
 * @see Player
 */
public class ChatMessage<R extends ClientInterface> implements Serializable {
    @Serial
    private static final long serialVersionUID = 82642348L;

    /**
     * The {@link Player} that sends the message.
     *
     * @see Player
     */
    private Player<R> sender;
    /**
     * The list of {@link Player} that receives the message.
     *
     * @apiNote The attribute <code>receivers</code> must not be empty and can only contain either
     * a player other than the {@code sender} or the list of all players including the {@code sender}.
     * @see Player
     */
    private final List<Player<R>> receivers;

    /**
     * The {@link String} containing the text of the message.
     */
    private String text;

    /**
     * Constructor that sets the fields of the class to the parameter passed.
     * @param sender The {@link Player player} that will be set to sender of the message.
     * @param receivers The list of {@link Player player} that will receive the message.
     * @param text The text of the message.
     *
     * @apiNote The parameter <code>receivers</code> must not be empty and
     * can only contain either a player other than the {@code sender} or the list of all players including the {@code sender}.
     * @see Player
     */
    public ChatMessage(Player<R> sender, List<Player<R>> receivers, String text) {
        this.sender = sender;
        this.receivers = receivers;
        this.text = text;
    }

    /**
     * Method that gets the {@code sender} of the message.
     * @return The player who is the sender of the message.
     *
     * @see Player
     */
    public Player<R> getSender() {
        return sender;
    }

    /**
     * Method that gets the text of the message.
     * @return A string representing the text of the message.
     */
    public String getText() {
        return text;
    }

    /**
     * Method that gets the {@code receivers} of the message.
     * @return A list of players that are the receivers of the message.
     *
     * @apiNote The returned value is never null. The method returns either a list of one player or a list of all players.
     * @see Player
     */
    public List<Player<R>> getReceivers() {
        return receivers;
    }

    /**
     * Method that sets the {@code sender} of the message.
     * @param sender The player that will be set to {@code sender} of the message.
     *
     * @see Player
     */
    public void setSender(Player<R> sender) {
        this.sender = sender;
    }

    /**
     * Method that adds a {@link Player player} to the list {@link ChatMessage#receivers}.
     * @param receiver The {@link Player} that will be added to the list {@link ChatMessage#receivers}.
     *
     * @see Player
     */
    public void addReceiver(Player<R> receiver) {
        this.receivers.add(receiver);
    }

    /**
     * Method that adds a list of {@link Player players} to the list {@link ChatMessage#receivers}.
     * @param to The list of {@link Player players} that will be added to the list {@link ChatMessage#receivers}.
     *
     * @see Player
     */
    public void setToAll(List<Player<R>> to){
        this.receivers.addAll(to);
    }

    /**
     * Method that sets the {@link ChatMessage#text} of the message.
     * @param text The {@link String} that will be set to the attribute {@link ChatMessage#text}.
     */
    public void setText(String text) {
        this.text = text;
    }
}
