package server;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
	private static String IP = "localhost";
	private static String schema = "oblg3";
	private static String port = "3306";
	private static String username = "root";
	private static String password = "arielsql";
	 public static Connection getConnection() throws Exception{
	  try {
	   Class.forName("com.mysql.jdbc.Driver");
	   Connection conn = DriverManager.getConnection("jdbc:mysql://"+IP+":"+port+"/"+schema,username,password);
	   System.out.println("Connected");
	   return conn;
	  } catch(SQLException e) {e.printStackTrace();
	  }
	  
	  
	  return null;
	 }
}
