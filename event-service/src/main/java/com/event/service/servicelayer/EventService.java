package com.event.service.servicelayer;

import java.util.List;
import java.util.UUID;

import com.event.service.model.Event;


public interface EventService {

	List<Event> findAllEvents(UUID uuid);
	

}