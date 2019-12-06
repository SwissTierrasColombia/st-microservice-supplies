FROM openjdk:12

VOLUME /tmp

ADD ./target/st-microservice-supplies-0.0.1-SNAPSHOT.jar st-microservice-supplies.jar

EXPOSE 8080

ENTRYPOINT java -jar /st-microservice-supplies.jar