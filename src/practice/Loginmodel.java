package practice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;

public class Loginmodel {
	static Connection connection;
	
	public Loginmodel() {
		connection = sqlConnection.Connector();
		if(connection==null) { 
			System.out.println("connection not successful!!");
			System.exit(1);
		}
	}
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean isLogin(String user, String pass) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultset=null;
		String query="select * from admin where username = ? and pwd = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			
			resultset = preparedStatement.executeQuery();
			if(resultset.next()) {
				return true;
			}
			else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}finally {
			preparedStatement.close();
			resultset.close();
		}
	}
	public String hint(String user) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultset=null;
		String query="select hint from admin where username = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			resultset = preparedStatement.executeQuery();
			System.out.println(resultset.getString(1));
			return resultset.getString(1);
		}catch(Exception e){
			e.printStackTrace();
			return "<not available>";
		}finally {
			preparedStatement.close();
			resultset.close();
		}
	}
}
