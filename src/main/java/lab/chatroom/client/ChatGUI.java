package lab.chatroom.client;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

public class ChatGUI extends Application
{
  public static void main(String[] args) {
    launch(args);
  }

  // Based on https://docs.oracle.com/javafx/2/get_started/hello_world.htm
  @Override
  public void start(Stage stage) {
    stage.setTitle("Hello World!");
    Button btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction((event) -> {
      System.out.println("Hello World!");
    });
    StackPane root = new StackPane();
    root.getChildren().add(btn);
    stage.setScene(new Scene(root, 300, 250));
    stage.show();
  }
}
