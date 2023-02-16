FROM openjdk:11
ADD target/*.jar em-server.jar
EXPOSE 8200
ENTRYPOINT ["java","-jar","em-server.jar"]

