package lab.chatroom;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/* A connection models a channel between two entities (client and server).
*  It also implements the protocol between them in the `run` method.
*  It has the responsibility to close socket when it finishes. It is not necessary
*  to close it explicitly because closing `in` or `out` automatically close the socket
*  as well.
*/

public class Connection extends Thread {
  private Socket socket;

  public Connection(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try(
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
    ) {
      String msg = "";
      while(!msg.equals("\\quit")) {
        msg = in.readLine();
        if(msg == null) {
          break;
        }
        out.println(msg);
      }
    } catch (IOException io) {
      System.err.println(io);
    }
  }
}
