package com.istock.base.evnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.istock.base.event.Event;
import com.istock.base.event.EventListener;
import com.istock.base.event.ListenerOrder;

@ListenerOrder(order = 1)
public class SampleListener implements EventListener {

	private Logger logger = LoggerFactory.getLogger(getClass());
	public void executeEvent(Event event) {
		logger.info("=================this is sample listener==================");
	}

	public boolean canExec(Event event) {
		Object eventObj = event.getSource();
		return SampleModel.class.isAssignableFrom(eventObj.getClass());
	}

	public boolean canPass(Event event) {
		return false;
	}

}
