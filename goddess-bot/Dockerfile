FROM openjdk:latest
# copy the packaged jar file into our docker image
COPY target/goddess-bot-1.0-SNAPSHOT-jar-with-dependencies.jar /App.jar
COPY .env /.env
# set the startup command to execute the jar
CMD ["java", "-jar", "/App.jar"]