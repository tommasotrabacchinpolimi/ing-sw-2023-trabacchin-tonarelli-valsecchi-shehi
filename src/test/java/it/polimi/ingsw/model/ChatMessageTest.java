package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @Test
    void getSender() {
        PersonalGoal goal = new PersonalGoal();
        List<Player> listReceivers = new ArrayList<>();
        List<Player> listOneReceiver = new ArrayList<>();
        Player playerSender = new Player("Melanie", goal);
        Player playerReceiver1 = new Player("Emanuele", goal);
        Player playerReceiver2 = new Player("Tommaso", goal);
        Player playerReceiver3 = new Player("Adem", goal);
        listOneReceiver.add(playerReceiver2);
        listReceivers.add(playerReceiver1);
        listReceivers.add(playerReceiver2);
        listReceivers.add(playerReceiver3);
        ChatMessage message1 = new ChatMessage(playerSender, listReceivers, "Hello World!");
        ChatMessage message2 = new ChatMessage(playerSender, listOneReceiver, "Hi Tommaso!");
        assertEquals(playerSender.toString(), message1.getSender().toString());
        assertEquals(playerSender.toString(), message2.getSender().toString());
    }

    @Test
    void getText() {
        PersonalGoal goal = new PersonalGoal();
        List<Player> listReceivers = new ArrayList<>();
        List<Player> listOneReceiver = new ArrayList<>();
        Player playerSender = new Player("Melanie", goal);
        Player playerReceiver1 = new Player("Emanuele", goal);
        Player playerReceiver2 = new Player("Tommaso", goal);
        Player playerReceiver3 = new Player("Adem", goal);
        listOneReceiver.add(playerReceiver2);
        listReceivers.add(playerReceiver1);
        listReceivers.add(playerReceiver2);
        listReceivers.add(playerReceiver3);
        ChatMessage message1 = new ChatMessage(playerSender, listReceivers, "Hello World!");
        ChatMessage message2 = new ChatMessage(playerSender, listOneReceiver, "Hi Tommaso!");
        assertEquals("Hello World!", message1.getText());
        assertEquals("Hi Tommaso!", message2.getText());
    }

    @Test
    void getReceivers() {
        PersonalGoal goal = new PersonalGoal();
        List<Player> listReceivers = new ArrayList<>();
        List<Player> listOneReceiver = new ArrayList<>();
        Player playerSender = new Player("Melanie", goal);
        Player playerReceiver1 = new Player("Emanuele", goal);
        Player playerReceiver2 = new Player("Tommaso", goal);
        Player playerReceiver3 = new Player("Adem", goal);
        listOneReceiver.add(playerReceiver2);
        listReceivers.add(playerReceiver1);
        listReceivers.add(playerReceiver2);
        listReceivers.add(playerReceiver3);
        ChatMessage message1 = new ChatMessage(playerSender, listReceivers, "Hello World!");
        ChatMessage message2 = new ChatMessage(playerSender, listOneReceiver, "Hi Tommaso!");
        for (int i = 0; i < message2.getReceivers().size(); i++){
            assertEquals(listOneReceiver.get(i).toString(), message2.getReceivers().get(i).toString());
        }
        for (int i = 0; i < message1.getReceivers().size(); i++){
            assertEquals(listReceivers.get(i).toString(), message1.getReceivers().get(i).toString());
        }
    }

    @Test
    void setSender() {
        PersonalGoal goal = new PersonalGoal();
        List<Player> listReceivers = new ArrayList<>();
        List<Player> listOneReceiver = new ArrayList<>();
        Player playerSender = new Player("Melanie", goal);
        Player playerReceiver1 = new Player("Emanuele", goal);
        Player playerReceiver2 = new Player("Tommaso", goal);
        Player playerReceiver3 = new Player("Adem", goal);
        listOneReceiver.add(playerReceiver2);
        ChatMessage message = new ChatMessage(playerSender, listReceivers, "Hello World!");
        message.setSender(playerReceiver1);
        assertEquals(playerReceiver1.toString(), message.getSender().toString());
    }

    @Test
    void setReceivers() {
        PersonalGoal goal = new PersonalGoal();
        List<Player> listReceivers = new ArrayList<>();
        Player playerSender = new Player("Melanie", goal);
        Player playerReceiver1 = new Player("Emanuele", goal);
        Player playerReceiver2 = new Player("Tommaso", goal);
        Player playerReceiver3 = new Player("Adem", goal);
        listReceivers.add(playerReceiver1);
        listReceivers.add(playerReceiver2);
        ChatMessage message = new ChatMessage(playerSender, listReceivers, "Hello World!");
        message.addReceiver(playerReceiver3);
        for (int i = 0; i < message.getReceivers().size()-1; i++){
            assertEquals(listReceivers.get(i).toString(), message.getReceivers().get(i).toString());
        }
        assertEquals(playerReceiver3.toString(), message.getReceivers().get(message.getReceivers().size()-1).toString());
    }

    @Test
    void setToAll() {
        PersonalGoal goal = new PersonalGoal();
        List<Player> listReceivers = new ArrayList<>();
        List<Player> list = new ArrayList<>();
        Player playerSender = new Player("Melanie", goal);
        Player playerReceiver1 = new Player("Emanuele", goal);
        Player playerReceiver2 = new Player("Tommaso", goal);
        Player playerReceiver3 = new Player("Adem", goal);
        ChatMessage message = new ChatMessage(playerSender, list, "Hello World!");
        listReceivers.add(playerReceiver1);
        listReceivers.add(playerReceiver2);
        listReceivers.add(playerReceiver3);
        message.setToAll(listReceivers);
        for(int i = 0; i < message.getReceivers().size(); i++){
            assertEquals(listReceivers.get(i).toString(), message.getReceivers().get(i).toString());
        }
    }

    @Test
    void setText() {
        PersonalGoal goal = new PersonalGoal();
        List<Player> listOneReceiver = new ArrayList<>();
        Player playerSender = new Player("Melanie", goal);
        Player playerReceiver2 = new Player("Tommaso", goal);
        listOneReceiver.add(playerReceiver2);
        ChatMessage message = new ChatMessage(playerSender, listOneReceiver, "Hi Tommaso!");
        message.setText("Changed Message");
        assertEquals("Changed Message", message.getText());
    }
}