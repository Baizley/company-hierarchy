FROM openjdk:8-jdk-alpine

COPY ./target/company-hierarchy-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app/
RUN sh -c 'touch company-hierarchy-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java", "-jar", "company-hierarchy-0.0.1-SNAPSHOT.jar"]