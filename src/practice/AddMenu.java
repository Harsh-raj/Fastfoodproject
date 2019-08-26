package practice;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddMenu {
	public Loginmodel loginModel=new Loginmodel();
	@FXML
	private TextField itemName;
	
	@FXML
	private TextField itemPrice;
	
	@FXML
	private Label status;
	
	@FXML
	private Label info;
	
	@FXML
	private TextField itemCategory;
	
	@FXML
	private Button addMenu;
	
	@FXML
	private Button cancelbtn;
	
	static Connection connection = sqlConnection.Connector();
	
	PreparedStatement preparedStatement = null;
	
	ResultSet res;
	
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public void addMenu(ActionEvent event) throws SQLException {
		if(!itemName.getText().equals("") && !itemPrice.getText().equals("") && !itemCategory.getText().equals("")) {
			try {
				if(connection==null)
					throw new NullPointerException();
				String query= "Select max(ID) from fastfood;";
				preparedStatement = connection.prepareStatement(query);
				res=preparedStatement.executeQuery(query);
				res.next();
				int r=res.getInt(1)+1;
				System.out.println(r);
				String query1="insert into fastfood (ID,itemname,price,category) values('" + r + "','" + itemName.getText() + "','" + itemPrice.getText() + "','" + itemCategory.getText() + "');";
				preparedStatement = connection.prepareStatement(query1);
				preparedStatement.executeUpdate();
				query1="insert into stock (ID,itemname,qty) values('" + r + "','" + itemName.getText() + "',10);";
				preparedStatement = connection.prepareStatement(query1);
				preparedStatement.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				preparedStatement.close();
			}
		((Node)event.getSource()).getScene().getWindow().hide();
		}else {
			info.setText("some of the fields are not filled...");
		}
	}
	public void cancelsetting(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide(); 
	}
}