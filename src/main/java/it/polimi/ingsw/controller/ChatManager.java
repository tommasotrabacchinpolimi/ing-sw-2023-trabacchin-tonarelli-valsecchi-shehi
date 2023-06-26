package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.ChatMessage;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 19/04/2023
 */
public class ChatManager {
    private Controller controller;
    private static final String MESSAGES_FILE = "./src/main/resources/it.polimi.ingsw/Messages.json";

    /**
     * Creating Gson object parser that exclude fields with {@link JSONExclusionStrategy @ExcludeFromJSON} annotations
     *
     * @see JSONExclusionStrategy
     */
    private static final Gson chatManagerGson = new GsonBuilder().setExclusionStrategies(new JSONExclusionStrategy()).create();

    private PrintWriter printWriter;

    public  ChatManager(){
        initWriter();
    }

    public ChatManager(Controller controller) {
        this.controller = controller;
        //initWriter();
    }

    /**
     * Initialize the {@link #printWriter print writer} to convert chat in json format
     */
    private void initWriter() {
        printWriter = this.getPrintWriter();
        assert printWriter != null;
    }

    /**
     * Add message to the chat in the {@linkplain it.polimi.ingsw.model.State model}
     *
     * @param sender user that has sent the message
     * @param text message body
     * @param receiversNickname players (nickname) that have to receive the message.
     */
    public void sentMessage(ClientInterface sender, String text,  String[] receiversNickname) {
        List<Player> receivers = new ArrayList<>();

        Arrays.stream(receiversNickname)
                .forEach(rec -> receivers.add(controller.getState().getPlayerFromNick(rec)));

        if(receiversNickname.length != receivers.size()) {
            System.err.println("Error in receivers creation");
        }

        Player senderPlayer = controller.getState().getPlayers()
                .stream()
                .filter(p -> p.getVirtualView() == sender)
                .toList()
                .get(0);

        ChatMessage chatMessage = new ChatMessage(
                senderPlayer,
                receivers,
                text
        );

        controller.getState().addMessage(chatMessage);

        //storeMessagesAsListObject(controller.getState().getMessages());
    }

    /**
     * Convert every message in chat to json format and save it in a file
     *
     * @param chat complete list of all messages
     */
    public void storeMessagesAsListObject(List<ChatMessage> chat) {
        printWriter.write(chatManagerGson.toJson(chat));
        printWriter.flush();
    }

    /**
     * Create a {@link PrintWriter} to write json file
     *
     * @return an object that allows to write on file
     */
    private PrintWriter getPrintWriter(){
        try {
            return new PrintWriter(MESSAGES_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
