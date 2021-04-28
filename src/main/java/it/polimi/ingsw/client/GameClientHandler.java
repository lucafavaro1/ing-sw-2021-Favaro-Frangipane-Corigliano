package it.polimi.ingsw.client;

import it.polimi.ingsw.server.controller.GameHandler;
import it.polimi.ingsw.server.model.Game;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

//Classe che realizza la gestione delle connessioni dei client al server, permettendo la connessione multipla tramite interfaccia Runnable

/**
 * TODO: Per ora si può inviare un solo messaggio alla volta, quindi la corrispondenza è semplicemente 1 a 1, implementare possibilità di inviare più messaggi?(da discutere)
 * TODO: Implementare il controllo della lobby (se piena o meno)
 * TODO: Implementare controllo del nickname con quelli già presenti in lobby
 * TODO: Implementare la creazione del match e l'attesa dei giocatori in lobby pre-partita
 */

public class GameClientHandler implements Runnable{
    private Socket client;
    private BufferedReader in, stdIn;
    private PrintWriter out;

    private String invOption ="This option is not valid, choose again";
    private String lobbyIsFull = "Lobby is full, cannot join";
    private String gameTypeStr ="Choose game type : 1)Single Player" +
            "     2)MultiPlayer";
    private String matchTypeStr ="Choose an option : 1)Create a new match       2)Join an existing match";
    private String numOfPlayersStr ="Choose the number of players:";
    private String matchIDStr ="Insert a valid Match ID (1 to 9)";



    public GameClientHandler(Socket clientSocket) throws IOException{
        this.client=clientSocket;
        in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        out=new PrintWriter(client.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void chooseNick(BufferedReader in, PrintWriter out, boolean first) {
        String str = "";
        int count = 0;

        try {

            str = in.readLine();


            while (str.isBlank()) {
                out.println("Invalid nickname");
                out.println("Choose a valid nickname: ");
                str = in.readLine(); //ricezione nickname
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

    public void invalidOption(String error, String again, int tries, String str, int count, BufferedReader in, PrintWriter out) throws IOException {
        out.println(error);
        out.println(again);
        str = in.readLine();
        if(count>tries) {
            out.println("Too many bad requests, application is closing");
            System.exit(-1);
        }

    }


    public boolean isJoinable(int x){
        if(x>5) return false;
        return true;
    }


    public boolean isFilled(int x){
        return true;
    }
    @Override
    public void run() {

        System.out.println("Inizio comunicazione con il client: ");
        String serverInput = "";
        String str = "";
        int count = 1;
        //ciclo di ricezione dal client e invio di risposta
        try{

            out.println("Choose game type : 1)Single Player" +                                                          //AskGameType
                    "     2)MultiPlayer");

            str=in.readLine();//Ricezione gametype

            while(Integer.parseInt(str)!=2 && Integer.parseInt(str)!=1 ) {             //Controllo gametype ( e eventuale nuova ricezione )
                invalidOption(invOption, gameTypeStr, 3, str, count, in, out);
                count++;
            }

            count = 0;

            if(Integer.parseInt(str)==1){
                out.println("Singleplayer Mode chosen!");

                chooseNick(in,out, true);                // scelta nickname valido
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
                    invalidOption(invOption, matchTypeStr, 3, str, count, in, out);
                    count++;
                }

                //////////////////////////////////////////
                // MULTIPLAYER CREATE MATCH
                if(Integer.parseInt(str) == 1) {
                    out.println("Multiplayer: create a new match");
                    chooseNick(in,out, true);                // scelta nickname valido
                    out.println("Choose the number of player:");

                    str=in.readLine();
                    count = 0;
                    while(Integer.parseInt(str)<1 || Integer.parseInt(str)>4){
                        invalidOption(invOption, numOfPlayersStr, 3, str, count, in, out);
                        count++;
                    }
                    out.println("Multiplayer: creating match...");


                }
                //////////////////////////////////////////
                // MULTIPLAYER JOIN MATCH
                if(Integer.parseInt(str) == 2) {
                    out.println("Multiplayer: joining an existing match");
                    out.println("Insert a valid Match ID (1 to 9): ");
                    str=in.readLine();                                                                                  //ricezione matchID
                    count = 0;
                    while(Integer.parseInt(str)<1 || Integer.parseInt(str)>9){                                          //controllo matchID
                        invalidOption(invOption, matchIDStr, 3, str, count, in, out);
                        count++;
                    }
                    out.println("Lobby " + str);
                    while(!isJoinable(Integer.parseInt(str))){                                                          //controllo lobby
                        invalidOption(lobbyIsFull, matchIDStr, 3, str, count, in, out);
                        count++;
                    }
                    out.println("Successfully joined lobby "+ str);
                    boolean lobbyFilled= isFilled(Integer.parseInt(str));                                               //controlla se la lobby è completa
                    chooseNick(in,out, true);
                    //checkNickname();
                    if(lobbyFilled) out.println("Starting match...");
                    out.println("Waiting for other players to join...");
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
