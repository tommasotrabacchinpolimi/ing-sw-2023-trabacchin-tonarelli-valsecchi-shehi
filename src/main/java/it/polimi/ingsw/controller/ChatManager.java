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

public class ChatManager<R extends ClientInterface> {
    private Controller<R> controller;
    private static final String MESSAGES_FILE = "./src/main/resources/Messages.json";

    /**
     * Creating Gson object parser that exclude fields with {@link JSONExclusionStrategy @ExcludeFromJSON} annotations
     *
     * @see JSONExclusionStrategy
     */
    private static final Gson chatManagerGson = new GsonBuilder().setExclusionStrategies(new JSONExclusionStrategy()).create();

    private PrintWriter pw;

    public  ChatManager(){
        initWriter();
    }

    public ChatManager(Controller<R> controller) {
        this.controller = controller;
        initWriter();
    }

    /**
     * Initialize the {@link #pw print writer} to convert chat in json format
     */
    private void initWriter() {
        pw = this.getPrintWriter();
        assert pw != null;
    }

    /**
     * Add message to the chat in the {@linkplain it.polimi.ingsw.model.State model}
     *
     * @param sender user that has sent the message
     * @param text message body
     * @param receiversNickname players (nickname) that have to receive the message.
     */
    public void sentMessage(R sender, String text,  String[] receiversNickname) {
        List<Player<R>> receivers = new ArrayList<>();

        Arrays.stream(receiversNickname)
                .forEach(rec -> receivers.add(controller.getState().getPlayerFromNick(rec)));

        if(receiversNickname.length != receivers.size()) {
            System.err.println("Error in receivers creation");
        }

        Player<R> senderPlayer = controller.getState().getPlayers()
                .stream()
                .filter(p -> p.getVirtualView() == sender)
                .toList()
                .get(0);

        ChatMessage<R> chatMessage = new ChatMessage<>(
                senderPlayer,
                receivers,
                text
        );

        controller.getState().addMessage(chatMessage);

        storeMessagesAsListObject(controller.getState().getMessages());
    }

    /**
     * Convert every message in chat to json format and save it in a file
     *
     * @param chat complete list of all messages
     */
    public void storeMessagesAsListObject(List<ChatMessage<R>> chat) {
        pw.write(chatManagerGson.toJson(chat));
        pw.flush();
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

    /**
     * Flush and close {@link PrintWriter}
     */
    public void closeFileWriter(){
        pw.flush();
        pw.close();
    }
}
