FROM openjdk:8

COPY target/KafkaUIByLcc-1.0.jar /usr/local/KafkaUILCC/app.jar

WORKDIR /usr/local/KafkaUILCC

ENTRYPOINT ["java", "-Djava.awt.headless=true","-Duser.timezone=GMT+8", "-jar",  "/usr/local/KafkaUILCC/app.jar"]
