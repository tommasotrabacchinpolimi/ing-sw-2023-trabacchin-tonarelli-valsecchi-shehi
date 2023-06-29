package it.polimi.ingsw.net.clientmessage;

import it.polimi.ingsw.net.ClientMessage;
import it.polimi.ingsw.net.ClientDispatcherInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;


  /**
   * Represents a client message indicating that a personal goal has been achieved.
   * It implements the {@link ClientMessage} interface.
   *
   * @author Tommaso Trabacchin
   * @author Melanie Tonarelli
   * @author Emanuele Valsecchi
   * @author Adem Shehi

   */

public class AchievedPersonalGoalNetMessage implements ClientMessage {
      /**
       * Nickname of the player
       */
    private final String nickname;

      /**
       * List of Tiles
       */
    private final List<Coordinate> tiles;

      /**
       * Constructs an AchievedPersonalGoalNetMessage object with the specified nickname and list of achieved tiles.
       *
       * @param nickname The nickname associated with the message.
       * @param tiles    The list of coordinates representing the achieved tiles.
       */
    public AchievedPersonalGoalNetMessage(String nickname, List<Coordinate> tiles) {
        this.nickname = nickname;
        this.tiles = tiles;
    }

      /**
       * Retrieves the nickname associated with the message.
       *
       * @return The nickname.
       */
    public String getNickname() {
        return nickname;
    }

      /**
       * Retrieves the nickname associated with the message.
       *
       * @return The List of Tile {@link Coordinate} that made it possible to achieve PersonalGaol.
       */

    public List<Coordinate> getTiles() {
        return tiles;
    }

      /**
       * Method that allows to dispatch the message to the client
       *
       * @param clientDispatcherInterface is the Handler of the message
       */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
