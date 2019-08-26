package practice;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VerifyCust implements Initializable{
	@FXML
	private TextField mobileno;
	@FXML
	private TextField Fname;
	@FXML
	private TextField Lname;
	@FXML
	private Label label;
	static Connection connection = sqlConnection.Connector();
	
	public static long mob;
	
	PreparedStatement preparedStatement = null;
	
	ResultSet resultset;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		label.setText("Enter mobileno...");
		
	}
	
	public void verifyCust(ActionEvent event) throws SQLException {
		if(!mobileno.getText().equals("")) {
			try {
				if(connection==null)
					throw new NullPointerException();
				mob=Long.parseLong(mobileno.getText());
				String query="select * from Customer where mobileno = '"+ Long.parseLong(mobileno.getText()) +"';";
				preparedStatement = connection.prepareStatement(query);
				resultset = preparedStatement.executeQuery();
				if(resultset.next()) {
					Stage primaryStage=new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root= loader.load(getClass().getResource("/practice/Order.fxml").openStream());
					Scene scene = new Scene(root,500,300);
					scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.setTitle("Fastfood Corner");
					primaryStage.show();
				}
				else {
					label.setText("New Customer... Enter details");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		finally {
			preparedStatement.close();
			resultset.close();
		}
		}else {
			label.setText("Enter Customer mobile number...");
		}
	}
	
	public void verifynewCust(ActionEvent event) throws SQLException {
		if(!Fname.getText().equals("") && !Lname.getText().equals("") && !mobileno.getText().equals("")) {
		try {
			if(connection==null)
				throw new NullPointerException();
					String query1="insert into Customer (mobileno, Fname, L_name, creditpt) values('" + Long.parseLong(mobileno.getText()) + "','" + Fname.getText() + "','" + Lname.getText() + "',0);";
					preparedStatement = connection.prepareStatement(query1);
					preparedStatement.executeUpdate();
					((Node)event.getSource()).getScene().getWindow().hide(); 
					Stage primaryStage=new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root= loader.load(getClass().getResource("/practice/Order.fxml").openStream());
					Scene scene = new Scene(root,500,300);
					scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.setTitle("Fastfood Corner");
					primaryStage.show();
				}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			preparedStatement.close();
			resultset.close();
		}
		}
		else {
			label.setText("Enter New Customer details...");
		}
	}
	
	public void back(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage=new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root= loader.load(getClass().getResource("/practice/login.fxml").openStream());
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Fastfood Corner");
			primaryStage.show();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}