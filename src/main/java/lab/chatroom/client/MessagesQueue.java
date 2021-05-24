package lab.chatroom.client;

// This class represents a shared state between the network and the graphical components.
public class MessagesQueue {
  private StringBuilder messages;

  public MessagesQueue() {
    messages = new StringBuilder();
  }

  public synchronized void produceMsg(String msg) {
    messages.append(msg + "\n");
  }

  public synchronized String consumeMsg() {
    String msg = messages.toString();
    messages = new StringBuilder();
    return msg;
  }
}
