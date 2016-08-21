pokemonWorld
============

Projet Syfmony-Android

Installer le projet:

1) Récupérer le dépot

2) Symfony:
- Dans une console, se placer dans le dossier du projet et lancer la commande "composer update"
- Modifier les valeurs du fichier parameters.yml sous app > config pour l'accès à la base de données
- Dans une console lancer les commandes suivantes:
  - php bin/console doctrine:database:create (Création de la base de données)
  - php bin/console doctrine:schema:update --force (Mise à jour de la base de données)
  - php bin/console doctrine:fixtures:load (Insertion des jeux de données dans la base de données)
  - php bin/console cache:clear --env=prod (Clear cache)
  - php bin/console server:run command (Lancement du serveur)

3) Android
- Importer la totalité du projet sous Android Studio
- Modifier la valeur de la clé "apiUrl" présente dans le fichier config.properties du dossier "assets".
Celle ci doit correspondre à l'adresse IP de votre machine suivi du port web. Si vous utiliser le serveur inclus
dans symfony, le port utilisé est le 8000.

Inscrivez-vous pour pouvoir accéder à l'application (écran de lancement).
Les interactions entre dresseurs sont disponibles en passant par la partie "Exploration".
Dézoomer sur la Google maps pour faire apparaître les marqueurs des joueurs adverses qui sont de couleur bleue comparé à celui
du joueur connecté (vous) qui est rouge.
Au clic sur un marqueur, une info-bulle apparaît contenant le nom du joueur adverse, cliquer sur celle-ci pour voir les interactions
disponibles avec ce joueur.
