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

            // Demande du nom d'utilisateur
            String login = scanner.nextLine();
            System.out.println(login);

            // Connexion avec miage et car
            if (login.substring("USER ".length()).equals("miage")) {
                out.write("331 User name ok \r\n".getBytes());
                String mdp = scanner.nextLine();
                System.out.println(mdp);
                if (mdp.substring("PASS ".length()).equals("car")) {
                    out.write("230 User logged in \r\n".getBytes());
                }
                
            } else {
                // Si l'utilisateur n'existe pas
                out.write("530 User name ko \r\n".getBytes());
                out.write("255 Exit \r\n".getBytes());
                clientSocket.close();
            }

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

 