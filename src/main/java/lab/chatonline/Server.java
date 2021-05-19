package lab.chatroom;

import java.net.*;
import java.io.*;

/* This server can only accepts one client. The server is stopped
*  when this client timed out or if it sends the message "\quit".
*/

public class Server {
  public static void main(String[] args) {
    System.out.println("Starting server...");
    try (
      ServerSocket server = new ServerSocket(ServerInfo.port);
      Socket connection = server.accept();
      PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))
    ){
      System.out.println("New client at " + connection);
      String msg = "";
      while(!msg.equals("\\quit")) {
        msg = in.readLine();
        if(msg == null) {
          break;
        }
        out.println(msg);
      }
    } catch (IOException ex) {
      System.err.println(ex);
    }
    System.out.println("Server shutted down.");
  }
}
