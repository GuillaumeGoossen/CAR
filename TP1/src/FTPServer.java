package TP1.src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Classe responsable de l'écoute des connexions entrantes et de la création de threads pour chaque client
 */

public class FTPServer {
    // Port utilisé par le serveur
    private static final int PORT = 2121;

    public static void main(String[] args) {
        // Création du serveur
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Affichage du port utilisé
            System.out.println("Serveur prêt à accepter des connexions sur le port " + PORT);

            // Boucle infinie pour accepter les connexions des clients
            while (true) {
                // Acceptation de la connexion
                Socket clientSocket = serverSocket.accept();
                // Création d'un thread pour gérer le client
                new Thread(new FTPClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}