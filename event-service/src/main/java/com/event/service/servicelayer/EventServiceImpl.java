package com.event.service.servicelayer;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.event.service.model.Event;
import com.event.service.repository.EventDAO;

@Repository
public class EventServiceImpl implements EventService {
	
@Autowired
private EventDAO eventDAO;

	@Override
	public List<Event> findAllEvents(UUID uuid) {
		List<Event> events =  eventDAO.findAllEvents(uuid);
		return events;
	}

}
