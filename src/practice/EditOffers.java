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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditOffers implements Initializable{
	
	static Connection connection = sqlConnection.Connector();
	
	PreparedStatement preparedStatement = null;
	
	@FXML
	private TableView<offers> Offer;
    @FXML
    private TableColumn<offers,Integer> Creditpt;
    @FXML
    private TableColumn<offers,Integer> Discount;
    @FXML
    private TextField credit;
    @FXML
    private TextField disc;
    @FXML
    private Label info;
    
	ObservableList<offers> data= FXCollections.observableArrayList();
	
	ResultSet res;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			offerdisc();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void offerdisc() throws SQLException {
		try {
			if(connection==null)
				throw new NullPointerException();
			String query="select * from discount;";
			preparedStatement = connection.prepareStatement(query);
			res=preparedStatement.executeQuery(query);
			while(res.next())
	        {
	            data.add(new offers(res.getInt(1),res.getInt(2)));
	        }
		}catch(Exception e) {
			e.printStackTrace();
			preparedStatement.close();
		}
		Creditpt.setCellValueFactory(new PropertyValueFactory<>("Creditpt"));
		Discount.setCellValueFactory(new PropertyValueFactory<>("Discount"));
		Offer.setItems(null);
		Offer.setItems(data);
	}
	
	public void editDisc() throws SQLException {
		if(!credit.getText().equals("") && !disc.getText().equals("") && Integer.parseInt(disc.getText())<100) {
		try {
			if(connection==null)
				throw new NullPointerException();
			System.out.println("/");
			String query="Update discount set Discount = '"+ disc.getText() +"' where credit_pt='"+ credit.getText() +"';";
			preparedStatement = connection.prepareStatement(query);
			System.out.println("/");
			preparedStatement.executeUpdate(query);
			System.out.println("/");
			info.setText("Update Successful");
		}catch(Exception e) {
			e.printStackTrace();
			preparedStatement.close();
			info.setText("Update Unsuccessful");
		}
		}else {
			info.setText("fields are empty or discount value is invalid...");
		}
	}
	
	public void backbtn(ActionEvent event) throws SQLException {
		((Node)event.getSource()).getScene().getWindow().hide(); 
	}
}
