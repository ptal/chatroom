package lab.chatroom;

public class ServerInfo {
  public static final int port = 3333;
  public static final String ip = "localhost";

  public static String info() {
    return ip + ":" + port;
  }
}
