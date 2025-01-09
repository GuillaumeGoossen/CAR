package TP1.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Classe qui gère les interactions spécifiques avec chaque client, y compris l'authentification et le traitement des commandes
 */

public class FTPClientHandler implements Runnable {

    private Socket clientSocket;
    private static final Map<String, String> users = new HashMap<>();
    private ServerSocket dataSocket;
    private Socket dataConnection;
    private String transferType = "A"; // Default to ASCII mode

    static {
        // Initialisation des logins et mots de passe autorisés
        users.put("miage", "car");
        users.put("admin", "admin");
    }

    public FTPClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        Scanner scanner = null;
        try {
            // Initialisation des flux de communication
            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();
            scanner = new Scanner(in);

            // Envoi du message de bienvenue
            out.write("220 Service Ready \r\n".getBytes());

            // Demande du nom d'utilisateur
            String login = scanner.nextLine();
            System.out.println(login);

            // Extraction du nom d'utilisateur
            String username = login.substring("USER ".length());

            // Connexion avec miage et car
            if (users.containsKey(username)) {
                out.write("331 User name ok \r\n".getBytes());
                String mdp = scanner.nextLine();
                System.out.println(mdp);

                if (users.get(username).equals(mdp.substring("PASS ".length()))) {
                    out.write("230 User logged in \r\n".getBytes());

                    while (true) {
                        String command = scanner.nextLine();
                        System.out.println(command);

                        if ("QUIT".equalsIgnoreCase(command)) {
                            out.write("221 Connexion FTP fermée \r\n".getBytes());
                            clientSocket.close();
                            break;
                        } else if (command.equalsIgnoreCase("SYST")) {
                            out.write("215 UNIX Type: L8 \r\n".getBytes());
                        } else if (command.equalsIgnoreCase("FEAT")) {
                            out.write("211 Features:\r\n".getBytes());
                        } else if (command.equalsIgnoreCase("EPSV")) {
                            dataSocket = new ServerSocket(0); // Port 0 pour obtenir un port libre
                            int dataPort = dataSocket.getLocalPort();
                            out.write(("229 Entering Extended Passive Mode (|||" + dataPort + "|)\r\n").getBytes());
                        } else if (command.startsWith("RETR ")) {
                            String fileName = command.substring("RETR ".length());
                            File file = new File(fileName);

                            if (file.exists() && !file.isDirectory()) {
                                out.write(("150 Opening data connection for " + fileName + "\r\n").getBytes());
                                if (dataSocket != null) {
                                    dataConnection = dataSocket.accept();
                                }
                                FileInputStream fis = new FileInputStream(file);
                                OutputStream dataOut = dataConnection.getOutputStream();
                                byte[] buffer = new byte[1024];
                                int bytesRead;

                                while ((bytesRead = fis.read(buffer)) != -1) {
                                    dataOut.write(buffer, 0, bytesRead);
                                }

                                fis.close();
                                dataOut.close();
                                dataConnection.close();
                                out.write("226 Transfer complete \r\n".getBytes());
                            } else {
                                out.write("550 File not found \r\n".getBytes());
                            }
                        } else if (command.startsWith("LIST")) {
                            out.write("150 Here comes the directory listing.\r\n".getBytes());
                            if (dataSocket != null) {
                                dataConnection = dataSocket.accept();
                            }
                            File directory = new File(".");
                            File[] files = directory.listFiles();
                            OutputStream dataOut = dataConnection.getOutputStream();
                            for (File file : files) {
                                dataOut.write((file.getName() + "\r\n").getBytes());
                            }
                            dataOut.close();
                            dataConnection.close();
                            out.write("226 Directory send OK.\r\n".getBytes());
                        }

                        else {
                            out.write("502 Command not implemented \r\n".getBytes());
                        }
                    }
                } else {
                    // Si le mot de passe est incorrect
                    out.write("530 Invalid password \r\n".getBytes());
                    clientSocket.close();
                }
            } else {
                // Si l'utilisateur n'existe pas
                out.write("530 Invalid username \r\n".getBytes());
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}