package lab.chatroom;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/* Each messages entered by the user (via the console) is sent
*  to the server. We wait a response from the server before we
*  ask the user for input again.
*  The client exits when the user inputs "\quit", however this command is
*  sent the server first for a clean shutdown.
*/

public class Client {
  public static void main(String[] args) {
    System.out.println("Connecting to server " + ServerInfo.info());
  }
}
