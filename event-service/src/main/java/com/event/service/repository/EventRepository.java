package com.event.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.event.service.model.Event;

	@Repository
	public interface EventRepository extends MongoRepository<Event, String> {
	}

