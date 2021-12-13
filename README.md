# poc-kafka-quarkus
POC to parallelize long time processing using Kafka and Quarkus.

# Instructions

## Running everything with Docker Compose

### Compile the two projects

Enter the processor folder and run:
```
$ ./mvnw package
```

Enter the producer folder and run:
```
$ ./mvnw package
```

### Run the Docker Compose

On the root folder, run:
```
$ docker-compose up
```

If you want to run multiple instances of the processor, just pass the `scale` argument:
```
$ docker-compose up --scale processor=2
```

## Running in development mode

### Start Kafka and Zookeeper on localhost

On the root folder, run:
```
$ docker-compose -f docker-compose-localhost.yaml up
```

### Start producer and processor

Enter the processor folder and run:
```
$ mvn quarkus:dev
```

Enter the producer folder and run:
```
$ mvn quarkus:dev -Ddebug=5006
```





# Kafka

## Partitions

The `num.partitions` parameter defines how many paritions the topic will have.

## Consumer groups

One consumer group will receive all messages sent to a topic.

One consumer group can have 'n' instances of applications running.

Each instance of the consumer group will process messages from some partitons of the topic.

## Consumer groups and partitions

Kafka will balance the topic partitions between the instances of a consumer group.

If you have more consumers than partitions, some consumers will remain idle. 


# Study


https://quarkus.io/guides/kafka

https://strimzi.io/docs/operators/latest/using.html

## smallrye

https://smallrye.io/smallrye-reactive-messaging/smallrye-reactive-messaging/3.13/index.html

https://smallrye.io/smallrye-reactive-messaging/smallrye-reactive-messaging/3.1/advanced/blocking.html

https://smallrye.io/smallrye-reactive-messaging/smallrye-reactive-messaging/3.1/emitter/emitter.html#_emitter_and_channel

