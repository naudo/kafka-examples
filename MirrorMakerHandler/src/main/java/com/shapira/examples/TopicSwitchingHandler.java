package com.shapira.examples;


import kafka.consumer.BaseConsumerRecord;
import kafka.message.MessageAndMetadata;
import kafka.tools.MirrorMaker;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Collections;
import java.util.List;

public class TopicSwitchingHandler implements MirrorMaker.MirrorMakerMessageHandler {

    private final String topicPrefix;

    public TopicSwitchingHandler(String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }

    public List<ProducerRecord<byte[], byte[]>> handle(MessageAndMetadata<byte[], byte[]> record) {
        return Collections.singletonList(new ProducerRecord<byte[], byte[]>(topicPrefix + "." + record.topic(), record.partition(), record.key(), record.message()));
    }

    public List<ProducerRecord<byte[], byte[]>> handle(BaseConsumerRecord record) {
        String message = new String(record.value());
        String encrypted = Rot13.encrypt(message);
        System.out.print("Message: '" + message + "' Transformed: '" + encrypted + "'");
        return Collections.singletonList(new ProducerRecord<byte[], byte[]>(topicPrefix + "." + record.topic(), record.partition(), record.key(), encrypted.getBytes()));
    }
}


class Rot13 {
    public static String encrypt(String msg) {

        String encryptedString = "";
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            encryptedString += c;
        }
        return encryptedString;
    }

}
