package eu.sn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessagingService {

    Logger logger = LoggerFactory.getLogger(MessagingService.class);

    @JmsListener(destination = "${queue:scheduledQueue}")
    public void receiveMessageFromQueue(String content) {

        logger.info("Received message from scheduledQueue. Content is: " + content);
    }
}
