package it.polimi.ingsw.client;

import it.polimi.ingsw.server.controller.GameHandler;
import it.polimi.ingsw.server.model.Game;

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

    public void choosingNickAsFirst(BufferedReader in, PrintWriter out) {
        String str = "";
        int count = 0;
        try {
            out.println("Choose a valid nickname: ");
            str = in.readLine();

            while (str.isBlank()) {
                out.println("Invalid nickname");
                out.println("Choose a valid nickname: ");
                str = in.readLine();
                count++;
                if (count > 2) {
                    out.println("Too many bad requests, application is closing");
                    System.exit(-1);
                }
            }
            out.println("Okay, chosen nickname:" + str);
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + client.getInetAddress());
            System.exit(1);
        }
    }

    @Override
    public void run() {

        System.out.println("Inizio comunicazione con il client: ");
        String serverInput = "";
        String str = "";
        int count = 1;
        //ciclo di ricezione dal client e invio di risposta
        try{

            out.println("Choose game type : 1)Single Player" +
                    "     2)MultiPlayer");

            str=in.readLine();//Ricezione gametype
            while(Integer.parseInt(str)!=2 && Integer.parseInt(str)!=1 ) {             //Controllo gametype ( e eventuale nuova ricezione )
                out.println("This option is not valid, choose again");
                out.println("Choose game type : 1)Single Player" +
                        "     2)MultiPlayer");
                str = in.readLine();
                count ++;
                if(count>5) {
                    out.println("Too many bad requests, application is closing");
                    System.exit(-1);
                }
            }

            count = 0;

            if(Integer.parseInt(str)==1){                                                                               //
                out.println("Singleplayer Mode chosen!");

                choosingNickAsFirst(in,out);                // scelta nickname valido
                out.println("Creating a new match ...");
                GameHandler thisGame = new GameHandler(this,1);
                //TODO:spedire al controller l'evento con cui si vuole far cominciare la partita

            }


            if(Integer.parseInt(str)==2){
                out.println("Multiplayer Mode chosen!");
                out.println("Choose an option : 1)Create a new match       2)Join an existing match");
                str = in.readLine();
                count = 0;

                while (Integer.parseInt(str)!= 1 && Integer.parseInt(str)!=2) {
                    out.println("This option is not valid, choose again");
                    out.println("Choose an option : 1)Create a new match       2)Join an existing match");
                    str = in.readLine();
                    count ++;
                    if(count>2) {
                        out.println("Too many bad requests, application is closing");
                        System.exit(-1);
                    }
                }

                //////////////////////////////////////////
                // MULTIPLAYER CREATE MATCH
                if(Integer.parseInt(str) == 1) {
                    out.println("Multiplayer: create a new match");
                    choosingNickAsFirst(in,out);                // scelta nickname valido
                    out.println("Choose the number of player:");
                    // TODO: continuare da qui!
                }
                //////////////////////////////////////////
                // MULTIPLAYER JOIN MATCH
                if(Integer.parseInt(str) == 2) {
                    out.println("Multiplayer: joining an existing match");
                }

            }

        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + client.getInetAddress());
            System.exit(1);
        }
        finally {
            System.out.println("Server closing...");
            try {
                in.close();
                out.close();
                stdIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
