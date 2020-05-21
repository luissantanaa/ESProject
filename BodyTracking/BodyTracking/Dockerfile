
# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# copy application JAR (with libraries inside)
COPY target/BodyTracking-0.1.1-SNAPSHOT.jar /app.jar
# specify default command
CMD "/usr/bin/java -jar -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m -Dspring.profiles.active=default /app.jar"
