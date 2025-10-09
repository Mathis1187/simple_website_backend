# Backend du Site Web Simple

## Description
Le Backend du Site Web Simple est une application backend basée sur Java et construite avec le framework Spring Boot. Elle fournit des API RESTful pour gérer les utilisateurs, les séries et l'authentification d'un site web simple. Le backend est conçu pour être sécurisé, évolutif et facile à intégrer avec une application frontend.

## Fonctionnalités
- Authentification et autorisation des utilisateurs avec JWT.
- Opérations CRUD pour la gestion des séries.
- Fonctionnalités d'inscription et de connexion des utilisateurs.
- Points d'accès sécurisés avec contrôle d'accès basé sur les rôles.
- Données préchargées pour les tests.

## Structure du Projet
```
src/
  main/
    java/
      mathis/
        simple_website_backend/
          configs/        # Fichiers de configuration pour la sécurité et les paramètres de l'application
          controller/     # Contrôleurs REST pour gérer les requêtes HTTP
          models/         # Modèles de données représentant les entités
          repository/     # Interfaces pour les opérations sur la base de données
          services/       # Logique métier et couche de service
    resources/
      application.properties  # Configuration de l'application
      data.sql                # Données préchargées pour la base de données
  test/
    java/                   # Tests unitaires et d'intégration
    resources/              # Ressources spécifiques aux tests
```

## Prérequis
- Java 17 ou supérieur
- Maven 3.8.9 ou supérieur
- Une base de données (par exemple, MySQL, PostgreSQL) si vous n'utilisez pas la base de données en mémoire

## Démarrage

### Installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/Mathis1187/simple_website_backend.git
   ```
2. Accédez au répertoire du projet :
   ```bash
   cd simple_website_backend
   ```

### Exécution de l'Application
1. Construisez le projet avec Maven :
   ```bash
   ./mvnw clean install
   ```
2. Lancez l'application :
   ```bash
   ./mvnw spring-boot:run
   ```

### Tests
Exécutez les tests avec Maven :
```bash
./mvnw test
```

## Configuration
- Mettez à jour le fichier `application.properties` dans `src/main/resources` pour configurer la base de données et d'autres paramètres.

## Points d'Accès API
### Authentification
- `POST /auth/login` : Connexion utilisateur
- `POST /auth/register` : Inscription utilisateur

### Séries
- `GET /series` : Obtenir toutes les séries
- `POST /series` : Ajouter une nouvelle série
- `PUT /series/{id}` : Mettre à jour une série
- `DELETE /series/{id}` : Supprimer une série

### Utilisateurs
- `GET /users` : Obtenir tous les utilisateurs
- `GET /users/{id}` : Obtenir un utilisateur par ID

## Documentation Swagger
La documentation API Swagger est disponible à l'adresse suivante :
```
http://localhost:6969/swagger-ui.html
```
Utilisez-la pour explorer et tester les points d'accès disponibles de manière interactive.

## Junkins ScrenShot

<img width="1742" height="828" alt="image" src="https://github.com/user-attachments/assets/8b0e0a69-ebb6-4eb8-80ee-a086288cfd2c" />


