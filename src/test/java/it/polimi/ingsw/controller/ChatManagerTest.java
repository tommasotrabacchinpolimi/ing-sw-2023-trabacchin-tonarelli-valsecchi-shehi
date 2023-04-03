package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ChatMessage;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ChatManagerTest {
    private static final Random RANDOM = new Random();

    @Test
    void sentMessage() {
    }

    @Test
    void storeMessagesAsListObject() {
        ChatManager chatManager = new ChatManager();

        chatManager.storeMessagesAsListObject(createRandomChat(RANDOM.nextInt(1,5)));
    }

    private List<ChatMessage> createRandomChat(int messagesNumber){
        List<ChatMessage> chat = new ArrayList<ChatMessage>();

        for(int i = 0; i < messagesNumber; ++i) {
            chat.add(new ChatMessage(
                    new Player("nick"),
                    getReceiversList(RANDOM.nextInt(1,4)),
                    "message content " + RANDOM.nextInt()
            ));
        }

        return chat;
    }

    private List<Player> getReceiversList(int num) {
        List<Player> receivers = new ArrayList<Player>();

        if(num >= 4)
            System.err.println("Too many players");

        for(int i = 0; i < num; ++i) {
            receivers.add(new Player("nick" + (i + 1)));
        }

        return receivers;
    }
}