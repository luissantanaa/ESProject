
# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# copy application JAR (with libraries inside)
COPY target/BodyTracking-0.2.2-SNAPSHOT.jar /app.jar
# specify default command
CMD ["/usr/bin/java", "-jar", "/app.jar"]
