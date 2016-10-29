package com.istock.base.evnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.istock.base.event.Event;
import com.istock.base.event.EventListener;
import com.istock.base.event.ListenerOrder;

@ListenerOrder(order = 3)
public class YoungPeopleListener implements EventListener {

	private Logger logger = LoggerFactory.getLogger(getClass());
	public void executeEvent(Event event) {
		logger.info("=================this is age great than 18 and less than 40 listener==================");
	}

	public boolean canExec(Event event) {
		Object eventObj = event.getSource();
		
		if(PeopleModel.class.isAssignableFrom(eventObj.getClass())){
			PeopleModel sampleModel = (PeopleModel)eventObj;
			return sampleModel.getAge()>18 && sampleModel.getAge()<=40;
		}
		return false;
	}

	public boolean canPass(Event event) {
		return false;
	}

}
