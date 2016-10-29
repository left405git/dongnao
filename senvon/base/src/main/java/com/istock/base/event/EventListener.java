package com.istock.base.event;

public interface EventListener {

	/**执行一个事件监听器
	 * @param event
	 */
	public void executeEvent(Event event);
	
	/**在执行事件前,当前的事件处理是否能执行该事件.
	 * @param event
	 * @return
	 */
	public boolean canExec(Event event);
	
	/**在执行事件前,检查时否需要跳过事件处理.
	 * @param event
	 * @return
	 */
	public boolean canPass(Event event);
}
