package com.event.service.controller;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.service.exception.RecordNotFoundException;
import com.event.service.model.Event;
import com.event.service.servicelayer.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v1")
@Api(value="Event Service Controller APIs")

@RestController
public class EventServiceController {

	static final Logger logger  = LogManager.getLogger(EventServiceController.class.getName());
	
	@Autowired
	private EventService eventService;

	@ApiOperation("Get all available Events for an Employee")
	@GetMapping("/getAllEvents/{uuid}")
	public ResponseEntity<Object>  findAllEvents(@PathVariable UUID uuid) throws RecordNotFoundException {
		logger.info("Received email id " +uuid );
		List<Event> events =  eventService.findAllEvents(uuid);
		if(events.size() == 0) {
			throw new RecordNotFoundException("No Events present. Please wait for the events to be consumed ");
		}
		return new ResponseEntity<Object>(events, HttpStatus.OK);
	}
	

}
