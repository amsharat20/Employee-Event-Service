package com.event.service.repository;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.event.service.Application;
import com.event.service.model.Event;

@Component
public class EventDAOImpl  implements EventDAO {

	static final Logger logger  = LogManager.getLogger(EventDAOImpl.class.getName());
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	final static String EVENT_COLLECTION = "events";

	@Override
	public List<Event> findAllEvents(UUID uuid) {
        mongoTemplate.getCollection(EVENT_COLLECTION);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("uuid").is(uuid));
		query.with(new Sort(Sort.Direction.ASC,"creationDate"));
		
		logger.info("NAME " +uuid);
		logger.info("QUERY " +query.toString());
		
		List<Event> events = mongoTemplate.find(query, Event.class);
		
		logger.info("SIZE  " +events.size());
		
		for(Event event : events ) {
			System.out.println("##########  " + event.getUuid() + "---- " +event.getName());
		}
       		return events;
	}


}
