package com.employee.management.kafkaservice;

import java.util.UUID;

public class Event {
	
	private String message;
	private UUID uuid;
	private String name;
	private String email;
	private String description;
	
	public Event( String message, UUID uuid, String name,String email,String description){
	this.message = message;
	this.description = description;
	this.uuid = uuid;
	this.name = name;
	this.email = email;
	}
	

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
