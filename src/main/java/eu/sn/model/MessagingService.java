package eu.sn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessagingService {

    @Autowired
    private Environment env;

    Logger logger = LoggerFactory.getLogger(MessagingService.class);

    @JmsListener(destination = "${destinationName:defaultConsumerDestination}")
    public void receiveMessageFromQueue(String content) {
        String destination = env.getProperty("destinationName");
        if (destination == null) destination = "defaultConsumerDestination";
        logger.info("Received message from " + destination + ". Content is: " + content);
    }
}
