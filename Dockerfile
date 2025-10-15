# Utiliser une image de base officielle de Java
FROM eclipse-temurin:21-jdk-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré dans l'image
COPY target/simple_website_backend-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port utilisé par l'application
EXPOSE 6969

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]