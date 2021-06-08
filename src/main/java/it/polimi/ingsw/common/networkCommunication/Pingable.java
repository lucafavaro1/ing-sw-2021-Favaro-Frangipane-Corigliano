package it.polimi.ingsw.common.networkCommunication;

import it.polimi.ingsw.common.Events.EventHandler;

import static java.lang.Thread.sleep;

/**
 * abstract class that deals with the logic of ping and notify the disconnection
 */
public abstract class Pingable implements EventHandler {
    protected boolean connected = true;
    private static final int maxFails = 3;
    private boolean check = false;
    protected int nPingFails = 0;
    private boolean pinged = false;

    /**
     * starting a thread that checks if we have been pinged
     */
    protected void checkConnection() {
        // if such a thread already exists don't create the thread
        if (check)
            return;

        check = true;

        while (check) {
            try {
                sleep(1000);
                sendPing();

                // if we have been pinged, we reset nPingFails, otherwise we increase nPingFails
                if (pinged) {
                    pinged = false;
                    nPingFails = 0;
                } else {
                    nPingFails++;
                }

                // if too many fails, we notify the disconnection and wait the reconnection
                if (nPingFails >= maxFails && connected) {
                    notifyDisconnection();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method that sends a ping to the other end of the socket
     */
    protected abstract void sendPing();

    /**
     * method that deals with the disconnection: the client terminates meanwhile the server waits for a reconnection
     */
    protected abstract void notifyDisconnection();

    /**
     * method called by a ping from the other end: notifies that the other end is alive
     */
    public void ping() {
        pinged = true;
    }
}
