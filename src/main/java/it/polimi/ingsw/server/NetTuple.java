package it.polimi.ingsw.server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;

public class NetTuple {
    private String hostname;
    private InetAddress IP;

    public NetTuple(String hostname, InetAddress IP){
        this.hostname=hostname;
        this.IP=IP;
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

}
