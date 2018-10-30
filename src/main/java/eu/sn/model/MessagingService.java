package eu.sn.model;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class MessagingService {

    @Value("${amqpUrl:tcp://localhost:61616}")
    String amqpUrl;

    @Value("${queue:scheduledQueue}")
    String queue;

    @Value("${toQueue:true}")
    boolean toQueue;

    Logger logger = LoggerFactory.getLogger(MessagingService.class);

    public void receiveMessage() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(amqpUrl);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination;
            if (toQueue) {
                // Create the destination (Topic or Queue)
                destination = session.createQueue(queue);
            } else {
                destination = session.createTopic(queue);
            }

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);


            // Wait for a message
            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.info("Received: " + text);
            } else {
                logger.info("Received: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            logger.info("Caught: " + e);
            e.printStackTrace();
        }
    }

}
