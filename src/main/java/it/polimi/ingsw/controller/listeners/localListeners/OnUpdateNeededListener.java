package it.polimi.ingsw.controller.listeners.localListeners;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.net.RemoteInterface;

public interface OnUpdateNeededListener<R extends ClientInterface> {
    void onUpdateNeededListener(Player<R> player);
}
