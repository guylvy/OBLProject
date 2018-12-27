package application;

import java.io.IOException;
import java.util.Optional;

import client.ChatClient;
import client.Student;
import common.CommonIF;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole extends Application implements CommonIF 
{
  //Class variables *************************************************
  
	private static String IP = "localhost";
	private static int port = 4407;
	
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
	private Student student;
  ChatClient client;
  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
 * @throws InterruptedException 
   */
  public synchronized Student getStudent() throws Exception{
	  return student;
  }
  public void forgetStudent() {
	  this.student = null;
  }
  public void send(Object o) throws InterruptedException 
  {
        client.handleMessageFromClientUI(o);
  }

  /**
   * This method overrides the method in the CommonIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  @Override
  public void handle(Object message)
  {
	  if (message instanceof Student)
		  student=(Student) message;
	  else if (message instanceof String)
		  newAlert(AlertType.INFORMATION,null,null,(String)message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client GUI.
   */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PrototypeGUI.fxml"));
		      client= new ChatClient(IP = getParameters().getRaw().get(0), port, this);
			loader.setController(new MainController(this));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	      System.exit(1);
	    } catch (IndexOutOfBoundsException e) {
			newAlert(AlertType.ERROR, null, "No IP", "Please specify IP in first argument");
			System.exit(1);
		}
		
	}
	public static Optional <ButtonType> newAlert(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title == null ? "OBL - Group 3" : title);
		alert.setHeaderText(header);
		alert.setContentText(content);
			return alert.showAndWait();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
//End of ConsoleChat class
