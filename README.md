<h2>Background</h2>

Objective of this app is to calculate distance and time on the basis of tracking data published by producer from the csv file and by processing the data consumed at Consumer.

<h2>Steps</h2>

1. First step is to clone GpxLocationTrackingApp in local machine.<br>
2. Open cmd prompt and navigate inside project folder.<br>
3. Run the below command to start and set up the kafka and zookeeper server:<br>
    docker-compose -f docker-compose.yml up<br>
4. Open another cmd prompt and run below commands to create kafka topic:<br>
    docker exec -it kafka sh<br>
    cd /opt/bitnami/kafka<br>
    ./bin/kafka-topics.sh --create --bootstrap-server 192.168.99.100:9092 --replication-factor 1 --partitions 1 --topic topicCal<br>
5. To check if topic is created run below command:<br>
    ./bin/kafka-topics.sh --list --bootstrap-server 192.168.99.100:9092<br>
6. Open another cmd prompt and Go to folder Kafka-Producer-Service, run cmd:<br>
    cd Kafka-Producer-Service<br>
7. Run the kafka producer app, cmd: <br>
    mvn spring-boot:run<br>
8. Open another cmd prompt and Go to folder Kafka-Consumer-processor, cmd: <br>
    cd Kafka-Consumer-processor<br>
9. Run the kafka consumer app, cmd:<br>
    mvn spring-boot:run<br>

*Note* - I have used separate ip (192.168.99.100) instead of localhost to avoid conflicts with other Application.

<h2>Result</h2>

1. Tracking data is published by producer from the csv file.<br>
2. In the consumer console logs, distance and time is calculated.<br>
