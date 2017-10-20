Using a MessageHandler in MirrorMaker
======================================

This is just a forked version of https://github.com/gwenshap/kafka-examples to see if it's possible to modify the message using a MM Message Handler using the latest Kafka. To achieve this, we're just taking the message value and applying Rot13 to it. 


```bash

mvn clean install
export CLASSPATH=$CLASSPATH:~/.m2/repository/MirrorMakerExample/TopicSwitchingHandler/1.0-SNAPSHOT/TopicSwitchingHandler-1.0-SNAPSHOT.jar
./bin/kafka-mirror-maker \
    --consumer.config etc/kafka/consumer.properties \
    --message.handler com.shapira.examples.TopicSwitchingHandler \
    --message.handler.args dc1 \
    --producer.config etc/kafka/producer.properties --whitelist mm1
```

* Test it with a producer on source topic and consumer on destination:

    `bin/kafka-console-producer --topic mm1 --broker-list localhost:9092`
    
    `bin/kafka-console-consumer --topic dc1.mm1 --bootstrap-servers localhost:9092 --from-beginning`



