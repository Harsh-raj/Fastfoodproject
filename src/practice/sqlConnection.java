package practice;

import java.sql.Connection;
import java.sql.DriverManager;

public class sqlConnection {
	public static Connection Connector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fastfood", "root", "");
			return conn;
		}catch(Exception e) {
			e.printStackTrace();
		    return null;
		}
	}
}
