# About
This project demonstrates the accumulation of message data using a Pulsar Function.

The project consists of three applications:
* a **producer**, which
    * periodically creates randomized messages and
    * sends them as JSON messages to topic `messages`.
* a **function** to be deployed to Pulsar, which
    * consumes message JSON events from topic `messages`
    * transforms them to Java objects using Jackson,
    * accumulates message data (textCount, wordCount) per userId,
    * stores the accumulated values in Pulsar's (better: BookKeeper's) persistent state store, and
    * outputs accumulated events as JSON messages to topic `message-stats`.
* a **consumer**, which
    * consumes JSON events from both topics,
    * transforms them to Java objects using Jackson, and
    * outputs the received objects to the log.

![Accumulate order totals from order events](pulsar-functions.png)

# Building
```shell
mvn clean package
```
Remember the path of this project:
```shell
export PRJ=`pwd`
```

# Pulsar Setup
You need a Pulsar 4.x installation on localhost, see https://pulsar.apache.org/docs/en/standalone/.

```shell
cd <pulsar-installation>
bin/pulsar standalone
```
In another shell, deploy the Pulsar Function that has been just built and packaged:
```shell
bin/pulsar-admin functions localrun \
  --jar $PRJ/function/target/function-0.1.0-SNAPSHOT-jar-with-dependencies.jar \
  --function-config-file $PRJ/function/function-config.yml \
  --stateStorageServiceUrl bk://127.0.0.1:4181
```

# Running
## Run Consumer
```shell
mvn -pl consumer spring-boot:run
```
Once the first message events are sent, you should see both the original messages and the message stats printed on the console.

## Run Producer
```shell
mvn -pl consumer spring-boot:run
```
Alternatively, both applications can be started from your IDE (e.g. as IntelliJ services).

## Cleanup
There are several ways to clean the topics:
* Either stop Pulsar and then clean everything:
  ```shell
  cd <pulsar-installation>
  rm -fr data logs
  ```
* Or delete the topics:
  ```shell
  bin/pulsar-admin topics delete messages
  bin/pulsar-admin topics delete message-stats
  ```
Note that the latter variant retains the function state (i.e. message stats). 