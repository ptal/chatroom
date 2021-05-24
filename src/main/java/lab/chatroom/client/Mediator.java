package lab.chatroom.client;

import java.util.*;

// Mediator is a design pattern to organize communication between two or more classes.
// We have the GUI and the network which must communicate because both produces and consumes messages.
// The network receives data (messages) and the GUI consumes the data (toReceive queue), and vice-versa (toSend queue).
// MessagesQueue is an instance of the well-known producer-consumer concurrent pattern.
public class Mediator {
  private MessagesQueue toSend;
  private MessagesQueue toReceive;
  private ArrayList<Runnable> msgReceivedFromNetwork;
  private ArrayList<Runnable> msgReceivedFromGUI;
  private ArrayList<Runnable> exitObservers;

  public Mediator() {
    toSend = new MessagesQueue();
    toReceive = new MessagesQueue();
    msgReceivedFromNetwork = new ArrayList<>();
    msgReceivedFromGUI = new ArrayList<>();
    exitObservers = new ArrayList<>();
  }

  public void subscribeMsgFromNetwork(Runnable r) {
    msgReceivedFromNetwork.add(r);
  }

  public void subscribeMsgFromGUI(Runnable r) {
    msgReceivedFromGUI.add(r);
  }

  public void subscribeExit(Runnable r) {
    exitObservers.add(r);
  }

  public void sendMessage(String msg) {
    toSend.produceMsg(msg);
    wakeUp(msgReceivedFromGUI);
  }

  public void receiveMessage(String msg) {
    toReceive.produceMsg(msg);
    wakeUp(msgReceivedFromNetwork);
  }

  public String consumeToSend() {
    return toSend.consumeMsg();
  }

  public String consumeToReceive() {
    return toReceive.consumeMsg();
  }

  public void exit() {
    wakeUp(exitObservers);
  }

  private static void wakeUp(ArrayList<Runnable> observers) {
    for(Runnable r : observers) {
      r.run();
    }
  }
}
