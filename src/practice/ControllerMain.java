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
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerMain implements Initializable{
	Stage primaryStage;
	public Loginmodel loginModel=new Loginmodel();
	@FXML
	private Label status;
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Label top;
	
	@FXML
	private Label hint;
	static String admin;
	PreparedStatement preparedstatement;
	
	static Connection connection = sqlConnection.Connector();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(loginModel.isDbConnected()) {
			status.setText("Linked");
		}else {
			status.setText("Not Linked");
		}
	}
	public void login(ActionEvent event) {
		try {
			if(loginModel.isLogin(username.getText(), password.getText())) {
				admin=username.getText();
				((Node)event.getSource()).getScene().getWindow().hide(); 
				Stage primaryStage = new Stage();
				FXMLLoader loader=new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/practice/Admin.fxml").openStream());
				AdminController adminController = (AdminController)loader.getController();
				adminController.getUser(username.getText());
				Scene scene = new Scene(root,500,300);
				scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Fastfood Corner");
				primaryStage.show();
			}
			else {
				top.setText("Login failed");
				username.setText("");
				password.setText("");
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			top.setText("Login failed");
			e.printStackTrace();
		}
	}
	
	public void verifyCust(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); 
			Stage primaryStage=new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root= loader.load(getClass().getResource("/practice/verifystaff.fxml").openStream());
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
	
	public void hintpass(ActionEvent event) throws SQLException {
		if(!username.getText().equals("")) {
		try {
			String query="select hint from admin where username = '"+ username.getText()+"';";
				if(connection==null)
					throw new NullPointerException();
				preparedstatement = connection.prepareStatement(query);
				ResultSet rs=preparedstatement.executeQuery();
				if(rs.next())
					hint.setText(rs.getString(1));
				else
					hint.setText("hint unavailable");
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				preparedstatement.close();
			}
		}else {
			hint.setText("field is empty...");
		}
	}
}