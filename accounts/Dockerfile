# We need a dependency of java runtime, so we have to fetch the base image of jdk
FROM openjdk:17-jdk-slim

# Who will maintain the image
MAINTAINER harshit

# Copy the fat jar from target folder - Adding application fat jar to our image
# copy src dest
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Entrypoint of our application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]