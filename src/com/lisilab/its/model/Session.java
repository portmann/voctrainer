package com.lisilab.its.model;


public class Session {
	
	long id;
	long start;
	int duration;
	Student student;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
