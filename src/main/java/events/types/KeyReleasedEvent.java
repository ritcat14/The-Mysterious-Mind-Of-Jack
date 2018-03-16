package events.types;

import events.Event;
/*
A child class of event to detect when the key is released
 */
public class KeyReleasedEvent extends Event {
  
  protected int key = 0;
  
    public KeyReleasedEvent(int key, Type type){
      super(type);
      this.key = key;
    }
  
    public int getKey(){
      return key;
    }
  
}
