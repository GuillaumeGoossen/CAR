package TP1.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * Classe qui gère les interactions spécifiques avec chaque client, y compris l'authentification et le traitement des commandes
 */

 public class FTPClientHandler implements Runnable{
    
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public FTPClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run(){
        try {
            // Initialisation des flux de communication
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Envoi du message de bienvenue
            out.println("220 Service ready");
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 }

 