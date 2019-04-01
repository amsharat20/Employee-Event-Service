package com.event.service.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.event.service.model.Event;
import com.event.service.repository.EventDAOImpl;
import com.event.service.repository.EventRepository;

@Service
public class EventListener {
	
	static final Logger logger  = LogManager.getLogger(EventListener.class.getName());
	
	@Autowired
	EventRepository eventRepository;

	@KafkaListener(topics = "employee-topic", groupId = "group_json", containerFactory = "eventKafkaListenerFactory")
	public void consumeJson(Event event) {
		
		eventRepository.save(event);
		
		logger.info("Consumed JSON Message: " +event.getUuid() + event.getEmail() + event.getDescription() +event.getName());
	}
}
