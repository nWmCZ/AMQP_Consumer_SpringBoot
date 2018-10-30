package eu.sn.scheduler;

import eu.sn.model.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    MessagingService messagingService;

    Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(fixedDelayString = "${scheduler:5000}")
    public void scheduledJob() {
        logger.info("Scheduled job started");
        messagingService.receiveMessage();
        logger.info("Scheduled job finished");
    }
}
