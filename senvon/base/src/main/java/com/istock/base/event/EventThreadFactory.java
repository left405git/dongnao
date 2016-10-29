package com.istock.base.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventThreadFactory {

	private ThreadPoolExecutor executor;
//	private LinkedBlockingQueue<Event> queue;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public EventThreadFactory(Integer coreSize,Integer maxSize , Integer capacity , Integer expireSec){
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(capacity == null ? 10000 : capacity);
		executor = new ThreadPoolExecutor(coreSize==null?5:coreSize , maxSize==null?5:maxSize , expireSec == null ?10:expireSec , TimeUnit.SECONDS , queue);
	}
	
	public void executeEvent(final Class<?> listenerClass ,final Event event){
		if(event != null){
			//使用线程执行触发一个事件
			
			if(EventListener.class.isAssignableFrom(listenerClass)){
				this.executor.submit(new Runnable(){
					public void run() {
						try{
							EventListener listener = (EventListener)listenerClass.newInstance();
							if(listener.canPass(event)){
								return;
							}
							if(listener.canExec(event)){
								listener.executeEvent(event);
							}
						}catch(Exception e){
							logger.error("execute event error:" , e);
						}
						
					}
				});
			}
		}
	}
}
