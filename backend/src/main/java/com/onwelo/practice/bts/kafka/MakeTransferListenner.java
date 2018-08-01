package com.onwelo.practice.bts.kafka;

import com.onwelo.practice.bts.session.SessionOutgoing;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MakeTransferListenner {

    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(MakeTransferListenner.class);


    @KafkaListener(topics = "make-transfer")
    public void processMessage(String content) {
        Logger.debug(content);
    }
}
