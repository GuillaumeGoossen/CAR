# CAR
Conception d'Applications Réparties (TP)

## TP1

### Arborescence

Création de deux classes `FTPServer` et `FTPClientHandler` :

#### Classe FTPServer

La classe `FTPServer` a pour rôle de créer et de gérer le serveur FTP.

Elle initialise un serveur en écoutant sur un port spécifique (dans ce cas, le port 2121). Elle attend les connexions des clients en utilisant une boucle infinie. Lorsqu'un client se connecte, elle accepte la connexion et crée un nouveau thread pour gérer cette connexion en utilisant la classe `FTPClientHandler`.

#### Classe FTPClientHandler

La classe gère les interactions avec un client FTP. Elle authentifie l'utilisateur en vérifiant le nom d'utilisateur et le mot de passe, puis traite les commandes FTP telles que `QUIT`, `SYST` et `FEAT`. Si la commande `QUIT` est reçue, elle ferme la connexion.

#### Codes de réponse FTP utilisés

- `220` : Service prêt
- `331` : Nom d'utilisateur correct
- `230` : Utilisateur connecté
- `221` : Connexion fermée
- `215` : Type de système
- `211` : Fonctionnalités
- `502` : Commande non implémentée
- `530` : Nom d'utilisateur ou mot de passe incorrect