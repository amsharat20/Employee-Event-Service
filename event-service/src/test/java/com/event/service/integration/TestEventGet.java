package com.event.service.integration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.event.service.controller.EventServiceController;
import com.event.service.model.Event;
import com.event.service.repository.EventDAO;
import com.event.service.repository.EventDAOImpl;
import com.event.service.repository.EventRepository;
import com.event.service.servicelayer.EventService;

@RunWith(SpringRunner.class)
@WebMvcTest(EventServiceController.class )
public class TestEventGet {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	EventDAO eventDAO;
	
	@MockBean
	EventDAOImpl eventDAOImpl;
	
	@MockBean
	Event event;
	
	@MockBean
	EventService eventService;
	
	@MockBean
	EventRepository eventRepository;
	
	@MockBean
	KafkaTemplate<String, Event> kafkaTemplate;
	
	@Test
	public void testGetEvents() throws Exception {
		
		UUID uuid = UUID.randomUUID();
		Event event1 = new Event("Event-Create",uuid,"sharat","sharat@w.com","message");
		Event event2 = new Event("Event-Update",uuid,"sharat","sharat@w.com","message");
		List<Event> events = new ArrayList<Event>();
		events.add(event);
		
        Mockito.when(eventService.findAllEvents(uuid)).thenReturn(Arrays.asList(event1, event2));
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/getAllEvents/{uuid}",uuid).accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request).andExpect(status().isOk());
	}

	

}
