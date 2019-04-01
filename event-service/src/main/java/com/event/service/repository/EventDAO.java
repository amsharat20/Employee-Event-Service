package com.event.service.repository;

import java.util.List;
import java.util.UUID;

import com.event.service.model.Event;

public interface EventDAO {


	List<Event> findAllEvents(UUID uuid);
	

}
