package com.istock.base.event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**注册事件条件容器
 * @author Administrator
 *
 */
public class EventContainer {

	private Map<Class<?> , List<Class<?>>> map = new ConcurrentHashMap<Class<?>, List<Class<?>>>();
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	private EventThreadFactory factory;
	private Integer coreSize;//核心处理线程
	private Integer maxSize;//最大处理线程
	private Integer capacity;//本地队列的容量
	private Integer expireSec;//一个调用的超时时间
	
	public void setCoreSize(Integer coreSize) {
		this.coreSize = coreSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public void setExpireSec(Integer expireSec) {
		this.expireSec = expireSec;
	}

	public void init(){
		factory = new EventThreadFactory(coreSize , maxSize,capacity , expireSec);
	}
	
	/**注册一个类型监听器
	 * @param clazz
	 * @param listenerClass
	 */
	public void registerListener(Class<?> clazz ,Class<?> listenerClass){
		List<Class<?>> list = map.get(clazz);
		if(list == null){
			list = new ArrayList<Class<?>>();
		}
		if(listenerClass != null && EventListener.class.isAssignableFrom(listenerClass)){
			list.add(listenerClass);
			if(list.size()>1){
				list.sort(new Comparator<Class<?>>(){

					public int compare(Class<?> o1,Class<?> o2) {
						Integer o1Order = 5,o2Order = 5;
						if(o1.isAnnotationPresent(ListenerOrder.class)){
							ListenerOrder lorder = o1.getAnnotation(ListenerOrder.class);
							o1Order = lorder.order();
						}
						if(o2.isAnnotationPresent(ListenerOrder.class)){
							ListenerOrder lorder = o2.getAnnotation(ListenerOrder.class);
							o2Order = lorder.order();
						}
						
						if(o1Order>o2Order){
							return 1;
						}else if(o1Order<o2Order){
							return -1;
						}else{
							return 0;
						}
						
					}

					
				});
			}
			map.put(clazz, list);
		}
	}
	
	/**提交一个事件
	 * @param event
	 */
	public void submitEvent(Event event){
		logger.info("======ready to submit event:{}=====" , event);
		Object eventObject = event.getSource();
		if(eventObject != null){
			Class<?> eventClass = eventObject.getClass();
			Set<Class<?>> clazzSet = findRelateClass(eventClass);
			for(Class<?> clazz : clazzSet){
				List<Class<?>> listenerList = map.get(clazz);
				if(listenerList != null && listenerList.size()>0){
					for(Class<?> listenerClass : listenerList){
						factory.executeEvent(listenerClass, event);
					}
				}
			}
		}
		logger.info("======finish to submit event:{}======" , event);
	}
	
	
	public Set<Class<?>> findRelateClass(Class<?> clazz){
		Set<Class<?>> result = new HashSet<Class<?>>();
		if(clazz != null){
			result.add(clazz);
			Class<?> parentClass = clazz.getSuperclass();
			if(parentClass != null && !parentClass.equals(Object.class)){
				result.addAll(findRelateClass(parentClass));
			}
			Class<?>[] interfaceClazzes = clazz.getInterfaces();
			if(interfaceClazzes != null){
				for(Class<?> interfaceClazz : interfaceClazzes){
					result.addAll(findRelateClass(interfaceClazz));
				}
			}
		}
		return result;
	}
}
