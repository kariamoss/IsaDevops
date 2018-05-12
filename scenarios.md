/!\ La connection de Polytech bloque les accès à notre serveur. Il faut donc utiliser une autre connection.

Le script run.sh permet de lancer notre projet "basique".

Lancer client.sh permet d'avoir le client en ligne de commande, tandis que notre interface web se situe à l'adresse http://localhost:8080/j2e-interface-1.0-SNAPSHOT/listEvents.jsf

En ligne de commande, vous pouvez utiliser les commandes suivantes :

- ? / help : donne une liste des commandes utilisables
- createCoordinator prénom nom email motDePasse : Crée un nouveau coordinateur et l'identifie directement.
- createEvent nomDeLEvenement nombreDePersonnes : Crée un nouvel évènement. Il faut être connecté.
- getEvents : Donne la liste des évènements créés.
- getEventWithName nomDeLEvenement : Donne les détail sur l'évenement demandé.
- exit : Ferme l'application.

Dans l'interface jsf, il est possible de voir les évènements créés sur la page d'accueil.
Depuis la page d'accueil, il est possible de créer un coordinateur et un évenement.
La création de l'évenement est soumis à la connection au-préalable du coordinateur.
Comme les salles sont réservées en méthode asynchrone il faut recharger la page d'accueil pour voir la salle attribuée.

Le script stop.sh permet d'arrêter le container docker

Le script run-premium.sh permet de lancer notre projet "premium".

La différence est qu'une facture est envoyée au coordinateur lors de la création d'un évenement.
Le service que nous utilisons ne permet d'envoyer des mails qu'à certaines adresses emails, pré-renseignées.
Nous avons donc directement utilisé une de nos adresses mais ceci n'est visible que pendant la démonstration.

Le script stop-premium permet d'arrêter le container docker