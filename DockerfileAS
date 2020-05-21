
# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# copy application JAR (with libraries inside)
COPY BodyTracking/BodyTrackingAnalysis/target/BodyTrackingAnalysis-0.0.2-SNAPSHOT.jar /app_as.jar
# specify default command
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "-XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m","/app_as.jar"]
