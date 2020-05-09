
# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# copy application WAR (with libraries inside)
COPY BodyTracking/BodyTracking/target/BodyTracking-0.0.2-SNAPSHOT.war /app.war
# specify default command
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "-XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m","/app.war"]
