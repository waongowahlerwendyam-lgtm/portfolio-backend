FROM openjdk:21.0.10

WORKDIR /app

# Copier le fichier JAR généré par Maven
COPY target/*.jar app.jar

# Exposer le port 8080 (ou celui que vous utilisez)
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]