FROM openjdk:8

COPY target/KafkaUIByLcc-1.0.jar /data/app.jar

WORKDIR /data

ENTRYPOINT ["java", "-Djava.awt.headless=true","-Duser.timezone=GMT+8", "-jar",  "/data/app.jar"]
