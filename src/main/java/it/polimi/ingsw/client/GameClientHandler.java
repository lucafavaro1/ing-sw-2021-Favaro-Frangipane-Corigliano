package it.polimi.ingsw.client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

//Classe che realizza la gestione delle connessioni dei client al server, permettendo la connessione multipla tramite interfaccia Runnable

/**
 * TODO: Per ora si può inviare un solo messaggio alla volta, quindi la corrispondenza è semplicemente 1 a 1, implementare possibilità di inviare più messaggi?(da discutere)
 *
 */

public class GameClientHandler implements Runnable{
    private Socket client;
    private BufferedReader in, stdIn;
    private PrintWriter out;



    public GameClientHandler(Socket clientSocket) throws IOException{
        this.client=clientSocket;
        in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        out=new PrintWriter(client.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run() {

        System.out.println("Inizio comunicazione con il client: ");
        String serverInput = "";
        String str = "";
        //ciclo di ricezione dal client e invio di risposta
        try{
            while (true) {
                str = in.readLine();
                System.out.println("Client message: " + str);
                if (str.equals("END")) break;
                serverInput = stdIn.readLine();
                out.println(serverInput);
                if (serverInput.equals("END")) break;
            }
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + client.getInetAddress());
            System.exit(1);
        }
        finally{
            System.out.println("Server closing...");
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();

        }
    }
}
