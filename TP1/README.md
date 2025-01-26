# CAR
Conception d'Applications Réparties (TP)

## TP1

### Arborescence

Création de deux classes `FTPServer` et `FTPClientHandler` :

#### Classe FTPServer

La classe `FTPServer` a pour rôle de créer et de gérer le serveur FTP.

Elle initialise un serveur en écoutant sur un port spécifique (dans ce cas, le port 2121). Elle attend les connexions des clients en utilisant une boucle infinie. Lorsqu'un client se connecte, elle accepte la connexion et crée un nouveau thread pour gérer cette connexion en utilisant la classe `FTPClientHandler`.

#### Classe FTPClientHandler

La classe `FTPClientHandler` gère les interactions avec un client FTP. Elle authentifie l'utilisateur en vérifiant le nom d'utilisateur et le mot de passe, puis traite les commandes FTP suivantes :

- `USER <username>` : Spécifie le nom d'utilisateur pour l'authentification.
- `PASS <password>` : Spécifie le mot de passe pour l'authentification.
- `QUIT` : Ferme la connexion.
- `SYST` : Retourne le type de système.
- `FEAT` : Liste les fonctionnalités supportées par le serveur.
- `EPSV` : Active le mode passif étendu.
- `RETR <filename>` : Télécharge un fichier depuis le serveur.
- `LIST` : Liste les fichiers et répertoires dans le répertoire courant.
- `CWD <directory>` : Change le répertoire de travail.

La classe utilise les codes de réponse FTP suivants pour communiquer l'état des opérations :

- `220` : Service prêt
- `331` : Nom d'utilisateur correct
- `230` : Utilisateur connecté
- `221` : Connexion fermée
- `215` : Type de système
- `211` : Fonctionnalités
- `229` : Mode passif étendu activé
- `150` : Ouverture de la connexion de données
- `226` : Transfert terminé
- `550` : Fichier ou répertoire non trouvé
- `502` : Commande non implémentée
- `530` : Nom d'utilisateur ou mot de passe incorrect

La classe utilise un répertoire courant (`currentDirectory`) pour gérer les opérations de fichiers et de répertoires. Ce répertoire est initialisé avec le chemin canonique du répertoire courant au moment de la création de l'objet `FTPClientHandler`.

### Extension du Programme FTP

Pour étendre le programme et supporter d'autres commandes et mécanismes du protocole FTP, nous pouvons ajouter des blocs `else if` supplémentaires dans la méthode `run` de la classe [`FTPClientHandler`](src/FTPClientHandler.java). Par exemple, pour les commandes `DELE` (supprimer un fichier), `MKD` (créer un répertoire), et `RMD` (supprimer un répertoire), nous pouvons ajouter des blocs de code qui effectuent les opérations de système de fichiers correspondantes. 

### Démonstration du Fonctionnement du Serveur

1. **Démarrage du Serveur FTP** :
   - Placez-vous dans le répertoire racine du projet (`CAR/TP1`).
   - Compilez les classes Java :
     ```sh
     javac -d classes src/FTPServer.java src/FTPClientHandler.java
     ```
   - Lancez le serveur FTP :
     ```sh
     java -cp classes TP1.src.FTPServer
     ```
   - Le serveur écoute maintenant les connexions sur le port 2121.

2. **Connexion au Serveur FTP** :
   - Utilisez un client FTP pour vous connecter au serveur.
   - Exemple avec le client FTP en ligne de commande :
     ```sh
     ftp localhost 2121
     ```
   - Entrez le nom d'utilisateur et le mot de passe lorsque vous y êtes invité :
     ```
     Name (localhost:username): miage
     Password: car
     ```

3. **Utilisation des Commandes FTP** :
   - Une fois connecté, vous pouvez utiliser les commandes FTP supportées par le serveur.

4. **Exemple de Session FTP** :
   - Voici un exemple de session FTP :
     ```
     $ ftp localhost 2121
     Connected to localhost.
     220 Service Ready
     Name (localhost:username): miage
     331 User name ok
     Password: car
     230 User logged in
     Remote system type is UNIX.
     Using binary mode to transfer files.
     ftp> ls
     229 Entering Extended Passive Mode (|||35221|)
     150 Here comes the directory listing.
     class
     classes
     README.md
     src
     test.txt
     226 Directory send OK.
     ftp> cd .
     250 Directory successfully changed to /mnt/c/Users/ggoos/Documents/M1S2/TP/CAR/TP1
     ftp> cd ..
     250 Directory successfully changed to /mnt/c/Users/ggoos/Documents/M1S2/TP/CAR
     ftp> cd TP1
     250 Directory successfully changed to /mnt/c/Users/ggoos/Documents/M1S2/TP/CAR/TP1
     ftp> quit
     221 Connexion FTP fermée
     ```