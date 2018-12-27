// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import application.MyData;
import client.Student;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.MyDB;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
	final public static int PORT = 4407;
	private static Connection conn;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object o, ConnectionToClient client)
  {
	  try 
		{
	  if (o instanceof MyData) { // verify the object was an 
	  		MyData data = (MyData) o;
	  		    switch (data.getAction()) {
	  		    case "view_student_name":
	  		    	Statement stmt = conn.createStatement();
	  				ResultSet rs = stmt.executeQuery("SELECT * FROM Student");
	  				while (!rs.isClosed() && rs.next()) {
	  					if (rs.getString("StudentID").equals(data.getData("student_id"))) {
	  						Student st = new Student(rs.getString("StudentID"),rs.getString("StudentName"),rs.getString("StatusMembership"),
	  								rs.getString("Operation"),rs.getBoolean("Freeze"));
	  						client.sendToClient(st);
	  						rs.close();
	  					}
	  				}
	  				if (!rs.isClosed()) {
	  					MyData errorData = new MyData("student_id_not_found");
	  					errorData.add("message", "ID was not found in our database.");
	  				client.sendToClient(errorData);
	  				rs.close();
	  					}
	  				break;
	  		    case "update_statusmembership":
	  		    	Student st = (Student)data.getData("student");
	  		    	PreparedStatement que = conn.prepareStatement("update Student set StatusMembership=? WHERE StudentID=?");
					que.setString(1, (String)data.getData("selected_status")); // statusmembership input from box 
					que.setString(2,st.getId()); // studentid
					que.executeUpdate();
					st.setStatusMembership((String)data.getData("selected_status"));
					client.sendToClient(st);
	  				que.close();
	  		    }
	  		    }
		} catch (SQLException e) {e.printStackTrace();}
		catch (IOException e) {
			System.out.println("Failed to send to client...");
			e.printStackTrace();
			}
	  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    EchoServer sv = new EchoServer(PORT);
    try {
		conn = MyDB.getConnection();
		System.out.println("SQL Succesfully connected.");
	} catch (Exception e) {e.printStackTrace();}
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
