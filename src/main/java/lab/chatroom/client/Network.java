package lab.chatroom.client;

import lab.chatroom.common.ServerInfo;
import java.net.*;
import java.io.*;
import java.util.Scanner;

/* Each messages entered by the user (via the console) is sent
*  to the server. We wait a response from the server before we
*  ask the user for input again.
*  The client exits when the user inputs "\quit", this command is
*  sent to the server for a clean shutdown and we quit when the server
*  has responded.
*  The client can shutdown the server with "\shutdown", if he does so,
*  each connection to the server is closed.
*/

public class Network implements Runnable {
  private Mediator mediator;
  private Socket socket;
  private PrintWriter out;
  private boolean quit;
  private Thread thisTask;

  public Network(Mediator mediator) {
    this.mediator = mediator;
    mediator.subscribeMsgFromGUI(this::sendMessage);
    mediator.subscribeExit(this::exit);
    quit = false;
  }

  @Override
  public void run() {
    thisTask = Thread.currentThread();
    System.out.println("Connecting to server " + ServerInfo.info());
    try (
      Socket socket_ = new Socket(ServerInfo.ip, ServerInfo.port);
      PrintWriter outRessource = new PrintWriter(socket_.getOutputStream(), true)
    ){
      socket = socket_;
      out = outRessource;
      readNetworkLoop(socket);
    } catch (IOException ex) {
      System.err.println(ex);
    }
  }

  public void exit() {
    quit = true;
    thisTask.interrupt();
    try {
      socket.close();
    } catch(IOException e) {}
  }

  private void sendMessage() {
    String msg = mediator.consumeToSend();
    out.print(msg);
    out.flush();
    if(msg.startsWith("\\quit")) {
      mediator.exit();
    }
  }

  private void readNetworkLoop(Socket socket) {
    try (BufferedReader in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()))
    ){
      String msg;
      while(!quit && (msg = in.readLine()) != null) {
        System.out.println("Received message: " + msg);
        parseMessage(msg);
      }
    }
    catch(IOException e) {
      System.err.println(e);
    }
  }

  private void parseMessage(String msg) {
    if(msg.startsWith("\\shutdown")) {
      mediator.exit();
    }
    else if(msg.startsWith("\\quit")) {
      mediator.receiveMessage("A client has quit.");
    }
    else {
      mediator.receiveMessage(msg);
    }
  }
}
