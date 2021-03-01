FROM openjdk:11
ADD /build/libs/assembly-service-*.jar /assembly-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/assembly-service.jar"]