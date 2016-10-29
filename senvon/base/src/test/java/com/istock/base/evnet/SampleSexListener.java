package com.istock.base.evnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.istock.base.event.Event;
import com.istock.base.event.EventListener;
import com.istock.base.event.ListenerOrder;

@ListenerOrder(order = 2)
public class SampleSexListener implements EventListener {

	private Logger logger = LoggerFactory.getLogger(getClass());
	public void executeEvent(Event event) {
		logger.info("this is Sex is men listener=========");
	}

	public boolean canExec(Event event) {
		Object eventObj = event.getSource();
		if(PeopleModel.class.isAssignableFrom(eventObj.getClass())){
			PeopleModel sampleModel = (PeopleModel)eventObj;
			return sampleModel.getSex() == 1;
		}
		return false;
	}

	public boolean canPass(Event event) {
		return false;
	}

}
