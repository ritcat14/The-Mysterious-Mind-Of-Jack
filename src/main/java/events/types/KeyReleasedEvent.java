package events.types;

import events.Event;

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
