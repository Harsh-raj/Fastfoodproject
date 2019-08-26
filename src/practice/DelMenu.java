package practice;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DelMenu implements Initializable{
	public Loginmodel loginModel=new Loginmodel();
	
	@FXML
	private ComboBox <String> categorycbk;
	
	@FXML
	private ListView<String> itemlist;
	
	@FXML
	private TextField itemName;
	
	@FXML
	private Button delMenu;
	
	@FXML
	private Button cancelbtn;
	
	@FXML
	private Label info;
	
	static Connection connection = sqlConnection.Connector();
	
	PreparedStatement preparedStatement = null;
	
	ResultSet res;
	
	ObservableList<String>data= FXCollections.observableArrayList();
	
	ObservableList<String>data1= FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
			getCat();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getCat() throws SQLException {
		try {
			if(connection==null)
				throw new NullPointerException();
			String query="select distinct Category from fastfood;";
			preparedStatement = connection.prepareStatement(query);		//executable
			res=preparedStatement.executeQuery(query);
			while(res.next()) {
				String rs = res.getString(1);
	            data.add(rs);
			}
		}catch(Exception e) {
			e.printStackTrace();
			preparedStatement.close();
		}
		categorycbk.setItems(null);
		categorycbk.setItems(data);
		categorycbk.toString();
	}

	public void delMenu(ActionEvent event) throws SQLException {
		if(!itemName.getText().equals("")) {
		try {
			if(connection==null)
				throw new NullPointerException();
			String query="delete from fastfood where Itemname='"+ itemName.getText() +"';";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			info.setText("item deleted...");//executable
			//if(preparedStatement.executeUpdate(query)!=0)
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			preparedStatement.close();
		}
		((Node)event.getSource()).getScene().getWindow().hide();
		}else {
			info.setText("fields are empty...");
		}
	}
	public void cbksel(ActionEvent event) {
		// TODO Auto-generated method stub
		data1.clear();
		try {
			if(connection==null)
				throw new NullPointerException();
			String query="Select itemname from fastfood where fastfood.Category ='"+ categorycbk.getValue().toString() +"';";
			preparedStatement = connection.prepareStatement(query);
			res=preparedStatement.executeQuery(query);
			while(res.next())
			{
				data1.add(res.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
			try {
				preparedStatement.close();
			} catch (SQLException e1) {
			// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
		itemlist.setItems(null);
		itemlist.setItems(data1);
	}
	public void cancelsetting(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide(); //executable
	}	
}
