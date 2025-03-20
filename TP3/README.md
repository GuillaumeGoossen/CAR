# TP3 - MapReduce Word Count avec Akka et Spring Boot

Ce projet implémente une application de comptage de mots en utilisant le paradigme MapReduce avec Akka et Spring Boot. L'application permet de charger un fichier texte, de compter les occurrences des mots et d'interagir avec un système d'acteurs.

### Fonctionnalités

1. Uploader un fichier texte
Utilisez le formulaire sur la page d'accueil pour charger un fichier texte.
Chaque ligne du fichier sera distribuée à un acteur Mapper, qui transmettra les mots à un acteur Reducer pour comptage.
2. Rechercher un mot
Entrez un mot dans le champ de recherche et cliquez sur "Rechercher".
L'application interrogera les acteurs pour obtenir le nombre d'occurrences du mot.
3. Arrêter le système
Cliquez sur le bouton "Arrêter le système" pour arrêter le système d'acteurs.
Attention : Une fois le système arrêté, il ne sera plus possible d'utiliser l'application sans redémarrer le projet.

### Important
Initialisation des Acteurs
Les acteurs (Mapper et Reducer) sont initialisés automatiquement au lancement de l'application.
Si vous cliquez sur "Arrêter le système", les acteurs seront terminés et ne pourront plus traiter de nouvelles requêtes. Vous devrez redémarrer l'application pour réinitialiser les acteurs.

### Exemple d'Utilisation
##### Charger un fichier texte :
- Cliquez sur "Uploader un fichier texte".
- Sélectionnez un fichier .txt contenant des lignes de texte.

##### Rechercher un mot :
- Entrez un mot dans le champ de recherche.
- Cliquez sur "Rechercher" pour afficher le nombre d'occurrences.

##### Arrêter le système :
- Cliquez sur "Arrêter le système" pour terminer les acteurs.
- Note : Après cette action, vous devrez redémarrer l'application pour continuer à l'utiliser.