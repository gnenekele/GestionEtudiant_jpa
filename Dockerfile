# ========= Étape de construction (Maven) =========
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copie du fichier de configuration Maven (pom.xml)
COPY pom.xml .

# Téléchargement des dépendances (mise en cache)
RUN mvn dependency:go-offline

# Copie du code source et compilation
COPY src ./src
RUN mvn clean package -DskipTests

# ========= Étape d'exécution (Alpine JRE) =========
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Maven génère le JAR dans le dossier 'target' au lieu de 'build/libs'
COPY --from=build /app/target/*.jar /app/app.jar

# Configuration de l'utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseContainerSupport","-jar","/app/app.jar"]