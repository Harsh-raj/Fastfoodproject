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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Monsales implements Initializable{
	
	static Connection connection = sqlConnection.Connector();
	
	PreparedStatement preparedStatement = null;
	
	@FXML
	private TableView<billdata> salestable;
    @FXML
    private TableColumn<billdata,Integer> Billno;
    @FXML
    private TableColumn<billdata,String> Cust_Fname;
    @FXML
    private TableColumn<billdata,Long> Mobileno;
    @FXML
    private TableColumn<billdata,Integer> Billamt;
    
	ObservableList<billdata> data= FXCollections.observableArrayList();
	
	ResultSet res;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			monsales();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void monsales() throws SQLException {
		try {
			if(connection==null)
				throw new NullPointerException();
			String query="SELECT * FROM monsales ORDER BY Billamt DESC;";
			preparedStatement = connection.prepareStatement(query);
			res=preparedStatement.executeQuery(query);
			while(res.next())
	        {
	            data.add(new billdata(res.getInt(1),res.getString(3),res.getLong(2),res.getInt(4)));
	        }
		}catch(Exception e) {
			e.printStackTrace();
			preparedStatement.close();
		}
		Mobileno.setCellValueFactory(new PropertyValueFactory<>("Mobileno"));
		Cust_Fname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        Billno.setCellValueFactory(new PropertyValueFactory<>("Billno"));
        Billamt.setCellValueFactory(new PropertyValueFactory<>("Billamt"));
		salestable.setItems(null);
        salestable.setItems(data);
	}
	
	public void backbtn(ActionEvent event) throws SQLException {
		((Node)event.getSource()).getScene().getWindow().hide(); 
	}
}