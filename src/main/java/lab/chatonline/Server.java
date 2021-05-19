package lab.chatroom;

import java.net.*;
import java.io.*;

/* This server accept more than one client.
   However, each client is isolated.
*/

public class Server {
  public static void main(String[] args) {
    System.out.println("Starting server...");
    try (ServerSocket server = new ServerSocket(ServerInfo.port))
    {
      while(true) {
        Socket socket = server.accept();
        System.out.println("New client at " + socket);
        new Connection(socket).start();
      }
    } catch (IOException ex) {
      System.err.println(ex);
    }
    System.out.println("Server shutted down.");
  }
}
