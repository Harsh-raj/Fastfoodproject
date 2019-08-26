package practice;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Settings {
	public Loginmodel loginModel=new Loginmodel();
	@FXML
	private TextField newconfirmpassword;
	
	@FXML
	private TextField oldpassword;
	
	@FXML
	private Label info;
	
	@FXML
	private TextField newpassword;
	
	@FXML
	private TextField hint;
	
	@FXML
	private Button okbtn;
	
	@FXML
	private Button cancelbtn;
	
	static Connection connection = sqlConnection.Connector();
	
	PreparedStatement preparedStatement = null;
	
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public void LoginSetting(ActionEvent event) throws SQLException {
		if(loginModel.isLogin(ControllerMain.admin,oldpassword.getText()) && !oldpassword.getText().equals("") && !newpassword.getText().equals("") && !newconfirmpassword.getText().equals("") && !hint.getText().equals("")) {
			String query="update admin set pwd = ?,hint = ? where pwd = ?";
			try {
				if(connection==null)
					throw new NullPointerException();
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setString(1, newconfirmpassword.getText());
				preparedStatement.setString(3, oldpassword.getText());
				preparedStatement.setString(2, hint.getText());
				preparedStatement.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				preparedStatement.close();
			}
			((Node)event.getSource()).getScene().getWindow().hide(); 
		}else {
			info.setText("fields are empty or invalid data entered in the fields");
		}
	}
	public void cancelsetting(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide(); 
	}
}
