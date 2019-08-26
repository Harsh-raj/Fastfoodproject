package practice;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TakeOrder implements Initializable{
	@FXML
    private TableView<Orders> table;
    @FXML
    private TableColumn<Orders,String> item;
    @FXML
    private TableColumn<Orders,Integer> price;
    @FXML
    private TextField adddata;
    @FXML
    private TextField deldata;
    @FXML
    private ComboBox<String> categorycbk;
    @FXML
    private ComboBox<String> itemcbk;
    @FXML
    private Label info;
    private int count=0;
    ObservableList<String> data=FXCollections.observableArrayList();
    ObservableList<Orders> data1=FXCollections.observableArrayList();
    ObservableList<Orders> data2=FXCollections.observableArrayList();
    static Connection connection = sqlConnection.Connector();
    public void adddatas()
    {
    	if(!adddata.getText().equals("")) {
        try{ResultSet rs = connection.createStatement().executeQuery("select qty from stock where itemname='"+adddata.getText()+"'");
            if(rs.next() && (rs.getInt(1)>0)) {
            	connection.createStatement().executeUpdate("UPDATE stock SET qty = IF(qty > 0, qty - 1, 0) WHERE itemname = '"+ adddata.getText() +"' and qty>0");
            	rs = connection.createStatement().executeQuery("select price from fastfood where itemname='"+adddata.getText()+"';");
            	int val=0;
            	if(rs.next()) {
            		val=rs.getInt(1);
            		count++;
            	}
            	String sql="insert into bill (itemname,price) values('"+adddata.getText()+"',"+val+");";
            	java.sql.Statement statement=connection.createStatement();
            	statement.executeUpdate(sql);
            	info.setText("Item "+ count +"added to the Order list...");
            }
            else {
            	info.setText("Order item out of Stock...");
            }
        }catch(Exception e){
            System.out.print(e);
        }
        }else {
        	info.setText("Enter the Order below...");
        }
    }
    public void deldatas(){
    	if(!adddata.getText().equals("")) {
        try{
            String sql="delete from bill where itemname ='"+deldata.getText()+"'";
            java.sql.Statement statement=connection.createStatement();
            statement.executeUpdate(sql);
            connection.createStatement().executeUpdate("UPDATE stock SET qty = qty+1 WHERE itemname = '" + deldata.getText() +"';");
        }
        catch(Exception e){
            System.out.print(e);
        }
    	}else {
        	info.setText("Enter the Order below to be deleted...");
        }
    }

    public void getCat() throws SQLException {
		try {
			if(connection==null)
				throw new NullPointerException();
			String query="select distinct Category from fastfood;";
			java.sql.Statement statement=connection.createStatement();
            ResultSet res=statement.executeQuery(query);
			while(res.next()) {
				String rs = res.getString(1);
	            data.add(rs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		categorycbk.setItems(null);
		categorycbk.setItems(data);
		categorycbk.toString();
	}
    
    public void getitem() throws SQLException {
		try {
			if(connection==null)
				throw new NullPointerException();
			String query="select distinct Itemname from fastfood;";
			java.sql.Statement statement=connection.createStatement();
            ResultSet res=statement.executeQuery(query);
			while(res.next()) {
				String rs = res.getString(1);
	            data.add(rs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		categorycbk.setItems(null);
		categorycbk.setItems(data);
		categorycbk.toString();
	}
    
    public void  checkout() throws SQLException
    {
    	if(connection==null)
			throw new NullPointerException();
		String query="select * from bill;";
		java.sql.Statement statement=connection.createStatement();
        ResultSet res=statement.executeQuery(query);
		if(res.next()) {
        try{
        	Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root= loader.load(getClass().getResource("/practice/Bill.fxml").openStream());
		Scene scene = new Scene(root,500,300);
		scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fastfood Corner");
		primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
		}else {
			info.setText("No orders are taken yet... add some items for the bill");
		}
    }
    public void Categorysel(ActionEvent event) {
        try
        {	
            data1 = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("select itemname, price from fastfood where category= '"+categorycbk.getValue().toString()+"';");
            while(rs.next())
            {
                data1.add(new Orders(rs.getString(1),rs.getInt(2)));
            }
        }catch(SQLException ex){
            System.err.println("Error"  + ex);
        }
        item.setCellValueFactory(new PropertyValueFactory<>("food_item"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.setItems(null);
        table.setItems(data1);
    }
    public void Itemsel(ActionEvent event) {
        try
        {	
            data2 = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("select itemname from fastfood where category= '"+categorycbk.getValue().toString()+"';");
            while(rs.next())
            {
                data2.add(new Orders(rs.getString(1),rs.getInt(2)));
            }
        }catch(SQLException ex){
            System.err.println("Error"  + ex);
        }
    }
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			getCat();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
