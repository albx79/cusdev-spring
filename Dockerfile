FROM maven:3.8.6-openjdk-18-slim AS builder

RUN mkdir /usr/src/app
WORKDIR /usr/src/app
COPY . .
RUN mvn package

FROM openjdk:17-jdk-slim AS runner
COPY --from=builder /usr/src/app/target/telepass-cusdev-server-0.0.1-SNAPSHOT.jar /
RUN apt update && apt-get install curl --yes
CMD java -jar /telepass-cusdev-server-0.0.1-SNAPSHOT.jar
