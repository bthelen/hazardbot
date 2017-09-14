package com.corelogic.hazardbot.notifier;

import com.corelogic.hazardbot.Event;
import com.corelogic.hazardbot.notification.NotificationService;
import com.corelogic.hazardbot.notification.SmsNotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventProducerService {

    private final StubRoadClosureRepository stubRoadClosureRepository;
    private final CoaRoadClosureRepository coaRoadClosureRepository;
    private final NotificationService notificationService;

    public EventProducerService(StubRoadClosureRepository stubRoadClosureRepository,
                                CoaRoadClosureRepository coaRoadClosureRepository,
                                NotificationService notificationService) {
        this.stubRoadClosureRepository = stubRoadClosureRepository;
        this.coaRoadClosureRepository = coaRoadClosureRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 30000)
    public void checkForEvents() {
        log.info("Firing checkForEvents");
        final List<RoadClosure> roadClosures = stubRoadClosureRepository.getNewRoadClosureEvents();
        handleRoadClosures(roadClosures);
        final List<RoadClosure> coaRoadClosures = coaRoadClosureRepository.getNewRoadClosureEvents();
        handleRoadClosures(coaRoadClosures);
    }

    private void handleRoadClosures(List<RoadClosure> roadClosures) {
        roadClosures.stream().forEach((roadClosure) -> {
            Event roadClosureEvent = new Event(String.format("Road Closure: %s at %s", roadClosure.getLocation(), roadClosure.getCrossStreets()));
            log.info("Road Closure to notify {}", roadClosureEvent.getContent());
            try {
                notificationService.notifySubscribers(roadClosureEvent);
            } catch (SmsNotificationException e) {
                log.error(e.getMessage(), e);
            }
        });
    }
}
