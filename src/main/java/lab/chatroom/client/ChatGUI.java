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
    ChatGUI.launch(args);
  }

  private Text chatText;
  private ScrollPane chatScroll;
  private Mediator mediator;
  private Thread networkTask;

  @Override
  public void start(Stage stage) {
    mediator = new Mediator();
    mediator.subscribeMsgFromNetwork(() -> Platform.runLater(this::refreshChat));
    mediator.subscribeExit(Platform::exit);
    networkTask = new Thread(new Network(mediator));
    networkTask.start();
    createGUI(stage);
    stage.show();
  }

  // Before exiting, we wait for the network thread to terminate.
  @Override
  public void stop() {
    mediator.exit();
    try {
      networkTask.join();
    } catch(Exception e) {}
  }

  private void createGUI(Stage stage) {
    Parent chatapp = createChatView();
    Scene scene = new Scene(chatapp, 400, 300);
    stage.setTitle("Discord Remixed!");
    stage.setScene(scene);
  }

  private Parent createChatView() {
    VBox chatapp = new VBox();
    addChat(chatapp);
    addChatInput(chatapp);
    return chatapp;
  }

  private void addChat(Pane parent) {
    chatText = new Text();
    chatScroll = new ScrollPane();
    chatScroll.setContent(chatText);
    parent.getChildren().add(chatScroll);
  }

  private void refreshChat() {
    String newMsg = mediator.consumeToReceive();
    chatText.setText(chatText.getText() + newMsg);
    chatScroll.setVvalue(1.0);
  }

  private void addChatInput(Pane parent) {
    HBox chatInput = new HBox();
    TextField messageInput = new TextField();
    Button sendButton = new Button();
    sendButton.setText("send");
    sendButton.setOnAction((event) -> {
      mediator.sendMessage(messageInput.getText());
      messageInput.setText("");
    });
    chatInput.getChildren().addAll(messageInput, sendButton);
    parent.getChildren().add(chatInput);
  }
}