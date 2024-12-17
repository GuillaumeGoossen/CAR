package TP1.src;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/*
 * Classe qui gère les interactions spécifiques avec chaque client, y compris l'authentification et le traitement des commandes
 */

 public class FTPClientHandler implements Runnable{
    
    private Socket clientSocket;

    public FTPClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run(){
        try {
            // Initialisation des flux de communication
            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();
            Scanner scanner = new Scanner(in);

            // Envoi du message de bienvenue
            out.write("220 Service Ready \r\n".getBytes());

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

 