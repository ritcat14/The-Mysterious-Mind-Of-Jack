package events.types;

import events.Event;
/*
A child class from Event to detect key input
 */
public class KeyPressedEvent extends Event {
  
  protected int key = 0;
  
    public KeyPressedEvent(int key, Type type){
      super(type);
      this.key = key;
    }
  
    public int getKey(){
      return key;
    }
  
}
