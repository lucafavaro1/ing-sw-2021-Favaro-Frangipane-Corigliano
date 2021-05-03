package it.polimi.ingsw.server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Class representing a couple of IP - Hostname to identify a player
 */
public class NetTuple {
    private String hostname;
    private InetAddress IP;
    private boolean isInLobby;

    /**
     * Simple constructor a NetTuple
     * @param hostname the name of the host connecting
     * @param IP the IPAddress of the host connecting
     */
    public NetTuple(String hostname, InetAddress IP){
        this.hostname=hostname;
        this.IP=IP;
        this.isInLobby=false;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public InetAddress getIP() {
        return IP;
    }

    public void setIP(Inet4Address IP) {
        this.IP = IP;
    }

    public boolean isInLobby() {
        return isInLobby;
    }

    public void setInLobby(boolean inLobby) {
        isInLobby = inLobby;
    }
}
