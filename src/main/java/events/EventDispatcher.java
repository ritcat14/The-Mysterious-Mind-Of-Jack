package events;
/*
A class to dispatch event to other classes listening to
 */

public class EventDispatcher {

	private Event event = null;
	
	public EventDispatcher(Event event) {
		this.event = event;
	}
	
	public void dispatch(Event.Type type, EventHandler handler) {
		if (event.handled)
			return;
		
		if (event.getType() == type)
			event.handled = handler.onEvent(event);
	}
}
