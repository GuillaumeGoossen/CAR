# CAR
Conception d'Applications Réparties (TP)

## TP1

### Arborescence

Création de deux classes `FTPServer` et `FTPClientHandler` :

#### Classe FTPServer

La classe `FTPServer` a pour rôle de créer et de gérer le serveur FTP.

Elle initialise un serveur en écoutant sur un port spécifique (dans ce cas, le port 2121). Elle attend les connexions des clients en utilisant une boucle infinie. Lorsqu'un client se connecte, elle accepte la connexion et crée un nouveau thread pour gérer cette connexion en utilisant la classe `FTPClientHandler`.

#### Classe FTPClientHandler

La classe `FTPClientHandler` a pour rôle de gérer les interactions avec chaque client connecté. 