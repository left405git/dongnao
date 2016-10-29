package com.istock.base.evnet;

import java.util.Set;

import org.junit.Test;

import com.istock.base.event.Event;
import com.istock.base.event.EventContainer;

public class EventConditionContainerTest {

	@Test
	public void test1() throws Exception{
		EventContainer container = new EventContainer();
		Set<Class<?>> classSet = container.findRelateClass(YoungPeopleListener.class);
		System.out.println(classSet);
	}
	
	
	
	@Test
	public void test2() throws Exception{
		EventContainer container = new EventContainer();
		container.init();
		
		container.registerListener(PeopleModel.class, SampleSexListener.class);
		container.registerListener(PeopleModel.class, YoungPeopleListener.class);
		container.registerListener(SampleModel.class, SampleListener.class);
		
		PeopleModel girl = new PeopleModel();
		girl.setAge(25);
		girl.setSex(2);
		girl.setName("senvon's daughter");
		container.submitEvent(new Event(girl));
		
		PeopleModel man = new PeopleModel();
		man.setAge(24);
		man.setSex(1);
		man.setName("senvon's sun");
		container.submitEvent(new Event(man));
		
		SampleModel model1 = new SampleModel();
		model1.setAge(80);
		model1.setSex(1);
		model1.setName("senvon's self");
		model1.setAddress("shanghai");
		model1.setFuck("nice fuck");
		container.submitEvent(new Event(model1));
		
		Thread.sleep(2000);
	}
}
