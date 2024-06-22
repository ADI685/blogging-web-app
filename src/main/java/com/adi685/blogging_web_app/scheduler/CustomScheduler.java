package com.adi685.blogging_web_app.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomScheduler {

	@Scheduled(fixedDelay = 6000000)
	public void printLog() {
		System.out.println("CUSTOM DELAY");
		log.info("Scheduler in action...........");
	}

}
